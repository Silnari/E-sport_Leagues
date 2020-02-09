package bazy.danych.projekt.dao.impl;

import bazy.danych.projekt.bean.Game;
import bazy.danych.projekt.dao.GameDao;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GameDaoImpl extends ConnectDao implements GameDao {

    @Override
    public int addGame(Game game) {
        return connector.insert(prepareSQLQueryToAddGame(game));
    }

    @Override
    public Game getGameByName(String name) {
        Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Game toReturn = null;

        try {
            preparedStatement = connection.prepareStatement(prepareSQLQueryForName(name), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            toReturn = mapResultSetToGameList(resultSet).get(0);
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to get game by name ");
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
    public Game getGameById(String id) {
        Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Game toReturn = null;

        try {
            preparedStatement = connection.prepareStatement(prepareSQLQueryForId(id), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            toReturn = mapResultSetToGameList(resultSet).get(0);
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to get game by id ");
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
    public List<Game> getAllGames() {
        Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Game> toReturn = null;

        try {
            preparedStatement = connection.prepareStatement(prepareSQLQueryForAll(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            toReturn = mapResultSetToGameList(resultSet);
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to get game list ");
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

    private List<Game> mapResultSetToGameList(ResultSet resultSet) {
        List<Game> gameList = new ArrayList<>();

        while (true) {
            try {
                if (!resultSet.next()) break;

                Game game = new Game();
                game.setId(resultSet.getString("id_gra"));
                game.setName(resultSet.getString("nazwa_gry"));
                game.setDescription(resultSet.getString("opis"));

                gameList.add(game);
            } catch (SQLException e) {
                System.out.println("Error has occurred while mapping result set to game list ");
                e.printStackTrace();
            }
        }

        return gameList;
    }

    private String prepareSQLQueryForName(String name) {
        return "SELECT * FROM esport.Gra WHERE nazwa_gry='" + name + "';";
    }

    private String prepareSQLQueryForId(String id) {
        return "SELECT * FROM esport.Gra WHERE id_gra='" + id + "';";
    }

    private String prepareSQLQueryForAll() {
        return "SELECT * FROM esport.Gra;";
    }

    private String prepareSQLQueryToAddGame(Game game) {
        return "INSERT INTO esport.Gra VALUES (" +
                "(SELECT id_gra FROM esport.Gra ORDER BY id_gra DESC LIMIT(1)) + 1, '" +
                game.getName() + "', '" +
                game.getDescription() + "');";
    }
}
