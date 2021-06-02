package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.Content
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ReviewServiceImplSpec extends Specification{

    @Autowired
    private ReviewServiceImpl reviewServiceImpl


    def "cuando se llama a save con un review con id 1, el mismo debe estar en base de datos"(){
        given:
        Review review = new Review();
        review.setId(1)

        when:
        reviewServiceImpl.save(review);

        then:
        reviewServiceImpl.findById(1).getId() == review.getId()
    }

    def "cuando se likea un review con 3 likes, el mismo pasa a tener 4"(){
        given:
        Review review = new Review();
        review.setId(1)
        review.setLikes(3)

        when:
        reviewServiceImpl.save(review)
        reviewServiceImpl.likeReview(review.getId())

        then:
        reviewServiceImpl.findById(1).getLikes() == 4
    }

    def "likear un review inexistente debe lanzar NoSuchElementException"(){
        given:

        when:
        reviewServiceImpl.likeReview(6)

        then:
        thrown NoSuchElementException

    }

    def "dislikear un review inexistente debe lanzar NoSuchElementException"(){
        given:

        when:
        reviewServiceImpl.dislikeReview(8)

        then:
        thrown NoSuchElementException

    }

    def "cuando se dislikea un review con 3 dislikes, el mismo pasa a tener 4"(){
        given:
        Review review = new Review();
        review.setId(1)
        review.setDislikes(3)

        when:
        reviewServiceImpl.save(review)
        reviewServiceImpl.dislikeReview(review.getId())

        then:
        reviewServiceImpl.findById(1).getDislikes() == 4
    }

    def "cuando se llama a findByContentId con un content id asociado a un review se debe obtener el mismo"(){
        given:
        Review review = new Review();
        review.setId(2)
        Content content = new Content();
        content.setId(1)
        review.setContent(content)

        when:
        reviewServiceImpl.save(review)

        then:
        reviewServiceImpl.findByContentId(1).get(0).getId() == review.getId()
        reviewServiceImpl.findByContentId(1).size() == 1
    }
}
