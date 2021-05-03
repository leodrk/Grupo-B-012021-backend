package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.controllers;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ReviewController {
    @PostMapping("/review")
    public ResponseEntity postReview(){
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @PutMapping(value = "/validate/{review}")
    public ResponseEntity postValidateReview(){
        return new ResponseEntity(null, HttpStatus.OK);
    }
    @GetMapping(value = "/validate/{movie}")
    public ResponseEntity getReviewByMovie(){
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
