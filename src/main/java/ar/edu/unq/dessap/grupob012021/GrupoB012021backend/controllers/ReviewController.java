package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {
    @GetMapping("/test")
    public ResponseEntity test(){
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
