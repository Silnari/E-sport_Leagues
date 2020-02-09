package bazy.danych.projekt.dao;

import bazy.danych.projekt.bean.TeamInLeague;

import java.util.List;

public interface TeamInLeagueDao {

    TeamInLeague getByTeamId(String id);

    int addTeamInLeague(TeamInLeague teamInLeague);

    boolean checkIfTeamIsInLeague(String teamId, String leagueId);

    List<TeamInLeague> getByLeagueId(String id);
}
