package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.content;


import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Content {

    @Id
    @GeneratedValue
    private int id;
    private String title;
    @Enumerated(EnumType.STRING)
    private TitleType titleType;
    private boolean isAdult;
    private int startYear;
    private int endYear;
    private int runtimeMinutes;
    private boolean isOriginalTitle;
    private String region;
    @ElementCollection(targetClass=Genre.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="content_genre")
    @Column(name="genre")
    private List<Genre> genres;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "content_id")
    private List<Episode> episodes;
    @JsonIgnore
    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
    @ElementCollection
    private List<String> subscribers;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public TitleType getTitleType() { return titleType; }

    public void setTitleType(TitleType titleType) { this.titleType = titleType; }

    public boolean isAdult() { return isAdult; }

    public void setAdult(boolean adult) { isAdult = adult; }

    public int getStartYear() { return startYear; }

    public void setStartYear(int startYear) { this.startYear = startYear; }

    public int getEndYear() { return endYear; }

    public void setEndYear(int endYear) { this.endYear = endYear; }

    public int getRuntimeMinutes() { return runtimeMinutes; }

    public void setRuntimeMinutes(int runtimeMinutes) { this.runtimeMinutes = runtimeMinutes; }

    public boolean isOriginalTitle() { return isOriginalTitle; }

    public void setOriginalTitle(boolean originalTitle) { isOriginalTitle = originalTitle; }

    public String getRegion() { return region; }

    public void setRegion(String region) { this.region = region; }

    public List<Genre> getGenres() { return genres; }

    public void setGenres(List<Genre> genres) { this.genres = genres; }

    public List<Episode> getEpisodes() { return episodes; }

    public void setEpisodes(List<Episode> episodes) { this.episodes = episodes; }

    public List<Review> getReviews() { return reviews; }

    public void setReviews(List<Review> reviews) { this.reviews = reviews; }

    public List<String> getSubscribers() { return subscribers; }

    public void setSubscribers(List<String> subscribers) { this.subscribers = subscribers; }

    public void addSubscriber(String subscriber){
        this.subscribers.add(subscriber);
    }
}

