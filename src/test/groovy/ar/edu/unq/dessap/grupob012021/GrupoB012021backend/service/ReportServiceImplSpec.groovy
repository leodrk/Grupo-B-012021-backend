package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.Reason
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.Report
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.ReportRepository
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ReportServiceImplSpec extends Specification{

    ReportRepository reportRepository = Mock(ReportRepository)
    ReviewService reviewService = Mock(ReviewService)
    ReportServiceImpl reportServiceImpl = new ReportServiceImpl(reviewService: reviewService, reportRepository:reportRepository)


    def "when reportReview method is called with a review that does not exist, it must throw NoSuchElementException"(){
        given:
        def reviewId = 1
        def reportId = 2
        reviewService.findById(reviewId) >> {throw new NoSuchElementException()}

        when:
        reportServiceImpl.reportReview(reviewId, reportId)

        then:
        thrown NoSuchElementException
    }

    def "when reportReview method is called with a review and a reason, it must sae a report"(){
        given:
        def review = new Review()
        def id = 1

        review.setReports(rep)
        reviewService.findById(_) >> review

        when:
        reportServiceImpl.reportReview(id, Reason.SPOIL.ordinal())

        then:
        1 * reportRepository.save(_)

        where:
        rep << [[new Report(reason:Reason.SPOIL)],[new Report(reason:Reason.NONSENSE)]]
    }

}