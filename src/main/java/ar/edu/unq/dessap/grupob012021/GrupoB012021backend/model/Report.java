package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model;

public class Report {

    private int reviewId;
    private Reason reason;
    private int reportAmount;

    public Report() {}

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

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
}

enum Reason {
    SPOIL,
    OFENSIVE,
    NONSENSE
}