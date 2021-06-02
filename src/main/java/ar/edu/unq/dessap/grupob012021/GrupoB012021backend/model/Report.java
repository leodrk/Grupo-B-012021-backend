package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review;
import javax.persistence.*;

@Entity(name ="Report")
public class Report {

    @Id
    @GeneratedValue
    private int id;
    private Reason reason;
    private int reportAmount;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "review_id")
    private Review review;

    public Report() {}

    public Report (Review review, Reason reason){
        this.reportAmount = 1;
        this.reason = reason;
        this.review = review;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id;}

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public int getReportAmount() {
        return reportAmount;
    }

    public void setReportAmount(int reportAmount) {
        this.reportAmount = reportAmount;
    }

    public Review getReview() { return review; }

    public void setReview(Review review) { this.review = review; }

}

