package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.reviewRepository;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

    Optional<Review> findById(int id);

    @Query("SELECT r FROM Review r WHERE r.content.id = ?1")
    List<Review> findByContentId(int contentId);

    List<Review> findAllByPlatform(String platform);
}