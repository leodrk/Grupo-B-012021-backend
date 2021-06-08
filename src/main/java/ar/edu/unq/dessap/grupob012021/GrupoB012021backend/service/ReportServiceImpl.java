package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.Reason;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.Report;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private ReviewService reviewService;

    public void reportReview (int reviewId, int reason) throws NoSuchElementException {
        Review review = reviewService.findById(reviewId);
        Report report;
        Optional<Report> optionalReports = review
                .getReports()
                .stream()
                .filter(r -> reason == r.getReason().ordinal())
                .findFirst();
        if (optionalReports.isPresent()){
            report = optionalReports.get();
            report.setReportAmount(report.getReportAmount() + 1);
        }
        else{
            report = new Report(review, Reason.values()[reason]);
        }
        reportRepository.save(report);
    }
}