package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.ReviewCriteriaDTO
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.reviewRepository.ReviewRepository
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest (classes = ReviewServiceImplSpec.class)
class ReviewServiceImplSpec extends Specification{

    ReviewRepository reviewRepository = Mock(ReviewRepository)
    ReviewServiceImpl reviewServiceImpl = new ReviewServiceImpl(reviewRepository: reviewRepository)


    def "when save method is called with a review, it must call the method save from reviewRepository"(){
        given:
        def review = new Review()

        when:
        reviewServiceImpl.save(review)

        then:
        1 * reviewRepository.save(review)
    }

    def "when findById method is called with a review, it must call the method findById from reviewRepository"(){
        given:
        def review = new Review()
        def id = 1

        when:
        reviewServiceImpl.findById(id)

        then:
        1 * reviewRepository.findById(id) >> Optional.of(review)
    }

    def "when likeReview is called with a review with 1 like, it must set its likes to 2 and save it"(){
        given:
        def review = new Review();
        review.setId(1)
        review.setLikes(1)
        reviewRepository.findById(_ as Integer) >> Optional.of(review)


        when:
        reviewServiceImpl.likeReview(review.getId())

        then:
        review.getLikes() == 2
        1 * reviewRepository.save(review)
    }

    def "when likeReview is called with no existing review, it must throw NoSuchElementException"(){
        given:
        reviewRepository.findById(_ as Integer) >> {throw new NoSuchElementException()}

        when:
        reviewServiceImpl.likeReview(6)

        then:
        thrown NoSuchElementException
    }

    def "when dislikeReview is called with a review with 1 like, it must set its likes to 0 and save it"(){
        given:
        def review = new Review();
        review.setId(1)
        review.setDislikes(1)
        reviewRepository.findById(_ as Integer) >> Optional.of(review)


        when:
        reviewServiceImpl.dislikeReview(review.getId())

        then:
        review.getLikes() == 0
        1 * reviewRepository.save(review)
    }

    def "when dislikeReview is called with no existing review, it must throw NoSuchElementException"(){
        given:
        reviewRepository.findById(_ as Integer) >> {throw new NoSuchElementException()}

        when:
        reviewServiceImpl.dislikeReview(6)

        then:
        thrown NoSuchElementException
    }

    def "when findByContentId is called it must call reviewRepository.findByContentId and return the resulting array"(){
        given:

        when:
        reviewServiceImpl.findByContentId(1)

        then:
        1 * reviewRepository.findByContentId(1)
    }

    def "when findByCriteria is called it must call reviewRepository.findByCriteria"(){
        given:
        def criteria = Mock(ReviewCriteriaDTO)

        when:
        reviewServiceImpl.findByCriteria(criteria,1)

        then:
        1 * reviewRepository.findByCriteria(criteria,1)
    }
}
