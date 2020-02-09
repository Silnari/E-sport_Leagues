package bazy.danych.projekt.bean;

public class League {

    private String id;
    private String idGame;
    private String gameName;
    private String name;
    private String description;
    private String numberOfTeams;

    public League(String id, String idGame, String gameName, String name, String description, String numberOfTeams) {
        this.id = id;
        this.idGame = idGame;
        this.gameName = gameName;
        this.name = name;
        this.description = description;
        this.numberOfTeams = numberOfTeams;
    }

    public League() {

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(String numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    @Override
    public String toString() {
        return "League{" +
                "id='" + id + '\'' +
                ", idGame='" + idGame + '\'' +
                ", gameName='" + gameName + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", numberOfTeams='" + numberOfTeams + '\'' +
                '}';
    }
}
