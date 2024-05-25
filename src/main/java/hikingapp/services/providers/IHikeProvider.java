package hikingapp.services.providers;

import hikingapp.data.model.Hike;

import java.util.List;

/**
 * General interface for hike search , update and deletion operation.
 */
public interface IHikeProvider {

    /**
     * Searches for hikes by name.
     * @param name The name of the hike to search for.
     * @return The list of found hikes, possibly empty.
     */
    List<Hike> searchByName(String name);

    /**
     * Updates the requested hike.
     * @param hike The hike to update.
     * @return The updated hike.
     */
    Hike updateHike(Hike hike);

    /**
     * Creates a hike.
     * @param hike The hike to create.
     */
    void createHike(Hike hike);

    /**
     * Returns a specific hike with all its associated details.
     * @param id The id of the hike to search for.
     * @return The found hike with its associated details, or null if not found.
     */
    Hike getHikeDetails(Long id);

    /**
     * Deletes the requested hike.
     * @param hike The hike to delete.
     */
    void deleteHike(Hike hike);

}
