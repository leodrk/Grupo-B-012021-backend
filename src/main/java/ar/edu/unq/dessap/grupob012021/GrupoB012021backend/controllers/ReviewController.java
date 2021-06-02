package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.controllers;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.ReviewCriteriaDTO;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ReviewController {

    @Autowired
    private ReviewService reviewService;


    @PostMapping(value="/saveReview")
    public ResponseEntity saveReview(@RequestBody Review review) {
        this.reviewService.save(review);
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @PostMapping(value = "/like/{review}")
    public ResponseEntity likeValidateReview(@PathVariable(value="review") int review){
        try {
            reviewService.likeReview(review);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @PostMapping(value = "/dislike/{review}")
    public ResponseEntity dislikeValidateReview(@PathVariable(value="review") int review){
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
    public ResponseEntity<List<Review>> getReviewByContent(@PathVariable(value="pageNumber") int pageNumber,
                                                           @RequestBody ReviewCriteriaDTO reviewCriteria){
            return new ResponseEntity<>(this.reviewService.findByCriteria(reviewCriteria, pageNumber), HttpStatus.OK);
    }
}
