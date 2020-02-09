package bazy.danych.projekt.bean;

public class PlayerInTeam {

    private String idPlayer;
    private String idTeam;
    private String dateFrom;
    private String dateTo;
    private String playerName;
    private String teamName;

    public PlayerInTeam() {

    }

    public PlayerInTeam(String idPlayer, String idTeam, String dateFrom, String dateTo, String playerName, String teamName) {
        this.idPlayer = idPlayer;
        this.idTeam = idTeam;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.playerName = playerName;
        this.teamName = teamName;
    }

    public String getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(String idPlayer) {
        this.idPlayer = idPlayer;
    }

    public String getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(String idTeam) {
        this.idTeam = idTeam;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public String toString() {
        return "PlayerInTeam{" +
                "idPlayer='" + idPlayer + '\'' +
                ", idTeam='" + idTeam + '\'' +
                ", dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", playerName='" + playerName + '\'' +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}
