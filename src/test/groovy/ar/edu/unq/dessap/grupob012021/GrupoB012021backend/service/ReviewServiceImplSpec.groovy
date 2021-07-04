package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.ReviewCriteriaDTO
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.ReviewsByMonthDTO
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

    def "when findById is called with a non existing id, it should throw NoSuchElementException"(){
        given:
        reviewRepository.findById(_) >> Optional.empty()

        when:
        reviewServiceImpl.findById(1)

        then:
        thrown NoSuchElementException
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
        reviewRepository.findById(_ as Integer) >> Optional.empty()

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
        reviewRepository.findById(_ as Integer) >> Optional.empty()

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

    def "when getReviewsByMonth is called with a platform that has no reviews, it must throw NoSuchElementException"(){
        given:
        def platform = "Netflix"
        reviewRepository.findAllByPlatform(platform) >> []

        when:
        reviewServiceImpl.getReviewsByMonth(platform)

        then:
        thrown NoSuchElementException
    }

    def "when getReviewsByMonth is called with a platform that has reviews, it should return a reviewsbymonthDTO"(){
        given:
        def platform = "Netflix"
        def date1 = new GregorianCalendar(2021,10,1).getTime()
        def date2 = new GregorianCalendar(2021,11,1).getTime()
        def stringDate1 = "November - 2021"
        def stringDate2 = "December - 2021"
        def review1 = new Review(platform: platform, date: date1)
        def review2 = new Review(platform: platform, date: date2)
        def review3 = new Review(platform: platform, date: date2)
        reviewRepository.findAllByPlatform(platform) >> [review1, review2, review3]
        def reviewsByMonthDTO = new ReviewsByMonthDTO(months: [stringDate1, stringDate2], reviewAmount: [1,2])

        when:
        def result = reviewServiceImpl.getReviewsByMonth(platform)

        then:
        result.getMonths() == reviewsByMonthDTO.getMonths()
        result.getReviewAmount() == reviewsByMonthDTO.getReviewAmount()
    }
}
