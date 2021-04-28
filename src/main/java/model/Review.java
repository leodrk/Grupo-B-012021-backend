package model;

import java.util.Date;

public class Review {

    private int id;
    private String shortText;
    private boolean spoilerAlert;
    private Date date;
    private String origin;
    private User user;


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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
