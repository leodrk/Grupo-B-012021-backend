package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.content;

import java.io.Serializable;

public class ContentDTO implements Serializable {

    private String title;
    private TitleType titleType;
    private String region;
    private int reviewCount;
    private double averageRating;

    public ContentDTO (){}

    public ContentDTO (Content content){
        this.title = content.getTitle();
        this.titleType = content.getTitleType();
        this.region = content.getRegion();
    }


    public double getAverageRating() { return averageRating; }

    public void setAverageRating(double averageRating) { this.averageRating = averageRating; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public TitleType getTitleType() { return titleType; }

    public void setTitleType(TitleType titleType) { this.titleType = titleType; }

    public String getRegion() { return region; }

    public void setRegion(String region) { this.region = region; }

    public int getReviewCount() { return reviewCount; }

    public void setReviewCount(int reviewCount) { this.reviewCount = reviewCount; }
}
