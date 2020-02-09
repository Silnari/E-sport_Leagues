package bazy.danych.projekt.dao;

import bazy.danych.projekt.bean.League;

import java.util.List;

public interface LeagueDao {

    League getLeagueByName(String name);

    League getLeagueById(String id);

    int addLeague(League league);

    List<League> getAllLeagues();
}
