package hikingapp.data.dao.repositories;

import hikingapp.data.model.ClubMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {

    List<ClubMember> findAll();

    ClubMember findClubMemberByEmail(String email);

    @EntityGraph(value = "member-with-hikes")
    @Query(value = "SELECT cm FROM ClubMember cm WHERE cm.id=:id")
    ClubMember findClubMemberByIdWithHikes(Long id);
}