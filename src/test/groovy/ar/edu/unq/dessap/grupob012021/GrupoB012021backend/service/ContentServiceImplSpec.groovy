package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.content.Content
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.content.ContentDTO
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.ReviewCriteriaDTO
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.ContentRepository
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.reviewRepository.ReviewRepository
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = ContentServiceImplSpec.class)
class ContentServiceImplSpec extends Specification{

    ContentRepository contentRepository = Mock(ContentRepository)
    ReviewRepository reviewRepository = Mock(ReviewRepository)
    ContentServiceImpl contentServiceImpl = new ContentServiceImpl(contentRepository: contentRepository, reviewRepository:reviewRepository)


    def "when findById is called it should return an optional"(){
        given:

        when:
        contentServiceImpl.findById(1)

        then:
        1 * contentRepository.findById(_)
    }

    def "when save is called, it should call contentRepository.save"(){
        given:
        def content = new Content()
        content.setId(1)

        when:
        contentServiceImpl.save(content)

        then:
        1 * contentRepository.save(content)
    }

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

    def "when getContentInfo is called with an existing contentid, it should return a contentDTO"(){
        given:
        def content = new Content()
        content.setTitle("duro de matar")
        def contentDTO = new ContentDTO()
        contentDTO.setTitle("duro de matar")
        content.setReviews([])
        contentRepository.findById(_) >> Optional.of(content)

        when:
        def result = contentServiceImpl.getContentInfo(1)

        then:
        result.getTitle() == contentDTO.getTitle()
    }

    def "when getContentInfo is called with a non existint contentId, it should return a NoSuchElementException"(){
        given:
        contentRepository.findById(_) >> {throw new NoSuchElementException()}

        when:
        contentServiceImpl.getContentInfo(1)

        then:
        thrown NoSuchElementException
    }
}