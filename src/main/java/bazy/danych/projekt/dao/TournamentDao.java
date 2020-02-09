package bazy.danych.projekt.dao;

import bazy.danych.projekt.bean.Tournament;

import java.util.List;

public interface TournamentDao {

    Tournament getTournamentByName(String name);

    Tournament getTournamentById(String id);

    int addTournament(Tournament tournament);

    List<Tournament> getAllTournaments();
}
