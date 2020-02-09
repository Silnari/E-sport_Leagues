package bazy.danych.projekt.db;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class Connector {

    static int lport;
    static String rhost;
    static int rport;

    public static void initConnection() {

        String user = "7perlowski";
        String password = "haslo";
        String host = "pascal.fis.agh.edu.pl";

        int port = 22;

        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            lport = 4321;
            rhost = "localhost";
            rport = 5432;
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            int assinged_port = session.setPortForwardingL(lport, rhost, rport);
            System.out.println("localhost:" + assinged_port + " -> " + rhost + ":" + rport);
        } catch (Exception e) {
            System.err.print("Error has occurred while trying to establishing connection:" + e);
        }
    }

    public Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://" + rhost + ":" + lport + "/u7perlowski", "u7perlowski", "7perlowski");
        } catch (SQLException e) {
            System.out.println("Could not connect to data base: ");
            e.printStackTrace();
        }

        return connection;
    }

    public int getInt(String query) {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int toReturn = 0;

        try {
            preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery();

            resultSet.next();

            toReturn = resultSet.getInt(1);
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to get int");
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

    public int insert(String query) {

        Connection connection = getConnection();
        Statement statement;

        if (connection != null) {
            try {
                System.out.println("Inserting records into the table...");
                System.out.println("Query: " + query);
                statement = connection.createStatement();

                statement.executeUpdate(query);
                System.out.println("Inserted records into the table");
                connection.close();
                return 0;
            } catch (SQLException e) {
                System.out.println("Exception has occurred while processing data");
                e.printStackTrace();
                return -1;
            }
        } else {
            System.out.println("No connection with data base!");
            return -1;
        }
    }

}
