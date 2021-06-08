package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.controllers

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service.ReportService
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

@SpringBootTest(classes = ReportControllerSpec.class)
class ReportControllerSpec extends Specification{

    ReportService reportService = Mock(ReportService)
    ReportController reportController = new ReportController(reportService:reportService)


    def "when reportReview is called properly it must return a responseentity with the status ok"(){
        given:
        def reviewId = 1
        def reasonId = 2
        def responseEntity = new ResponseEntity(null, HttpStatus.OK)


        when:
        def result = reportController.reportReview(reviewId,reasonId)

        then:ReportControllerSpec
        1 * reportService.reportReview(reviewId,reasonId)
        result == responseEntity
    }

    def "when reportReview is called and a exception is thrown, it must return a responseEntity with the status bad_request"(){
        given:
        def reviewId = 1
        def reasonId = 2
        def responseEntity = new ResponseEntity(null, HttpStatus.BAD_REQUEST)


        when:
        def result = reportController.reportReview(reviewId,reasonId)

        then:
        1 * reportService.reportReview(reviewId,reasonId) >> {throw new Exception()}
        result == responseEntity
    }
}