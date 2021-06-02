package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.reviewRepository;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.ReviewCriteriaDTO;

import java.util.List;

public interface ReviewRepositoryCustom {

    List<Review> findByCriteria (ReviewCriteriaDTO reviewCriteria, int pageNumber);
}
