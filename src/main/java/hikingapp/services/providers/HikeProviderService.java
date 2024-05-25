package hikingapp.services.providers;

import hikingapp.data.dao.Dao;
import hikingapp.data.model.Hike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service dedicated to the search of hikes.
 */
@Service
public class HikeProviderService implements IHikeProvider{

    @Autowired
    Dao dao;

    public Hike getHikeDetails(Long id) {
        return dao.getHikeById(id);
    }

    public List<Hike> searchByName(String name) {
        return dao.getHikesByNameLike(name);
    }

    public void createHike(Hike hike) {
        dao.addHike(hike);
    }

    public Hike updateHike(Hike hike){
        return dao.updateHike(hike);
    }

    public void deleteHike(Hike hike) {
        dao.deleteHike(hike.getId());
    }
}
