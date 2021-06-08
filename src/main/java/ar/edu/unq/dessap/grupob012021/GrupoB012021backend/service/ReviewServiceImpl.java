package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.ReviewCriteriaDTO;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.reviewRepository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;


    public void likeReview (int reviewId) throws NoSuchElementException{
            Optional<Review> optionalReview = reviewRepository.findById(reviewId);
            if (optionalReview.isPresent()) {
                Review review = optionalReview.get();
                review.setLikes(review.getLikes() + 1);
                reviewRepository.save(review);
            }
    }

    public void dislikeReview (int reviewId) throws NoSuchElementException{
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            review.setDislikes(review.getDislikes() + 1);
            reviewRepository.save(review);
        }
    }

    public List<Review> findByContentId(int contentId){
        ArrayList<Review> review = (ArrayList) reviewRepository.findByContentId(contentId);
        return review;
    }

    public void save (Review review){
        reviewRepository.save(review);
    }

    public Review findById (int reviewId) throws NoSuchElementException{
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if (optionalReview.isPresent()) {
            return optionalReview.get();
        }
        return null;
    }


    @Override
    public List<Review> findByCriteria(ReviewCriteriaDTO reviewCriteria, int pageNumber) {
        return reviewRepository.findByCriteria(reviewCriteria,pageNumber);
    }
}
