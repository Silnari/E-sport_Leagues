package bazy.danych.projekt.dao.impl;

import bazy.danych.projekt.bean.Tournament;
import bazy.danych.projekt.dao.TournamentDao;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TournamentDaoImpl extends ConnectDao implements TournamentDao {
    @Override
    public int addTournament(Tournament tournament) {
        return connector.insert(prepareSQLToAdd(tournament));
    }

    @Override
    public Tournament getTournamentByName(String name) {
        Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Tournament toReturn = null;

        try {
            preparedStatement = connection.prepareStatement(prepareSQLForName(name), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            toReturn = mapResultSetToTournamentList(resultSet).get(0);
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to get tournament by name ");
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error has occurred while trying to close connection ");
                e.printStackTrace();
            }
        }

        return toReturn;
    }

    @Override
    public Tournament getTournamentById(String id) {
        Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Tournament toReturn = null;

        try {
            preparedStatement = connection.prepareStatement(prepareSQLForId(id), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            toReturn = mapResultSetToTournamentList(resultSet).get(0);
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to get tournament by id ");
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error has occurred while trying to close connection ");
                e.printStackTrace();
            }
        }

        return toReturn;
    }

    @Override
    public List<Tournament> getAllTournaments() {
        Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Tournament> toReturn = null;

        try {
            preparedStatement = connection.prepareStatement(prepareSQLForAll(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            toReturn = mapResultSetToTournamentList(resultSet);
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to get tournament list ");
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error has occurred while trying to close connection ");
                e.printStackTrace();
            }
        }

        return toReturn;
    }

    private List<Tournament> mapResultSetToTournamentList(ResultSet resultSet) {
        List<Tournament> tournamentList = new ArrayList<>();

        while (true) {
            try {
                if (!resultSet.next()) break;

                Tournament tournament = new Tournament();
                tournament.setId(resultSet.getString("id_turniej"));
                tournament.setIdGame(resultSet.getString("id_gra"));
                tournament.setGameName(resultSet.getString("nazwa_gry"));
                tournament.setName(resultSet.getString("nazwa"));
                tournament.setNumberOfTeams(resultSet.getString("liczba_druzyn"));
                tournament.setRule(resultSet.getString("opis"));

                tournamentList.add(tournament);
            } catch (SQLException e) {
                System.out.println("Error has occurred while mapping result set to tournament list ");
                e.printStackTrace();
            }
        }

        return tournamentList;
    }

    private String prepareSQLForName(String name) {
        return "SELECT * FROM esport.turniej_pelne_nazwy WHERE nazwa='" + name + "';";
    }

    private String prepareSQLForId(String id) {
        return "SELECT * FROM esport.turniej_pelne_nazwy WHERE id_turniej='" + id + "';";
    }

    private String prepareSQLForAll() {
        return "SELECT * FROM esport.turniej_pelne_nazwy;";
    }

    private String prepareSQLToAdd(Tournament tournament) {
        return "INSERT INTO esport.Turniej VALUES (" +
                "(SELECT id_turniej FROM esport.Turniej ORDER BY id_turniej DESC LIMIT(1)) + 1, " +
                tournament.getIdGame() + ", '" +
                tournament.getName() + "', " +
                tournament.getNumberOfTeams() + ", " +
                tournament.getRule() + ");";
    }
}
