package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.Review;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public void likeReview (int reviewId) throws NoSuchElementException{
            Review review = reviewRepository.findById(reviewId).get();
            review.setLikes(review.getLikes() + 1);
            reviewRepository.save(review);
    }

    public void dislikeReview (int reviewId) throws NoSuchElementException{
        Review review = reviewRepository.findById(reviewId).get();
        review.setLikes(review.getDislikes()+1);
        reviewRepository.save(review);
    }

    public List<Review> findByContentId(int contentId) throws NoSuchElementException{
        ArrayList<Review> review = (ArrayList) reviewRepository.findByContentId(contentId);
        return review;
    }
}
