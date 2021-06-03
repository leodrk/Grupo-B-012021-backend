package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.Content;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.ReviewCriteriaDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContentService {

    List<Content> findByReviews(ReviewCriteriaDTO reviewCriteriaDTO);
}
