package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.ReviewCriteriaDTO;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.ReviewsByMonthDTO;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.reviewRepository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;


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

    public List<Review> findByCriteria(ReviewCriteriaDTO reviewCriteria, int pageNumber) {
        return reviewRepository.findByCriteria(reviewCriteria,pageNumber);
    }

    public ReviewsByMonthDTO getReviewsByMonth(String platform) throws NoSuchElementException {
        List<Review> reviews = reviewRepository.findAllByPlatform(platform);
        if (reviews.isEmpty()){
            throw new NoSuchElementException();
        }
        reviews.sort(Comparator.comparing(r -> r.getDate()));
        ListIterator<Review> reviewsIterator = reviews.listIterator();
        ReviewsByMonthDTO reviewsByMonth = new ReviewsByMonthDTO();
        while (reviewsIterator.hasNext()){
            Review tempReview = reviewsIterator.next();
            String month = getYearAndMonthFromReview(tempReview);
            List<Review> tempReviews = getReviewInMonth(reviews, tempReview.getDate());
            reviewsByMonth.addMonth(month);
            reviewsByMonth.addReviewAmount(tempReviews.size());
            reviews.removeAll(tempReviews);
            reviewsIterator = reviews.listIterator();
        }
        return reviewsByMonth;
    }

    private String getYearAndMonthFromReview(Review review){
        SimpleDateFormat yearFormater = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthFormater = new SimpleDateFormat("MMMM");
        return monthFormater.format(review.getDate()) + " - " + yearFormater.format(review.getDate());
    }

    private List<Review> getReviewInMonth (List<Review> reviews, Date date){
        LocalDateTime monthBegin = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().withDayOfMonth(1).minusDays(1).atTime(23, 59, 59);
        LocalDateTime monthEnd = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusMonths(1).withDayOfMonth(1).atTime(00, 00, 00);
        return reviews.stream().filter(r -> r.getDate().after(java.sql.Timestamp.valueOf(monthBegin)) &&
                                           r.getDate().before(java.sql.Timestamp.valueOf(monthEnd))).collect(Collectors.toList());
    }
}
