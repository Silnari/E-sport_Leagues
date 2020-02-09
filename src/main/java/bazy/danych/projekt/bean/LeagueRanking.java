package bazy.danych.projekt.bean;

public class LeagueRanking {

    private String name;
    private String points;
    private String numberOfWins;
    private String numberOfLosses;

    public LeagueRanking() {

    }

    public LeagueRanking(String name, String points, String numberOfWins, String numberOfLosses) {
        this.name = name;
        this.points = points;
        this.numberOfWins = numberOfWins;
        this.numberOfLosses = numberOfLosses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getNumberOfWins() {
        return numberOfWins;
    }

    public void setNumberOfWins(String numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    public String getNumberOfLosses() {
        return numberOfLosses;
    }

    public void setNumberOfLosses(String numberOfLosses) {
        this.numberOfLosses = numberOfLosses;
    }

    @Override
    public String toString() {
        return "LeagueRanking{" +
                "name='" + name + '\'' +
                ", points='" + points + '\'' +
                ", numberOfWins='" + numberOfWins + '\'' +
                ", numberOfLosses='" + numberOfLosses + '\'' +
                '}';
    }
}
