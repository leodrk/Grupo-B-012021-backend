package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service;

import org.springframework.stereotype.Service;

@Service
public interface ReportService {

    void reportReview (int reviewId, int reason);
}
