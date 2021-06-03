package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.controllers;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.Content;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.ReviewCriteriaDTO;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ContentController {

    @Autowired
    private ContentService contentService;

    @PostMapping(value = "content/findByCriteria")
    public ResponseEntity<List<Content>> findContentByReviews(@RequestBody ReviewCriteriaDTO reviewCriteriaDTO){
        return new ResponseEntity(contentService.findByReviews(reviewCriteriaDTO), HttpStatus.OK);
    }
}