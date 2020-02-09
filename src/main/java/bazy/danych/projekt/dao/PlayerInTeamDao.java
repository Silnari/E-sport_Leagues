package bazy.danych.projekt.dao;

import bazy.danych.projekt.bean.PlayerInTeam;

import java.util.List;

public interface PlayerInTeamDao {

    List<PlayerInTeam> getByTeamId(String id);

    int addPlayerInTeam(PlayerInTeam playerInTeam);

    boolean checkIfPlayerIsInTeam(String playerId, String teamId);

    List<PlayerInTeam> getByPlayerId(String id);
}
