package bazy.danych.projekt.bean;

public class PlayerGame {

    private String id_player;
    private String id_game;

    public PlayerGame() {

    }

    public PlayerGame(String id_player, String id_game) {
        this.id_player = id_player;
        this.id_game = id_game;
    }

    public String getId_player() {
        return id_player;
    }

    public void setId_player(String id_player) {
        this.id_player = id_player;
    }

    public String getId_game() {
        return id_game;
    }

    public void setId_game(String id_game) {
        this.id_game = id_game;
    }

    @Override
    public String toString() {
        return "PlayerGame{" +
                "id_player='" + id_player + '\'' +
                ", id_game='" + id_game + '\'' +
                '}';
    }
}
