package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.controllers;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ReviewController {

    @Autowired
    private ReviewService reviewService;


    @PostMapping("/review")
    public ResponseEntity postReview(){
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

    @GetMapping(value = "/validate/{movie}")
    public ResponseEntity getReviewByMovie(){
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
