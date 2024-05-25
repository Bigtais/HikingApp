package hikingapp.creation;

import hikingapp.data.dao.Dao;
import hikingapp.data.model.Category;
import hikingapp.data.model.ClubMember;
import hikingapp.data.model.Hike;
import hikingapp.services.providers.IPasswordEncoderProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Profile("test")
public class TestInitializer implements IAppInitializer{

    @Autowired
    Dao dao;

    @Autowired
    IPasswordEncoderProvider passwordEncoderProvider;
    @Override
    public void initialize() {
        var nMembers = 5;
        var nCategories = 5;
        var encoder = passwordEncoderProvider.getPasswordEncoder();

        String[] categoryNames = {
                "Alpinisme de roche",
                "Escalade sportive",
                "Terrain d'aventure",
                "Randonnée en fôret",
                "Escalade alpine"
        };

        ClubMember[] clubMembers = new ClubMember[nMembers];
        for (int i = 0; i < nMembers; i++) {
            clubMembers[i] = new ClubMember();
            clubMembers[i].setFirstName("aaa" + i);
            clubMembers[i].setLastName("aaa" + i);
            clubMembers[i].setPassword(encoder.encode("aaaaaaaa"));
            clubMembers[i].setEmail("aaa" + i + "@gmail.com");
            dao.addClubMember(clubMembers[i]);
        }

        for (int i = 0; i < nCategories; i++) {
            var category = new Category();
            category.setName(categoryNames[i]);
            dao.addCategory(category);
            for (int j = 0; j < nMembers; j++) {
                var hike = new Hike();
                hike.setName("Hike User " + j + " Category " + categoryNames[i]);
                hike.setDescription("Hike Description Category " + i + " User " + j);
                hike.setDate(new Date(System.currentTimeMillis() + 100_000_000));
                hike.setWebsite("https://www.test" + i + ".com");
                hike.setCreator(clubMembers[j]);
                hike.setCategory(category);
                dao.addHike(hike);
            }
        }
    }
}
