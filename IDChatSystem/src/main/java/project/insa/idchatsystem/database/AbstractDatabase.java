package project.insa.idchatsystem.database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractDatabase {
    // Database credentials
    private final String USER = "idchatsystem_usr";
    private final String PASSWORD = "idchatsystem_lgn";
    protected final String DB_TABLE_NAME;
    // Databases
    private final String DB_NAME = "idchatsystem";
    // JDBC driver name and database URL
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final int DB_PORT = 3306;
    private final String DB_SERVER_URL = "jdbc:mysql://localhost:" + DB_PORT;
    private final String DB_DATABASE_URL = DB_SERVER_URL + "/" + DB_NAME;

    // Database utilities
    protected Connection conn;
    protected Statement statement;

    public AbstractDatabase(String table_name) {
        this.DB_TABLE_NAME = table_name;
    }
    public void init() {
        try {
            // Register JDBC driver
            Class.forName(this.JDBC_DRIVER);

            // Connect to the local sql server
            this.connect(DB_SERVER_URL);
        } catch (ClassNotFoundException ex) {
            System.out.println("(MessageDatabase) : EXCEPTION AT REGISTERING JDBC DRIVER : " + ex);
            Logger.getLogger(MessageDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.initDatabase();
    }
    public void executeUpdate(String update) {

        this.createStatement();

        if(this.statement != null) {
            try {
                this.statement.executeUpdate(update);
            } catch (SQLException ex) {
                System.out.println("(MessageDatabase) : EXCEPTION AT EXECUTING UPDATE : " + ex);
                Logger.getLogger(MessageDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void createStatement() {
//        System.out.println("(MessageDatabase) - Creating a statement...");

        this.statement = null; // Reset the statement each time the method is called

        if(this.conn != null) {
            try {
                statement = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println("(MessageDatabase) : EXCEPTION AT CREATING STATEMENT : " + ex);
                Logger.getLogger(MessageDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if(this.statement != null) {
//            System.out.println("(MessageDatabase) - Successfully created the statement");
        } else {
//            System.out.println("(MessageDatabase) - Could not create a statement");
        }
    }
    public abstract void createTable();
    protected void initDatabase() {
        // Create Database if it does not exists
        this.executeUpdate("CREATE DATABASE IF NOT EXISTS " + this.DB_NAME);

        // Connect to the previously created database
        this.connect(DB_DATABASE_URL);

        // Create (if not exists) the table which will store the messages
        this.createTable();
    }

    private void connect(String url) {
//        System.out.println("(MessageDatabase) - Connecting to '" + url + "'");
        this.conn = null; // Reset the conn each time the method is called

        try {
            this.conn = DriverManager.getConnection(url, USER, PASSWORD);
        } catch (SQLException ex) {
            System.out.println("(MessageDatabase) : EXCEPTION AT CONNECT : " + ex);
            Logger.getLogger(MessageDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

        if(this.conn != null) {
//            System.out.println("(MessageDatabase) - Successfully connected to the database");
        } else {
//            System.out.println("(MessageDatabase) - Could not connect to the database");
        }
    }
    protected ResultSet executeQuery(String query) {
//        System.out.println("(MessageDatabase) - Query : " + query);

        ResultSet queryResultSet = null;

        this.createStatement();

        if(this.statement != null) {
            try {
                queryResultSet = this.statement.executeQuery(query);
            } catch (SQLException ex) {
                System.out.println("(MessageDatabase) : EXCEPTION AT EXECUTING QUERY : " + ex);
                Logger.getLogger(MessageDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return queryResultSet;
    }
}
