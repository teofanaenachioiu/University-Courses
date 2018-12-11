package domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Tichet implements HasID<Integer> {
    private Integer tichetId;
    private String description;
    private User openedBy;
    private LocalDate date;
    private Double cost;
    private DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public Tichet(Integer tichetId, String description, User openedBy, LocalDate date, Double cost) {
        this.tichetId=tichetId;
        this.description = description;
        this.openedBy = openedBy;
        this.date = date;
        this.cost = cost;
    }

    @Override
    public Integer getID() {
        return this.tichetId;
    }

    @Override
    public void setID(Integer integer) {
        this.tichetId=integer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOpenedBy() {
        return openedBy;
    }

    public void setOpenedBy(User openedBy) {
        this.openedBy = openedBy;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return tichetId + "," + description + "," + openedBy + "," + date + "," + cost;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }
}
