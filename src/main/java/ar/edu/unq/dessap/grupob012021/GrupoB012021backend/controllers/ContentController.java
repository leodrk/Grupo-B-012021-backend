package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.controllers;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.ReviewCriteriaDTO;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.content.Content;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.content.ContentDTO;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ContentController {

    @Autowired
    private ContentService contentService;

    @PostMapping(value = "api/content/findByCriteria")
    public ResponseEntity<List<Content>> findContentByReviews(@RequestBody ReviewCriteriaDTO reviewCriteriaDTO){
        return new ResponseEntity(contentService.findByReviews(reviewCriteriaDTO), HttpStatus.OK);
    }

    @GetMapping(value = "api/content/getInfo/{contentId}")
    public ResponseEntity<ContentDTO> getContentInfo (@PathVariable(value = "contentId") int contentId){
        try {
            var contentDTO = contentService.getContentInfo(contentId);
            return new ResponseEntity<>(contentDTO,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity("Ha ocurrido un error al obtener la información del contenido solicitado",HttpStatus.BAD_REQUEST);
        }
    }
}