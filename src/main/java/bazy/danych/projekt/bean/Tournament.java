package bazy.danych.projekt.bean;

public class Tournament {

    private String id;
    private String idGame;
    private String gameName;
    private String name;
    private String numberOfTeams;
    private String rule;


    public Tournament(String id, String idGame, String gameName, String name, String numberOfTeams, String rule) {
        this.id = id;
        this.idGame = idGame;
        this.gameName = gameName;
        this.name = name;
        this.numberOfTeams = numberOfTeams;
        this.rule = rule;
    }

    public Tournament() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdGame() {
        return idGame;
    }

    public void setIdGame(String idGame) {
        this.idGame = idGame;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(String numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "id='" + id + '\'' +
                ", idGame='" + idGame + '\'' +
                ", gameName='" + gameName + '\'' +
                ", name='" + name + '\'' +
                ", numberOfTeams='" + numberOfTeams + '\'' +
                ", rule='" + rule + '\'' +
                '}';
    }
}
