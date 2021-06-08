package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.Content
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.Reason
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.Report
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.ReviewCriteriaDTO
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.ContentRepository
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.reviewRepository.ReviewRepository
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ContentServiceImplSpec extends Specification{

    ContentRepository contentRepository = Mock(ContentRepository)
    ReviewRepository reviewRepository = Mock(ReviewRepository)
    ContentServiceImpl contentServiceImpl = new ContentServiceImpl(contentRepository: contentRepository, reviewRepository:reviewRepository)


    def "when findByReviews is called, it must call reviewRepository.findByCriteria and contentRepository.findDistinctByReviewsIn"(){
        given:
        def reviewCriteria = new ReviewCriteriaDTO()
        def reviews = []
        def content = new Content()
        def contentList = [content]

        when:
        def result = contentServiceImpl.findByReviews(reviewCriteria)

        then:
        1 * reviewRepository.findByCriteria(_) >> reviews
        result == contentList
        1 * contentRepository.findDistinctByReviewsIn(reviews) >> contentList
    }
}