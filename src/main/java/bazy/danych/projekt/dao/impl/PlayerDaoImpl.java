package bazy.danych.projekt.dao.impl;

import bazy.danych.projekt.bean.Player;
import bazy.danych.projekt.dao.PlayerDao;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PlayerDaoImpl extends ConnectDao implements PlayerDao {

    @Override
    public int deletePlayerById(String id) {
        return connector.insert(prepareSQLToDeletePlayer(id));
    }

    @Override
    public int addPlayer(Player player) {
        return connector.insert(prepareSQLQueryToAddPlayer(player));
    }

    @Override
    public Player getPlayerByNickname(String nickname) {
        Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Player toReturn = null;

        try {
            preparedStatement = connection.prepareStatement(prepareSQLQueryForNickname(nickname), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            toReturn = mapResultSetToPlayerList(resultSet).get(0);
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to get player by nickname ");
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
    public Player getPlayerById(String id) {
        Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Player toReturn = null;

        try {
            preparedStatement = connection.prepareStatement(prepareSQLQueryForId(id), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            toReturn = mapResultSetToPlayerList(resultSet).get(0);
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to get player by id ");
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
    public List<Player> getAllPlayers() {
        Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Player> toReturn = null;

        try {
            preparedStatement = connection.prepareStatement(prepareSQLQueryForAll(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            toReturn = mapResultSetToPlayerList(resultSet);
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to get player by nickname ");
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

    private List<Player> mapResultSetToPlayerList(ResultSet resultSet) {
        List<Player> playerList = new ArrayList<>();

        while (true) {
            try {
                if (!resultSet.next()) break;

                Player player = new Player();
                player.setId(resultSet.getString("id_gracz"));
                player.setNickname(resultSet.getString("nickname"));
                player.setName(resultSet.getString("imie"));
                player.setSurname(resultSet.getString("nazwisko"));
                player.setAge(Integer.parseInt(resultSet.getString("wiek")));
                player.setCountry(resultSet.getString("kraj"));

                playerList.add(player);
            } catch (SQLException e) {
                System.out.println("Error has occurred while mapping result set to player list ");
                e.printStackTrace();
            }
        }

        return playerList;
    }

    private String prepareSQLQueryForNickname(String nickname) {
        return "SELECT * FROM esport.Gracz WHERE nickname='" + nickname + "';";
    }

    private String prepareSQLQueryForId(String id) {
        return "SELECT * FROM esport.Gracz WHERE id_gracz=" + id + ";";
    }

    private String prepareSQLQueryForAll() {
        return "SELECT * FROM esport.Gracz;";
    }

    private String prepareSQLQueryToAddPlayer(Player player) {
        return "INSERT INTO esport.Gracz VALUES (" +
                "(SELECT id_gracz FROM esport.Gracz ORDER BY id_gracz DESC LIMIT(1)) + 1, '" +
                player.getNickname() + "', '" +
                player.getName() + "', '" +
                player.getSurname() + "', " +
                player.getAge() + ", '" +
                player.getCountry() + "');";
    }

    private String prepareSQLToDeletePlayer(String id) {
        return "DELETE FROM esport.Gracz_w_druzynie WHERE id_gracz = " + id + ";" +
                "DELETE FROM esport.Gracz WHERE id_gracz=" + id + ";";
    }
}
