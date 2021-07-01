package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review;

import javax.persistence.*;
import java.util.Date;

@Entity
public class SubscriberLog {


    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    private Review review;
    @ManyToOne
    @JoinColumn(name = "platform_id")
    private Platform platform;

    public SubscriberLog (Review review, Platform platform){
        this.review = review;
        this.platform = platform;
    }

    public SubscriberLog (){}

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Review getReview() { return review; }

    public void setReview(Review review) { this.review = review; }

    public Platform getPlatform() { return platform; }

    public void setPlatform(Platform platform) { this.platform = platform; }
}
