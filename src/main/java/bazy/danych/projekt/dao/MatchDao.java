package bazy.danych.projekt.dao;

import bazy.danych.projekt.bean.Match;

import java.util.List;

public interface MatchDao {

    Match getMatchById(String id);

    int addLeagueMatch(Match match);

    int addTournamentMatch(Match match);

    List<Match> getByLeagueId(String id);

    List<Match> getByTournamentId(String id);

    List<Match> getAllMatches();
}
