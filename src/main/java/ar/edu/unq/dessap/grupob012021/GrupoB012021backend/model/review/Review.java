package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.content.Content;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.report.Report;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name ="Review")
public class Review implements Serializable {

    @Id
    @GeneratedValue
    private int id;
    private String type;
    private String shortText;
    private String longText;
    private boolean spoilerAlert;
    @NotNull
    private Date date;
    private String origin;
    private String platform;
    private String userName;
    private String language;
    private String country;
    private String city;
    private int likes = 0;
    private int dislikes = 0;
    @NotNull
    private int rating;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "review")
    private List<Report> reports;
    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    public Review() {}

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getShortText() { return shortText; }

    public void setShortText(String shortText) { this.shortText = shortText; }

    public boolean isSpoilerAlert() { return spoilerAlert; }

    public void setSpoilerAlert(boolean spoilerAlert) { this.spoilerAlert = spoilerAlert; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public String getOrigin() { return origin; }

    public void setOrigin(String origin) { this.origin = origin; }

    public String getLongText() { return longText; }

    public void setLongText(String longText) { this.longText = longText; }

    public String getPlatform() { return platform; }

    public void setPlatform(String platform) { this.platform = platform; }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

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

    public Content getContent() { return content; }

    public void setContent(Content content) { this.content = content; }

    public int getRating() { return rating; }

    public void setRating(int rating) { this.rating = rating; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }


}
