package bazy.danych.projekt.bean;

public class Match {

    private String id;
    private String idGame;
    private String gameName;
    private String idTeam1;
    private String nameTeam1;
    private String idTeam2;
    private String nameTeam2;
    private String team1Points;
    private String team2Points;
    private String date;
    private String leagueName;
    private String tournamentName;

    public Match(String id, String idGame, String gameName, String idTeam1, String nameTeam1, String idTeam2, String nameTeam2, String team1Points, String team2Points, String date, String leagueName, String tournamentName) {
        this.id = id;
        this.idGame = idGame;
        this.gameName = gameName;
        this.idTeam1 = idTeam1;
        this.nameTeam1 = nameTeam1;
        this.idTeam2 = idTeam2;
        this.nameTeam2 = nameTeam2;
        this.team1Points = team1Points;
        this.team2Points = team2Points;
        this.date = date;
        this.leagueName = leagueName;
        this.tournamentName = tournamentName;
    }

    public Match() {

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

    public String getIdTeam1() {
        return idTeam1;
    }

    public void setIdTeam1(String idTeam1) {
        this.idTeam1 = idTeam1;
    }

    public String getIdTeam2() {
        return idTeam2;
    }

    public void setIdTeam2(String idTeam2) {
        this.idTeam2 = idTeam2;
    }

    public String getTeam1Points() {
        return team1Points;
    }

    public void setTeam1Points(String team1Points) {
        this.team1Points = team1Points;
    }

    public String getTeam2Points() {
        return team2Points;
    }

    public void setTeam2Points(String team2Points) {
        this.team2Points = team2Points;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getNameTeam1() {
        return nameTeam1;
    }

    public void setNameTeam1(String nameTeam1) {
        this.nameTeam1 = nameTeam1;
    }

    public String getNameTeam2() {
        return nameTeam2;
    }

    public void setNameTeam2(String nameTeam2) {
        this.nameTeam2 = nameTeam2;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id='" + id + '\'' +
                ", idGame='" + idGame + '\'' +
                ", gameName='" + gameName + '\'' +
                ", idTeam1='" + idTeam1 + '\'' +
                ", nameTeam1='" + nameTeam1 + '\'' +
                ", idTeam2='" + idTeam2 + '\'' +
                ", nameTeam2='" + nameTeam2 + '\'' +
                ", team1Points='" + team1Points + '\'' +
                ", team2Points='" + team2Points + '\'' +
                ", date='" + date + '\'' +
                ", leagueName='" + leagueName + '\'' +
                ", tournamentName='" + tournamentName + '\'' +
                '}';
    }
}
