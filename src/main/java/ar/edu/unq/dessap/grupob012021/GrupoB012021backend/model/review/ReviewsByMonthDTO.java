package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review;

import java.util.ArrayList;
import java.util.List;

public class ReviewsByMonthDTO {

    private List<String> months;

    private List<Integer> reviewAmount;

    public List<String> getMonths() { return months; }

    public void setMonths(List<String> months) { this.months = months; }

    public List<Integer> getReviewAmount() { return reviewAmount; }

    public void setReviewAmount(List<Integer> reviewAmount) { this.reviewAmount = reviewAmount; }

    public void addMonth(String month){
        if (this.months == null){
            this.months = new ArrayList<>();
        }
        this.months.add(month);
    }

    public void addReviewAmount(Integer reviewAmount){
        if (this.reviewAmount == null){
            this.reviewAmount = new ArrayList<>();
        }
        this.reviewAmount.add(reviewAmount);
    }
}
