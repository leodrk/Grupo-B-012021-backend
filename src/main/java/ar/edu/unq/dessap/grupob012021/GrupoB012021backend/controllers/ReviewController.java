package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.controllers;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.content.Content;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.ReviewCriteriaDTO;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.user.User;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service.ContentService;
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

    @PostMapping(value="/saveReview/{contentId}")
    public ResponseEntity saveReview(@RequestBody Review review, @PathVariable(value="contentId") int contentId) {
        User user = (User) request.getSession().getAttribute("user");
        String platform = user.getPlatform();
        Content content;
        try{
            content = contentService.findById(contentId).get();
        }
        catch (NoSuchElementException e){
            return new ResponseEntity("El contenido no existe", HttpStatus.BAD_REQUEST);
        }
        review.setContent(content);
        reviewService.save(review);
        subscriberLogService.notifySubscribers(review, platform);
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @PostMapping(value = "/like/{review}")
    public ResponseEntity likeReview(@PathVariable(value="review") int review){
        try {
            reviewService.likeReview(review);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @PostMapping(value = "/dislike/{review}")
    public ResponseEntity dislikeReview(@PathVariable(value="review") int review){
        try {
            reviewService.dislikeReview(review);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @GetMapping(value = "/findbycontent/{content}")
    public ResponseEntity<List<Review>> getReviewByContent(@PathVariable(value="content") int contentId){
            ArrayList<Review> reviewList = (ArrayList) reviewService.findByContentId(contentId);
            if (reviewList.size() > 0){
                return new ResponseEntity<>(reviewList, HttpStatus.OK);
            }
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/findByCriteria/{pageNumber}")
    public ResponseEntity<List<Review>> getReviewByCriteria(@PathVariable(value="pageNumber") int pageNumber,
                                                           @RequestBody ReviewCriteriaDTO reviewCriteria){
            return new ResponseEntity<>(this.reviewService.findByCriteria(reviewCriteria, pageNumber), HttpStatus.OK);
    }
}
