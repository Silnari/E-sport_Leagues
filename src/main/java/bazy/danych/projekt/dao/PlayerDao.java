package bazy.danych.projekt.dao;

import bazy.danych.projekt.bean.Player;

import java.util.List;


public interface PlayerDao {

    Player getPlayerByNickname(String nickname);

    Player getPlayerById(String id);

    int addPlayer(Player player);

    int deletePlayerById(String id);

    List<Player> getAllPlayers();
}
