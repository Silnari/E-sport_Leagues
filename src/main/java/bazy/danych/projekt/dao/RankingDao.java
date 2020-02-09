package bazy.danych.projekt.dao;

import bazy.danych.projekt.bean.AllRanking;
import bazy.danych.projekt.bean.LeagueRanking;

import java.util.List;

public interface RankingDao {

    List<AllRanking> getAll();

    List<LeagueRanking> getForLeague(String id);
}
