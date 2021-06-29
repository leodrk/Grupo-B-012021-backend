package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.ReviewCriteriaDTO;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.content.Content;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.content.ContentDTO;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.ContentRepository;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.reviewRepository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    ContentRepository contentRepository;
    @Autowired
    ReviewRepository reviewRepository;

    public List<Content> findByReviews(ReviewCriteriaDTO reviewCriteriaDTO) {
        List<Review> reviews = reviewRepository.findByCriteria(reviewCriteriaDTO);
        return contentRepository.findDistinctByReviewsIn(reviews);
    }

    public Optional<Content> findById(int contentId) {
        return contentRepository.findById(contentId);
    }

    @Cacheable(value = "contentInfo")
    public ContentDTO getContentInfo(int contentId) throws NoSuchElementException{
        System.out.println("obteniendo informacion de contenido");
        Optional<Content> optionalContent = contentRepository.findById(contentId);
        if (optionalContent.isPresent()){
            var content = optionalContent.get();
            var contentDTO = new ContentDTO(content);
            contentDTO.setReviewCount(content.getReviews().size());
            contentDTO.setAverageRating(getAverageRating(content.getReviews()));
            return contentDTO;
        }
        throw new NoSuchElementException();
    }

    public void save(Content content){
        contentRepository.save(content);
    }



    private double getAverageRating (List<Review> reviews){
        return reviews.stream().mapToInt(r -> r.getRating()).average().orElse(0);
    }

}