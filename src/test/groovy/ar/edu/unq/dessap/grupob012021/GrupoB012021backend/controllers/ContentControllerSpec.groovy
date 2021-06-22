package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.controllers

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.content.Content
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.ReviewCriteriaDTO
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service.ContentService
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

@SpringBootTest(classes = ContentControllerSpec.class)
class ContentControllerSpec extends Specification{

    ContentService contentService = Mock(ContentService)
    ContentController contentController = new ContentController(contentService:contentService)


    def "when findContentByReviews is called it must return a responseentity with the status ok"(){
        given:
        def contentList = [new Content()]
        def responseEntity = new ResponseEntity(contentList, HttpStatus.OK)

        when:
        def result = contentController.findContentByReviews(new ReviewCriteriaDTO())

        then:
        1 * contentService.findByReviews(_) >> contentList
        result == responseEntity
    }
}