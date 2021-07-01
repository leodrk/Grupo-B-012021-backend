package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review;


public class ReviewDTO {

    private String type;
    private String shortText;
    private String longText;
    private boolean spoilerAlert;
    private String platform;
    private String origin;
    private String userName;
    private String language;
    private String country;
    private String city;
    private int rating;

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getShortText() { return shortText; }

    public void setShortText(String shortText) { this.shortText = shortText; }

    public String getLongText() { return longText; }

    public void setLongText(String longText) { this.longText = longText; }

    public boolean isSpoilerAlert() { return spoilerAlert; }

    public void setSpoilerAlert(boolean spoilerAlert) { this.spoilerAlert = spoilerAlert; }

    public String getOrigin() { return origin; }

    public void setOrigin(String origin) { this.origin = origin; }

    public String getLanguage() { return language; }

    public void setLanguage(String language) { this.language = language; }

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public int getRating() { return rating; }

    public void setRating(int rating) { this.rating = rating; }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public String getPlatform() { return platform; }

    public void setPlatform(String platform) { this.platform = platform; }
}