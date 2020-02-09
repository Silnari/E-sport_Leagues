package bazy.danych.projekt.dao.impl;

import bazy.danych.projekt.bean.Team;
import bazy.danych.projekt.dao.TeamDao;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TeamDaoImpl extends ConnectDao implements TeamDao {
//    @Override
//    public int deleteTeamByName(String name) {
//        return 0;
//    }

    @Override
    public int addTeam(Team team) {
        return connector.insert(prepareSQLToAddTeam(team));
    }


    @Override
    public Team getTeamByName(String name) {
        Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Team toReturn = null;

        try {
            preparedStatement = connection.prepareStatement(prepareSQLForName(name), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            toReturn = mapResultSetToTeamList(resultSet).get(0);
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to get team list ");
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
    public List<Team> getAllTeams() {
        Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Team> toReturn = null;

        try {
            preparedStatement = connection.prepareStatement(prepareSQLForAll(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            toReturn = mapResultSetToTeamList(resultSet);
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to get team list ");
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

    private List<Team> mapResultSetToTeamList(ResultSet resultSet) {
        List<Team> teamList = new ArrayList<>();

        while (true) {
            try {
                if (!resultSet.next()) break;

                Team team = new Team();
                team.setId(resultSet.getString("id_druzyna"));
                team.setName(resultSet.getString("nazwa"));
                team.setCreationDate(resultSet.getString("data_stworzenia"));
                team.setNumberOfMatches(resultSet.getString("liczba_meczy"));

                teamList.add(team);
            } catch (SQLException e) {
                System.out.println("Error has occurred while mapping result set to game list ");
                e.printStackTrace();
            }
        }

        return teamList;
    }

    private String prepareSQLForName(String name) {
        return "SELECT * FROM esport.Druzyna WHERE nazwa='" + name + "';";
    }

    private String prepareSQLForAll() {
        return "SELECT * FROM esport.Druzyna;";
    }

    private String prepareSQLToAddTeam(Team team) {
        return "INSERT INTO esport.Druzyna VALUES (" +
                "(SELECT id_druzyna FROM esport.Druzyna ORDER BY id_druzyna DESC LIMIT(1)) + 1, '" +
                team.getName() + "', '" +
                team.getCreationDate() + "', " +
                team.getNumberOfMatches() + ");";
    }

//    private String prepareSQLToDeleteTeam(String name) {
//        return "DELETE FROM esport.Gracz_w_druzynie WHERE id_druzyna = " +
//    }
}
