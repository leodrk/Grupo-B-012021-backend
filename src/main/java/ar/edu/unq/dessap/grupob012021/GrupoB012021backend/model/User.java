package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review;
import javax.persistence.*;
import java.util.Collection;

@Entity(name ="User")
public class User {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String lastName;
    private String platform;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private Collection<Review> reviews;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Collection<Review> getReviews() { return reviews; }

    public void setReviews(Collection<Review> reviews) { this.reviews = reviews; }
}
