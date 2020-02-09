package bazy.danych.projekt.bean;

public class TeamInLeague {

    private String id_team;
    private String id_league;
    private String points;
    private String numberOfWins;
    private String numberOfLosses;

    public TeamInLeague() {

    }

    public TeamInLeague(String id_team, String id_league, String points, String numberOfWins, String numberOfLosses) {
        this.id_team = id_team;
        this.id_league = id_league;
        this.points = points;
        this.numberOfWins = numberOfWins;
        this.numberOfLosses = numberOfLosses;
    }

    public String getId_team() {
        return id_team;
    }

    public void setId_team(String id_team) {
        this.id_team = id_team;
    }

    public String getId_league() {
        return id_league;
    }

    public void setId_league(String id_league) {
        this.id_league = id_league;
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
        return "TeamInLeague{" +
                "id_team='" + id_team + '\'' +
                ", id_league='" + id_league + '\'' +
                ", points='" + points + '\'' +
                ", numberOfWins='" + numberOfWins + '\'' +
                ", numberOfLosses='" + numberOfLosses + '\'' +
                '}';
    }
}
