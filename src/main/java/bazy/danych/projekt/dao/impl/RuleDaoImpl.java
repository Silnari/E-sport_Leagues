package bazy.danych.projekt.dao.impl;

import bazy.danych.projekt.bean.Rule;
import bazy.danych.projekt.dao.RulesDao;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RuleDaoImpl extends ConnectDao implements RulesDao {

    @Override
    public int addRule(Rule rule) {
        return connector.insert(prepareSQLQueryToAdd(rule));
    }

    @Override
    public Rule getRuleById(String id) {
        Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Rule toReturn = null;

        try {
            preparedStatement = connection.prepareStatement(prepareSQLQueryForId(id), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            toReturn = mapResultSetToRuleList(resultSet).get(0);
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to get rule by id ");
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
    public List<Rule> getAllRules() {
        Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Rule> toReturn = null;

        try {
            preparedStatement = connection.prepareStatement(prepareSQLQueryForAll(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            toReturn = mapResultSetToRuleList(resultSet);
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to get rule list ");
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

    private List<Rule> mapResultSetToRuleList(ResultSet resultSet) {
        List<Rule> ruleList = new ArrayList<>();

        while (true) {
            try {
                if (!resultSet.next()) break;

                Rule rule = new Rule();
                rule.setId(resultSet.getString("id_zasady"));
                rule.setDescription(resultSet.getString("opis"));

                ruleList.add(rule);
            } catch (SQLException e) {
                System.out.println("Error has occurred while mapping result set to rule list ");
                e.printStackTrace();
            }
        }

        return ruleList;
    }

    private String prepareSQLQueryForId(String id) {
        return "SELECT * FROM esport.Zasady WHERE id_zasady='" + id + "';";
    }

    private String prepareSQLQueryForAll() {
        return "SELECT * FROM esport.Zasady;";
    }

    private String prepareSQLQueryToAdd(Rule rule) {
        return "INSERT INTO esport.Zasady VALUES (" +
                "(SELECT id_zasady FROM esport.Zasady ORDER BY id_zasady DESC LIMIT(1)) + 1,'" +
                rule.getDescription() + "');";
    }
}
