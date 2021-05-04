package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.Review;

import java.util.List;

public interface ReviewService {

    void likeReview (int reviewId);

    void dislikeReview (int reviewId);

    List<Review> findByContentId(int contentId);
}
