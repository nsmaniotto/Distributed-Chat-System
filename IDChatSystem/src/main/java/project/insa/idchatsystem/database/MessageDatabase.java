package project.insa.idchatsystem.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nsmaniotto
 */
public class MessageDatabase {
    // Singleton instance of the databases
    public static MessageDatabase INSTANCE;
    
    // JDBC driver name and database URL
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    private final int DB_PORT = 3306;
    private final String DB_NAME = "idchatsystem";
    private final String DB_SERVER_URL = "jdbc:mysql://localhost:" + DB_PORT;
    private final String DB_DATABASE_URL = DB_SERVER_URL + "/" + DB_NAME;

    // Database credentials
    private final String USER = "idchatsystem_usr";
    private final String PASSWORD = "idchatsystem_lgn";
    
    // Database utilities
    private Connection conn;
    private Statement statement;
    
    public static void MessageDatabase() {
        
    }
    
    /**
     * Access point of the (unique) instance
     * 
     * @return INSTANCE : MessageDatabase - single instance of this class
     */
    public static MessageDatabase getInstance() {
        if(MessageDatabase.INSTANCE == null){
            MessageDatabase.INSTANCE = new MessageDatabase();
        }
        
        return MessageDatabase.INSTANCE;
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
    
    private void connect(String url) {
        System.out.println("(MessageDatabase) - Connecting to '" + url + "'");
        this.conn = null; // Reset the conn each time the method is called 
        
        try {
            this.conn = DriverManager.getConnection(url, USER, PASSWORD);
        } catch (SQLException ex) {
            System.out.println("(MessageDatabase) : EXCEPTION AT CONNECT : " + ex);
            Logger.getLogger(MessageDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(this.conn != null) {
            System.out.println("(MessageDatabase) - Successfully connected to the database");
        } else {
            System.out.println("(MessageDatabase) - Could not connect to the database");
        }
    }
    
    private void initDatabase() {
        // Create Database if it does not exists
        this.executeUpdate("CREATE DATABASE IF NOT EXISTS " + this.DB_NAME);
        
        // Connect to the previously created database
        this.connect(DB_DATABASE_URL);
        
        // Create (if not exists) the table which will store the messages
        this.createMessageTable();
    }
    
    private void createMessageTable() {
        System.out.println("(MessageDatabase) - Creating the messages table...");
        
        this.executeUpdate("CREATE TABLE IF NOT EXISTS messages ("
                    + "ID INT NOT NULL,"
                    + "SOURCE_ID INT NOT NULL,"
                    + "DESTINATION_ID INT NOT NULL,"
                    + "TEXT TEXT NOT NULL," // TINYTEXT = 255 char, TEXT = 65535 char
                    + "TIMESTAMP TIMESTAMP NOT NULL,"
                    + "PRIMARY KEY (ID))");
    }
    
    private void executeUpdate(String update) {
        System.out.println("(MessageDatabase) - Update : " + update);
        
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
        System.out.println("(MessageDatabase) - Creating a statement...");
        
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
            System.out.println("(MessageDatabase) - Successfully created the statement");
        } else {
            System.out.println("(MessageDatabase) - Could not create a statement");
        }
    }
}