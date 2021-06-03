package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.Content;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional(readOnly = true)
public interface ContentRepository extends JpaRepository<Content, Long> {

    List<Content> findDistinctByReviewsIn (List<Review> reviews);
}