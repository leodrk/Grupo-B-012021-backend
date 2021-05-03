package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity(name ="Review")
public class Review {

    @Id
    @GeneratedValue
    private int id;
    private String shortText;
    private String longText;
    private boolean spoilerAlert;
    private Date date;
    private String origin;
    private String platform;
    private String nick;
    private String language;
    private String country;
    private String city;
    private int likes;
    private int dislikes;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "review_id")
    private List<Report> reports;

    public Review() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public boolean isSpoilerAlert() {
        return spoilerAlert;
    }

    public void setSpoilerAlert(boolean spoilerAlert) {
        this.spoilerAlert = spoilerAlert;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getLongText() { return longText; }

    public void setLongText(String longText) { this.longText = longText; }

    public String getPlatform() { return platform; }

    public void setPlatform(String platform) { this.platform = platform; }

    public String getNick() { return nick; }

    public void setNick(String nick) { this.nick = nick; }

    public String getLanguage() { return language; }

    public void setLanguage(String language) { this.language = language; }

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public int getLikes() { return likes; }

    public void setLikes(int likes) { this.likes = likes; }

    public int getDislikes() { return dislikes; }

    public void setDislikes(int dislikes) { this.dislikes = dislikes; }

    public List<Report> getReports() { return reports; }

    public void setReports(List<Report> reports) { this.reports = reports; }

}
