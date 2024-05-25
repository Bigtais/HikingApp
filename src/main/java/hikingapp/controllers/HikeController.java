package hikingapp.controllers;

import hikingapp.data.model.Category;
import hikingapp.data.model.Hike;
import hikingapp.services.providers.ClubMemberProviderService;
import hikingapp.services.providers.ICategoriesProvider;
import hikingapp.services.providers.IHikeProvider;
import hikingapp.services.security.HikeOwnerChecker;
import hikingapp.services.validation.HikeValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.Map;

/**
 * Controller for searching and displaying hikes with their details.
 */
@Controller
@RequestMapping("/hike")
public class HikeController {

    @Autowired
    HikeValidator validator;

    @Autowired
    ClubMemberProviderService clubMemberProviderService;

    @Autowired
    IHikeProvider hikeProviderService;

    @Autowired
    ICategoriesProvider categoriesProviderService;

    @Autowired
    HikeOwnerChecker hikeOwnerChecker;

    /**
     * Tries to find a list of hikes with the requested name.
     * @param name The name of the hikes to search for.
     * @return The view containing the list of found hikes, possibly none.
     */
    @RequestMapping("/find")
    public ModelAndView findHikes(String name) {
        final var hikes = hikeProviderService.searchByName("%" + name + "%");
        return new ModelAndView("hikesList", "hikes", hikes);
    }

    /**
     * Show the hike edition form for either creation or edition of an already existing hike.
     * @param hike The hike to edit, or a new empty hike.
     * @return The hike edition form.
     */
    @GetMapping("/edit")
    @PreAuthorize("#hike.creator == null || @hikeOwnerChecker.isOwner(principal, #hike)")
    public String getHikeForm(@ModelAttribute Hike hike){
        return "hikeForm";
    }

    /**
     * Creates a new hike from the data in the form, or updates it if it already exists.
     * @param principal The principal object corresponding to the currently authenticated user.
     * @param hike The hike to create or update.
     * @param result The result of the validation of the Hike object.
     * @return A redirection to the page of the hike if the create or update operation is successful,
     * else the hike form with the errors found.
     */
    @PostMapping("/edit")
    @PreAuthorize("#hike.creator == null || @hikeOwnerChecker.isOwner(principal, #hike)")
    public String saveHike(Principal principal, @ModelAttribute @Valid Hike hike, BindingResult result) {
        validator.validate(hike, result);
        if (result.hasErrors())
            return "hikeForm";

        // Update
        if (hike.getId() != null) {
            var found = hikeProviderService.getHikeDetails(hike.getId());
            if (found != null) {
                hikeProviderService.updateHike(hike);
                return "redirect:" + hike.getId().toString();
            }
        }

        // Creation
        var creator = clubMemberProviderService.getCurrentAuthenticatedMember(principal);
        hike.setCreator(creator);
        hikeProviderService.createHike(hike);
        return "redirect:" + hike.getId().toString();
    }

    /**
     * Tries to delete a hike.
     * @param hike The hike to delete.
     * @return A redirection to the account details of the currently authenticated user.
     * @throws AccessDeniedException If the currently authenticated user is not allowed to delete this hike.
     */
    @RequestMapping("/delete")
    @PreAuthorize("hikeOwnerChecker.isOwner(principal, hike)")
    public String deleteHike(@ModelAttribute Hike hike) throws AccessDeniedException {
        hikeProviderService.deleteHike(hike);
        return "redirect:/account";
    }

    /**
     * Show the hike corresponding to the ID in the path with its details.
     * @param hikeId The ID of the hike to display.
     * @return The view containing the details of the hike.
     */
    @RequestMapping("/{hikeId}")
    public ModelAndView getHikeDetails(@PathVariable Long hikeId) {
        var hike = hikeProviderService.getHikeDetails(hikeId);
        return new ModelAndView("hike", "hike", hike);
    }

    /**
     * Tries to load the hike for edition if the request parameter "id" is set, or creates a new hike otherwise.
     * @param hikeId The optional ID of the hike
     * @return The loaded hike, or a new empty hike.
     */
    @ModelAttribute
    public Hike newHike(@RequestParam(value = "id", required = false) Long hikeId)
    {
        if (hikeId != null) {
            var hike = hikeProviderService.getHikeDetails(hikeId);
            if (hike != null)
                return hike;
        }
        Hike hike = new Hike();
        hike.setName("");
        hike.setWebsite("");
        hike.setDescription("");
        return hike;
    }

    /**
     * Retrieves the list of categories possible for a hike.
     * @return A map of the categories and their name.
     */
    @ModelAttribute("categories")
    public Map<Category, String> categoriesMap() {
        return categoriesProviderService.getCategoriesAsMap();
    }
}
