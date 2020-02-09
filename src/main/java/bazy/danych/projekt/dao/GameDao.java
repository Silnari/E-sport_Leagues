package bazy.danych.projekt.dao;

import bazy.danych.projekt.bean.Game;

import java.util.List;

public interface GameDao {

    Game getGameByName(String name);

    Game getGameById(String id);

    int addGame(Game game);

    List<Game> getAllGames();
}
