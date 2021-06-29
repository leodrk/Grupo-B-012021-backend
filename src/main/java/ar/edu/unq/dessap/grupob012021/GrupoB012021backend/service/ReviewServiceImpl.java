package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.ReviewCriteriaDTO;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.reviewRepository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                var review = optionalReview.get();
                review.setLikes(review.getLikes() + 1);
                reviewRepository.save(review);
            }
            else{
                throw new NoSuchElementException();
            }
    }

    public void dislikeReview (int reviewId) throws NoSuchElementException{
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if (optionalReview.isPresent()) {
            var review = optionalReview.get();
            review.setDislikes(review.getDislikes() + 1);
            reviewRepository.save(review);
        }
        else{
            throw new NoSuchElementException();
        }
    }

    public List<Review> findByContentId(int contentId){
        return reviewRepository.findByContentId(contentId);
    }

    public void save (Review review){
        reviewRepository.save(review);
    }

    public Review findById (int reviewId) throws NoSuchElementException{
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if (optionalReview.isPresent()) {
            return optionalReview.get();
        }
        throw new NoSuchElementException();
    }


    @Override
    public List<Review> findByCriteria(ReviewCriteriaDTO reviewCriteria, int pageNumber) {
        return reviewRepository.findByCriteria(reviewCriteria,pageNumber);
    }
}
