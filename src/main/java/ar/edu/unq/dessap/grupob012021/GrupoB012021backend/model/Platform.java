package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name ="Platform")
public class Platform {

    @Id
    private String name;
    private String endpoint;

    public Platform (){}

    public Platform (String name, String endpoint){
        this.name = name;
        this.endpoint = endpoint;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEndpoint() { return endpoint; }

    public void setEndpoint(String endpoint) { this.endpoint = endpoint; }



}
