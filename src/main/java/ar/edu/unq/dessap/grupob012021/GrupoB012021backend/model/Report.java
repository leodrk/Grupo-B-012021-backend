package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name ="Report")
public class Report {

    @Id
    @GeneratedValue
    private int id;
    private Reason reason;
    private int reportAmount;

    public Report() {}


    public int getId() { return id; }

    public void setId(int id) { this.id = id;}

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public int getReportAmount() {
        return reportAmount;
    }

    public void setReportAmount(int reportAmount) {
        this.reportAmount = reportAmount;
    }
}

enum Reason {
    SPOIL,
    OFENSIVE,
    NONSENSE
}