package controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {

    public ResponseEntity test(){
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
