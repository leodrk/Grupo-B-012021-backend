package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class SubscriberLog {


    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    private Review review;
    private String platform;
    private Date date;

    public SubscriberLog (Review review, String platform){
        this.review = review;
        this.platform = platform;
        this.date = new Date();
    }

    public SubscriberLog (){}

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Review getReview() { return review; }

    public void setReview(Review review) { this.review = review; }

    public String getPlatform() { return platform; }

    public void setPlatform(String platform) { this.platform = platform; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }
}
