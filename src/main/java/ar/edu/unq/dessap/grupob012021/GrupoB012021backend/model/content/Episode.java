package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.content;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Episode {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private int episodeNumber;
    private int season;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getEpisodeNumber() { return episodeNumber; }

    public void setEpisodeNumber(int episodeNumber) { this.episodeNumber = episodeNumber; }

    public int getSeason() { return season; }

    public void setSeason(int season) { this.season = season; }

}
