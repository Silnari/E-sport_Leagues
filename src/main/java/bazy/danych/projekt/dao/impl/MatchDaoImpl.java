package bazy.danych.projekt.dao.impl;

import bazy.danych.projekt.bean.Match;
import bazy.danych.projekt.dao.MatchDao;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MatchDaoImpl extends ConnectDao implements MatchDao {

    @Override
    public int addLeagueMatch(Match match) {
        return connector.insert(prepareSQLToAddLeague(match));
    }

    @Override
    public int addTournamentMatch(Match match) {
        return connector.insert(prepareSQLToAddTournament(match));
    }

    @Override
    public Match getMatchById(String id) {
        Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Match toReturn = null;

        try {
            preparedStatement = connection.prepareStatement(prepareSQLForId(id), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            toReturn = mapResultSetToMatchList(resultSet).get(0);
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to get match by id ");
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
    public List<Match> getByLeagueId(String id) {
        Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Match> toReturn = null;

        try {
            preparedStatement = connection.prepareStatement(prepareSQLForLeagueId(id), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            toReturn = mapResultSetToMatchListFullNames(resultSet);
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to get match by league id ");
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
    public List<Match> getByTournamentId(String id) {
        Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Match> toReturn = null;

        try {
            preparedStatement = connection.prepareStatement(prepareSQLForTournamentId(id), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            toReturn = mapResultSetToMatchListFullNames(resultSet);
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to get match by tournament id ");
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
    public List<Match> getAllMatches() {
        Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Match> toReturn = null;

        try {
            preparedStatement = connection.prepareStatement(prepareSQLForAll(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            toReturn = mapResultSetToMatchList(resultSet);
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

    private List<Match> mapResultSetToMatchList(ResultSet resultSet) {
        List<Match> matchList = new ArrayList<>();

        while (true) {
            try {
                if (!resultSet.next()) break;

                Match match = new Match();
                match.setId(resultSet.getString("id_mecz"));
                match.setIdGame(resultSet.getString("id_gra"));
                match.setIdTeam1(resultSet.getString("id_druzyna1"));
                match.setIdTeam2(resultSet.getString("id_druzyna2"));
                match.setTeam1Points(resultSet.getString("punkty_druzyna1"));
                match.setTeam2Points(resultSet.getString("punkty_druzyna2"));
                match.setDate(resultSet.getString("data"));

                matchList.add(match);
            } catch (SQLException e) {
                System.out.println("Error has occurred while mapping result set to player list ");
                e.printStackTrace();
            }
        }

        return matchList;
    }

    private List<Match> mapResultSetToMatchListFullNames(ResultSet resultSet) {
        List<Match> matchList = new ArrayList<>();

        while (true) {
            try {
                if (!resultSet.next()) break;

                Match match = new Match();
                match.setId(resultSet.getString("id_mecz"));
                match.setIdGame(resultSet.getString("id_gra"));
                match.setGameName(resultSet.getString("nazwa_gry"));
                match.setNameTeam1(resultSet.getString("druzyna1"));
                match.setNameTeam2(resultSet.getString("druzyna2"));
                match.setTeam1Points(resultSet.getString("punkty_druzyna1"));
                match.setTeam2Points(resultSet.getString("punkty_druzyna2"));
                match.setDate(resultSet.getString("data"));
                match.setLeagueName(resultSet.getString("nazwa_ligi"));
                match.setTournamentName(resultSet.getString("nazwa_turnieju"));

                matchList.add(match);
            } catch (SQLException e) {
                System.out.println("Error has occurred while mapping result set to player list ");
                e.printStackTrace();
            }
        }

        return matchList;
    }

    private String prepareSQLForId(String id) {
        return "SELECT * FROM esport.Mecz WHERE id_mecz='" + id + "';";
    }

    private String prepareSQLForAll() {
        return "SELECT * FROM esport.Mecz;";
    }

    private String prepareSQLForLeagueId(String id) {
        return "SELECT * FROM esport.mecze_ligi(" + id + ");";
    }

    private String prepareSQLForTournamentId(String id) {
        return "SELECT * FROM esport.mecze_turnieju(" + id + ");";
    }

    private String prepareSQLToAddTournament(Match match) {
        return "INSERT INTO esport.Mecz (id_mecz, id_gra, id_druzyna1, id_druzyna2, punkty_druzyna1, punkty_druzyna2, data, id_turniej) VALUES (" +
                "(SELECT id_mecz FROM esport.Mecz ORDER BY id_mecz DESC LIMIT(1)) + 1, " +
                match.getIdGame() + ", " +
                match.getIdTeam1() + ", " +
                match.getIdTeam2() + ", " +
                match.getTeam1Points() + ", " +
                match.getTeam2Points() + ", '" +
                match.getDate() + "', " +
                match.getTournamentName() + ");";
    }

    private String prepareSQLToAddLeague(Match match) {
        int id = connector.getInt("SELECT (SELECT id_mecz FROM esport.Mecz ORDER BY id_mecz DESC LIMIT(1)) + 1;");

        System.out.println("id_mecz = " + id);

        return "INSERT INTO esport.Mecz (id_mecz, id_gra, id_druzyna1, id_druzyna2, punkty_druzyna1, punkty_druzyna2, data, id_liga) VALUES (" +
                id + ", " +
                match.getIdGame() + ", " +
                match.getIdTeam1() + ", " +
                match.getIdTeam2() + ", " +
                match.getTeam1Points() + ", " +
                match.getTeam2Points() + ", '" +
                match.getDate() + "', " +
                match.getLeagueName() + ");";
    }
}
