package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review;

import com.sun.istack.NotNull;

import java.util.Date;

public class ReviewCriteriaDTO {
    private String type;
    private boolean spoilerAlert;
    private String platform;
    private String language;
    private String country;
    private boolean searchByRating;
    private boolean ascending;


    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public boolean isSpoilerAlert() { return spoilerAlert; }

    public void setSpoilerAlert(boolean spoilerAlert) { this.spoilerAlert = spoilerAlert; }

    public String getPlatform() { return platform; }

    public void setPlatform(String platform) { this.platform = platform; }

    public String getLanguage() { return language; }

    public void setLanguage(String language) { this.language = language; }

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }

    public boolean isSearchByRating() { return searchByRating; }

    public void setSearchByRating(boolean searchByRating) { this.searchByRating = searchByRating; }

    public boolean isAscending() { return ascending; }

    public void setAscending(boolean ascending) { this.ascending = ascending; }


}
