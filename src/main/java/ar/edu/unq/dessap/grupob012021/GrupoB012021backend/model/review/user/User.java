package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.user;

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
    private String username;
    private String password;
    private String platform;
    private String token;
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

    public String getLastName() { return lastName; }

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

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

}

