package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.controllers;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping(value = "/report/{review}/{reason}")
    public ResponseEntity reportReview(@PathVariable(value="review") int review, @PathVariable(value="reason") int reason){
        try {
            reportService.reportReview(review, reason);
        }
        catch (Exception e){
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(null, HttpStatus.OK);
    }
}