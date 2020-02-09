package bazy.danych.projekt.dao.impl;

import bazy.danych.projekt.bean.PlayerInTeam;
import bazy.danych.projekt.dao.PlayerInTeamDao;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PlayerInTeamDaoImpl extends ConnectDao implements PlayerInTeamDao {

    @Override
    public int addPlayerInTeam(PlayerInTeam playerInTeam) {
        return connector.insert(prepareSQLToAdd(playerInTeam));
    }

    @Override
    public List<PlayerInTeam> getByTeamId(String id) {
        Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<PlayerInTeam> toReturn = null;

        try {
            preparedStatement = connection.prepareStatement(prepareSQLQueryForTeamId(id), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            toReturn = mapResultSetToPlayerInTeamList(resultSet);
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to get player-team list by team id");
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
    public List<PlayerInTeam> getByPlayerId(String id) {
        Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<PlayerInTeam> toReturn = null;

        try {
            preparedStatement = connection.prepareStatement(prepareSQLQueryForPlayerId(id), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            toReturn = mapResultSetToPlayerInTeamList(resultSet);
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to get player-team list by player id");
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

    public boolean checkIfPlayerIsInTeam(String playerId, String teamId) {
        List<PlayerInTeam> playerInTeamList = getByTeamId(teamId);

        for (PlayerInTeam player : playerInTeamList) {
            if (player.getIdPlayer().equals(playerId))
                return true;
        }

        return false;
    }

    private List<PlayerInTeam> mapResultSetToPlayerInTeamList(ResultSet resultSet) {
        List<PlayerInTeam> playerInTeamList = new ArrayList<>();

        while (true) {
            try {
                if (!resultSet.next()) break;

                PlayerInTeam playerInTeam = new PlayerInTeam();
                playerInTeam.setIdPlayer(resultSet.getString("id_gracz"));
                playerInTeam.setIdTeam(resultSet.getString("id_druzyna"));
                playerInTeam.setDateFrom(resultSet.getString("data_start"));
                playerInTeam.setDateTo(resultSet.getString("data_koniec"));
                playerInTeam.setPlayerName(resultSet.getString("nickname"));
                playerInTeam.setTeamName(resultSet.getString("druzyna"));

                playerInTeamList.add(playerInTeam);
            } catch (SQLException e) {
                System.out.println("Error has occurred while mapping result set to player in team list ");
                e.printStackTrace();
            }
        }

        return playerInTeamList;
    }

    private String prepareSQLQueryForPlayerId(String id) {
        return "SELECT * FROM esport.gracz_w_druzynie_pelne_nazwy WHERE id_gracz='" + id + "';";
    }

    private String prepareSQLQueryForTeamId(String id) {
        return "SELECT * FROM esport.gracz_w_druzynie_pelne_nazwy WHERE id_druzyna='" + id + "';";
    }

    private String prepareSQLToAdd(PlayerInTeam playerInTeam) {
        return "INSERT INTO esport.Gracz_w_Druzynie VALUES (" +
                "(SELECT id_gracz FROM esport.Gracz WHERE nickname='" + playerInTeam.getPlayerName() + "'), " +
                "(SELECT id_druzyna FROM esport.Druzyna WHERE nazwa='" + playerInTeam.getTeamName() + "'), '" +
                playerInTeam.getDateFrom() + "', '" +
                playerInTeam.getDateTo() + "');";
    }
}
