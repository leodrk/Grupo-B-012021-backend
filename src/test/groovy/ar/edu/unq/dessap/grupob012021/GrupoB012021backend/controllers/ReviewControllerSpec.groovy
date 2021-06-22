package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.controllers

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.content.Content
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.ReviewCriteriaDTO
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.user.User
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service.ContentService
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service.ReviewService
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service.SubscriberLogService
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

@SpringBootTest(classes = ReviewControllerSpec.class)
class ReviewControllerSpec extends Specification{

    ReviewService reviewService = Mock(ReviewService)
    SubscriberLogService subscriberLogService = Mock(SubscriberLogService)
    HttpServletRequest request = Mock(HttpServletRequest)
    ContentService contentService = Mock(ContentService)
    ReviewController reviewController = new ReviewController(reviewService: reviewService,
                                                             subscriberLogService: subscriberLogService,
                                                             request : request,
                                                             contentService: contentService)


    def "when saveReview is called properly it must return a responseentity with the status ok"(){
        given:
        def review = new Review()
        def responseEntity = new ResponseEntity(null, HttpStatus.OK)
        def session = Mock(HttpSession)
        def content = new Content()
        request.getSession() >> session
        session.getAttribute(_) >> new User()
        contentService.findById(_) >> Optional.of(content)


        when:
        def result = reviewController.saveReview(review, 1)

        then:ReviewControllerSpec
        1 * reviewService.save(review)
        result == responseEntity
    }

    def "when likeReview is called for an existing review it must call reviewService.likeReview and return a responseEntity.OK"(){
        given:
        def reviewId = 1
        def responseEntity = new ResponseEntity(null, HttpStatus.OK)


        when:
        def result = reviewController.likeReview(reviewId)

        then:
        result == responseEntity
    }

    def "when likeReview is called for an no existing review it must call reviewService.likeReview and return a responseEntity.BAD_REQUEST"(){
        given:
        def reviewId = 1
        def responseEntity = new ResponseEntity(null, HttpStatus.BAD_REQUEST)


        when:
        def result = reviewController.likeReview(reviewId)

        then:
        reviewService.likeReview(_) >> {throw new NoSuchElementException()}
        result == responseEntity
    }


    def "when dislikeReview is called for an existing review it must call reviewService.likeReview and return a responseEntity.OK"(){
        given:
        def reviewId = 1
        def responseEntity = new ResponseEntity(null, HttpStatus.OK)


        when:
        def result = reviewController.dislikeReview(reviewId)

        then:
        result == responseEntity
    }

    def "when dislikeReview is called for an no existing review it must call reviewService.likeReview and return a responseEntity.BAD_REQUEST"(){
        given:
        def reviewId = 1
        def responseEntity = new ResponseEntity(null, HttpStatus.BAD_REQUEST)


        when:
        def result = reviewController.dislikeReview(reviewId)

        then:
        reviewService.dislikeReview(_) >> {throw new NoSuchElementException()}
        result == responseEntity
    }

    def "when getReviewByContent is called and it finds a list of reviews it must return a responseEntity with the status ok"(){
        given:
        def contentId = 1
        def reviewList = [new Review()]
        def responseEntity = new ResponseEntity(reviewList, HttpStatus.OK)


        when:
        def result = reviewController.getReviewByContent(contentId)

        then:
        1 * reviewService.findByContentId(contentId) >> reviewList
        result == responseEntity
    }

    def "when getReviewByContent is called and it does not finds a list of reviews it must return a responseEntity with the status bad_request"(){
        given:
        def contentId = 1
        def responseEntity = new ResponseEntity(null, HttpStatus.BAD_REQUEST)


        when:
        def result = reviewController.getReviewByContent(contentId)

        then:
        1 * reviewService.findByContentId(contentId) >> []
        result == responseEntity
    }

    def "when getReviewByCriteria is called it must call reviewService.findByCriteria and return a responseEntity wieh the status ok" (){
        given:
        def pageNumber = 1
        def reviewCriteria = new ReviewCriteriaDTO()
        def reviewList = [new Review()]
        def responseEntity = new ResponseEntity(reviewList, HttpStatus.OK)


        when:
        def result = reviewController.getReviewByCriteria(pageNumber, reviewCriteria)

        then:
        1 * this.reviewService.findByCriteria(reviewCriteria, pageNumber) >> reviewList
        result == responseEntity
    }
}