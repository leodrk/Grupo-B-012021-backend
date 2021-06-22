package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.SubscriberLog;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.content.Content;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.ReviewCriteriaDTO;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.ContentRepository;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.SubscriberLogRepository;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.reviewRepository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public void save(Content content){
        contentRepository.save(content);
    }
}