package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.Review;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public interface ReviewService {

    void likeReview (int reviewId);

    void dislikeReview (int reviewId);

    List<Review> findByContentId(int contentId);

    void save (Review review);

    Review findById(int reviewId) throws NoSuchElementException;
}
