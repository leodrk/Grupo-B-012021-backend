package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.controllers;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.ReviewCriteriaDTO;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service.ContentService;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.content.Content;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.ReviewDTO;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service.ReviewService;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service.SubscriberLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private SubscriberLogService subscriberLogService;

    @Autowired
    private ContentService contentService;

    @Autowired
    private HttpServletRequest request;

    @PostMapping(value="api/review/saveReview/{contentId}")
    public ResponseEntity saveReview(@RequestBody ReviewDTO reviewDTO, @PathVariable(value="contentId") int contentId) {
        Content content;
        Optional<Content> optionalContent = contentService.findById(contentId);
        if(optionalContent.isPresent()) {
            content = optionalContent.get();
        }
        else {
            return new ResponseEntity("El contenido no existe", HttpStatus.BAD_REQUEST);
        }
        Review review = getReviewFromDTO(reviewDTO);
        review.setContent(content);
        reviewService.save(review);
        if(content.getSubscribers().contains(review.getPlatform())) {
            subscriberLogService.registerLog(review);
        }
        return new ResponseEntity("Review guardado satisfactoriamente", HttpStatus.OK);
    }

    @PostMapping(value = "api/review/like/{review}")
    public ResponseEntity likeReview(@PathVariable(value="review") int review){
        try {
            reviewService.likeReview(review);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @PostMapping(value = "api/review/dislike/{review}")
    public ResponseEntity dislikeReview(@PathVariable(value="review") int review){
        try {
            reviewService.dislikeReview(review);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @GetMapping(value = "api/review/findbycontent/{content}")
    public ResponseEntity<List<Review>> getReviewByContent(@PathVariable(value="content") int contentId){
            ArrayList<Review> reviewList = (ArrayList) reviewService.findByContentId(contentId);
            if (!reviewList.isEmpty()){
                return new ResponseEntity<>(reviewList, HttpStatus.OK);
            }
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "api/review/findByCriteria/{pageNumber}")
    public ResponseEntity<List<Review>> getReviewByCriteria(@PathVariable(value="pageNumber") int pageNumber,
                                                           @RequestBody ReviewCriteriaDTO reviewCriteria){
            return new ResponseEntity<>(this.reviewService.findByCriteria(reviewCriteria, pageNumber), HttpStatus.OK);
    }

    private Review getReviewFromDTO(ReviewDTO reviewDTO){
        Review review = new Review();
        review.setType(reviewDTO.getType());
        review.setShortText(reviewDTO.getShortText());
        review.setLongText(reviewDTO.getLongText());
        review.setSpoilerAlert(reviewDTO.isSpoilerAlert());
        review.setOrigin(reviewDTO.getOrigin());
        review.setLanguage(reviewDTO.getLanguage());
        review.setCountry(reviewDTO.getCountry());
        review.setCity(reviewDTO.getCity());
        review.setRating(reviewDTO.getRating());
        review.setPlatform(reviewDTO.getPlatform());
        review.setUserName(reviewDTO.getUserName());
        return review;
    }
}
