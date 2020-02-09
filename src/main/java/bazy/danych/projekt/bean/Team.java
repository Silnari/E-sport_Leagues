package bazy.danych.projekt.bean;

public class Team {

    private String id;
    private String name;
    private String creationDate;
    private String numberOfMatches;

    public Team(String id, String name, String creationDate, String numberOfMatches) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.numberOfMatches = numberOfMatches;
    }

    public Team() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getNumberOfMatches() {
        return numberOfMatches;
    }

    public void setNumberOfMatches(String numberOfMatches) {
        this.numberOfMatches = numberOfMatches;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", numberOfMatches='" + numberOfMatches + '\'' +
                '}';
    }
}
