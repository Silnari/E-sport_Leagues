package bazy.danych.projekt.dao;

import bazy.danych.projekt.bean.Team;

import java.util.List;

public interface TeamDao {

    Team getTeamByName(String name);

    int addTeam(Team team);

//    int deleteTeamByName(String name);

    List<Team> getAllTeams();
}
