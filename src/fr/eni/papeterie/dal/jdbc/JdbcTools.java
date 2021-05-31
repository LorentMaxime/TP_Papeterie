package fr.eni.papeterie.dal.jdbc;

import fr.eni.papeterie.dal.Settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcTools {
    private final static String URL = Settings.getPropriete("url");
    private final static String LOGIN = Settings.getPropriete("login");
    private final static String MDP = Settings.getPropriete("mdp");

    public static Connection recupConnection() throws SQLException{
        Connection connection = DriverManager.getConnection(URL, LOGIN, MDP);
        return connection;
    }


}
