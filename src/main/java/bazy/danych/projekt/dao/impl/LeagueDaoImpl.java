package bazy.danych.projekt.dao.impl;

import bazy.danych.projekt.bean.League;
import bazy.danych.projekt.dao.LeagueDao;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LeagueDaoImpl extends ConnectDao implements LeagueDao {

    @Override
    public League getLeagueByName(String name) {
        Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        League toReturn = null;

        try {
            preparedStatement = connection.prepareStatement(prepareSQLForName(name), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            toReturn = mapResultSetToLeagueList(resultSet).get(0);
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to get league by name ");
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
    public int addLeague(League league) {
        return connector.insert(prepareSQLToAddLeague(league));
    }

    @Override
    public League getLeagueById(String id) {
        Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        League toReturn = null;

        try {
            preparedStatement = connection.prepareStatement(prepareSQLForId(id), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            toReturn = mapResultSetToLeagueList(resultSet).get(0);
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to get league by id ");
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
    public List<League> getAllLeagues() {
        Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<League> toReturn = null;

        try {
            preparedStatement = connection.prepareStatement(prepareSQLForAll(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            toReturn = mapResultSetToLeagueList(resultSet);
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to get league list ");
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

    private List<League> mapResultSetToLeagueList(ResultSet resultSet) {
        List<League> leagueList = new ArrayList<>();

        while (true) {
            try {
                if (!resultSet.next()) break;

                League league = new League();
                league.setId(resultSet.getString("id_liga"));
                league.setIdGame(resultSet.getString("id_gra"));
                league.setGameName(resultSet.getString("nazwa_gry"));
                league.setName(resultSet.getString("nazwa"));
                league.setDescription(resultSet.getString("opis"));
                league.setNumberOfTeams(resultSet.getString("liczba_druzyn"));

                leagueList.add(league);
            } catch (SQLException e) {
                System.out.println("Error has occurred while mapping result set to league list ");
                e.printStackTrace();
            }
        }

        return leagueList;
    }

    private String prepareSQLForName(String name) {
        return "SELECT * FROM esport.liga_pelne_nazwy WHERE nazwa='" + name + "';";
    }

    private String prepareSQLForId(String id) {
        return "SELECT * FROM esport.liga_pelne_nazwy WHERE id_liga='" + id + "';";
    }

    private String prepareSQLForAll() {
        return "SELECT * FROM esport.liga_pelne_nazwy;";
    }

    private String prepareSQLToAddLeague(League league) {
        return "INSERT INTO esport.Liga VALUES (" +
                "(SELECT id_liga FROM esport.Liga ORDER BY id_liga DESC LIMIT(1)) + 1, " +
                league.getIdGame() + ", '" +
                league.getName() + "', '" +
                league.getDescription() + "', " +
                league.getNumberOfTeams() + ");";
    }
}
