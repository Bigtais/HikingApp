package hikingapp.data.dao;

import hikingapp.data.dao.repositories.CategoryRepository;
import hikingapp.data.dao.repositories.ClubMemberRepository;
import hikingapp.data.dao.repositories.HikeRepository;
import hikingapp.data.model.Category;
import hikingapp.data.model.ClubMember;
import hikingapp.data.model.Hike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class JpaDao implements Dao {

    @Autowired
    private ClubMemberRepository clubMemberRepository;

    @Autowired
    private HikeRepository hikeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryWithHikes(Long id) {
        return categoryRepository.findCategoryByIdWithHikes(id);
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Hike getHikeById(Long id) {
        return hikeRepository.getHikeById(id);
    }

    public List<Hike> getHikesByNameLike(String pattern) {
        return hikeRepository.getHikesByNameLikeIgnoreCase(pattern);
    }

    public List<Hike> getHikesByDescriptionLike(String pattern) {
        return hikeRepository.getHikesByDescriptionLike(pattern);
    }

    public List<Hike> getHikesByWebsiteLike(String pattern) {
        return hikeRepository.getHikesByWebsiteLike(pattern);
    }

    public List<Hike> getHikesByDate(Date date) {
        return hikeRepository.getHikesByDate(date);
    }

    public List<Hike> getHikesByCategory(Category category) {
        return hikeRepository.getHikesByCategory(category);
    }

    public List<Hike> getHikesByCreator(ClubMember creator) {
        return hikeRepository.getHikesByCreator(creator);
    }

    public List<ClubMember> getAllClubMembers() {
        return clubMemberRepository.findAll();
    }

    public ClubMember getClubMember(String email) {
        return clubMemberRepository.findClubMemberByEmail(email);
    }

    public ClubMember getClubMemberWithHikesDetails(Long id) {
        return clubMemberRepository.findClubMemberByIdWithHikes(id);
    }

    public Hike addHike(Hike hike) {
        return hikeRepository.save(hike);
    }

    public Hike updateHike(Hike hike) {
        return hikeRepository.save(hike);
    }

    public void deleteHike(Long id) {
        hikeRepository.deleteHikeById(id);
    }

    public ClubMember addClubMember(ClubMember clubMember) {
        return clubMemberRepository.save(clubMember);
    }

    public ClubMember updateClubMember(ClubMember clubMember) {
        return clubMemberRepository.save(clubMember);
    }
}
