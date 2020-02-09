package bazy.danych.projekt.dao.impl;

import bazy.danych.projekt.bean.TeamInLeague;
import bazy.danych.projekt.dao.TeamInLeagueDao;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TeamInLeagueDaoImpl extends ConnectDao implements TeamInLeagueDao {
    @Override
    public int addTeamInLeague(TeamInLeague teamInLeague) {
        return connector.insert(prepareSQLToAdd(teamInLeague));
    }

    @Override
    public boolean checkIfTeamIsInLeague(String teamId, String leagueId) {
        List<TeamInLeague> teamInLeagueList = getByLeagueId(leagueId);

        for (TeamInLeague team : teamInLeagueList) {
            if (team.getId_team().equals(teamId))
                return true;
        }

        return false;
    }

    @Override
    public TeamInLeague getByTeamId(String id) {
        Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        TeamInLeague toReturn = null;

        try {
            preparedStatement = connection.prepareStatement(prepareSQLForTeamId(id), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            toReturn = mapResultSetToTeamInLeagueList(resultSet).get(0);
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to get team league by team id ");
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
    public List<TeamInLeague> getByLeagueId(String id) {
        Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<TeamInLeague> toReturn = null;

        try {
            preparedStatement = connection.prepareStatement(prepareSQLForLeagueId(id), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            toReturn = mapResultSetToTeamInLeagueList(resultSet);
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to get team-league list by id ");
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

    private List<TeamInLeague> mapResultSetToTeamInLeagueList(ResultSet resultSet) {
        List<TeamInLeague> teamInLeagueList = new ArrayList<>();

        while (true) {
            try {
                if (!resultSet.next()) break;

                TeamInLeague teamInLeague = new TeamInLeague();
                teamInLeague.setId_league(resultSet.getString("id_liga"));
                teamInLeague.setId_team(resultSet.getString("id_druzyna"));
                teamInLeague.setPoints(resultSet.getString("punkty"));
                teamInLeague.setNumberOfWins(resultSet.getString("liczba_wygranych"));
                teamInLeague.setNumberOfWins(resultSet.getString("liczba_przegranych"));

                teamInLeagueList.add(teamInLeague);
            } catch (SQLException e) {
                System.out.println("Error has occurred while mapping result set to team in league list ");
                e.printStackTrace();
            }
        }

        return teamInLeagueList;
    }

    private String prepareSQLForTeamId(String id) {
        return "SELECT * FROM esport.Druzyna_w_lidze WHERE id_druzyna='" + id + "';";
    }

    private String prepareSQLForLeagueId(String id) {
        return "SELECT * FROM esport.Druzyna_w_lidze WHERE id_liga='" + id + "';";
    }

    private String prepareSQLToAdd(TeamInLeague teamInLeague) {
        return "INSERT INTO esport.Druzyna_w_Lidze VALUES (" +
                teamInLeague.getId_team() + ", " +
                teamInLeague.getId_league() + ", " +
                teamInLeague.getPoints() + ", " +
                teamInLeague.getNumberOfWins() + ", " +
                teamInLeague.getNumberOfLosses() + ");";
    }
}
