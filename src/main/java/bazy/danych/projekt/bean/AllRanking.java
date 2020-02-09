package bazy.danych.projekt.bean;

public class AllRanking {

    private String name;
    private String points;
    private String numerOfWins;
    private String numberOfLosses;
    private String league;

    public AllRanking() {

    }

    public AllRanking(String name, String points, String numerOfWins, String numberOfLosses, String league) {
        this.name = name;
        this.points = points;
        this.numerOfWins = numerOfWins;
        this.numberOfLosses = numberOfLosses;
        this.league = league;
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

    public String getNumerOfWins() {
        return numerOfWins;
    }

    public void setNumerOfWins(String numerOfWins) {
        this.numerOfWins = numerOfWins;
    }

    public String getNumberOfLosses() {
        return numberOfLosses;
    }

    public void setNumberOfLosses(String numberOfLosses) {
        this.numberOfLosses = numberOfLosses;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    @Override
    public String toString() {
        return "AllRanking{" +
                "name='" + name + '\'' +
                ", points='" + points + '\'' +
                ", numerOfWins='" + numerOfWins + '\'' +
                ", numberOfLosses='" + numberOfLosses + '\'' +
                ", league='" + league + '\'' +
                '}';
    }
}
