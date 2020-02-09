package bazy.danych.projekt.dao.impl;

import bazy.danych.projekt.bean.AllRanking;
import bazy.danych.projekt.bean.LeagueRanking;
import bazy.danych.projekt.dao.RankingDao;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RankingDaoImpl extends ConnectDao implements RankingDao {

    @Override
    public List<AllRanking> getAll() {
        Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<AllRanking> toReturn = null;

        try {
            preparedStatement = connection.prepareStatement(prepareSQLQueryforAll(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            toReturn = mapResultSetToAllRankingList(resultSet);
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to get ranking list ");
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
    public List<LeagueRanking> getForLeague(String id) {
        Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<LeagueRanking> toReturn = null;

        try {
            preparedStatement = connection.prepareStatement(prepareSQLQueryforLeagueId(id), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            toReturn = mapResultSetToLeagueRankingList(resultSet);
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to get ranking list for league id ");
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

    private List<AllRanking> mapResultSetToAllRankingList(ResultSet resultSet) {
        List<AllRanking> allRankingList = new ArrayList<>();

        while (true) {
            try {
                if (!resultSet.next()) break;

                AllRanking allRanking = new AllRanking();
                allRanking.setName(resultSet.getString("nazwa"));
                allRanking.setPoints(resultSet.getString("punkty"));
                allRanking.setNumberOfLosses(resultSet.getString("l_przegranych"));
                allRanking.setNumerOfWins(resultSet.getString("l_wygranych"));
                allRanking.setLeague(resultSet.getString("liga"));

                allRankingList.add(allRanking);
            } catch (SQLException e) {
                System.out.println("Error has occurred while mapping result set to all ranking list ");
                e.printStackTrace();
            }
        }

        return allRankingList;
    }

    private List<LeagueRanking> mapResultSetToLeagueRankingList(ResultSet resultSet) {
        List<LeagueRanking> leagueRankingList = new ArrayList<>();

        while (true) {
            try {
                if (!resultSet.next()) break;

                LeagueRanking leagueRanking = new LeagueRanking();
                leagueRanking.setName(resultSet.getString("nazwa"));
                leagueRanking.setPoints(resultSet.getString("l_punktow"));
                leagueRanking.setNumberOfWins(resultSet.getString("l_wygranych"));
                leagueRanking.setNumberOfLosses(resultSet.getString("l_przegranych"));

                leagueRankingList.add(leagueRanking);
            } catch (SQLException e) {
                System.out.println("Error has occurred while mapping result set to league ranking list ");
                e.printStackTrace();
            }
        }

        return leagueRankingList;
    }

    private String prepareSQLQueryforLeagueId(String id) {
        return "SELECT * FROM esport.ranking_ligi(" + id + ");";
    }

    private String prepareSQLQueryforAll() {
        return "SELECT * FROM esport.ranking_wszystkich;";
    }
}
