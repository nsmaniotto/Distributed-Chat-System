package project.insa.idchatsystem.database;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.User.distanciel.User;

/**
 *
 * @author nsmaniotto
 */
public class MessageDatabase {
    // Singleton instance of the databases
    private static MessageDatabase INSTANCE;
    
    // Databases
    private final String DB_NAME = "idchatsystem";
    // Tables
    private final String DB_MESSAGE_TABLE_NAME = "messages";
    // Rows
    private final String DB_MESSAGE_ROW_ID = "ID";
    private final String DB_MESSAGE_ROW_SOURCE_ID = "SOURCE_ID";
    private final String DB_MESSAGE_ROW_DESTINATION_ID = "DESTINATION_ID";
    private final String DB_MESSAGE_ROW_TEXT = "TEXT";
    private final String DB_MESSAGE_ROW_TIMESTAMP = "TIMESTAMP";
    
    // JDBC driver name and database URL
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    private final int DB_PORT = 3306;
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
        System.out.println("(MessageDatabase) - Creating the " + DB_MESSAGE_TABLE_NAME + " table...");
        
        this.executeUpdate("CREATE TABLE IF NOT EXISTS " + DB_MESSAGE_TABLE_NAME + " ("
                    + DB_MESSAGE_ROW_ID + " INT NOT NULL AUTO_INCREMENT,"
                    + DB_MESSAGE_ROW_SOURCE_ID + " INT NOT NULL,"
                    + DB_MESSAGE_ROW_DESTINATION_ID + " INT NOT NULL,"
                    + DB_MESSAGE_ROW_TEXT + " TEXT NOT NULL," // TINYTEXT = 255 char, TEXT = 65535 char
                    + DB_MESSAGE_ROW_TIMESTAMP + " TIMESTAMP NOT NULL,"
                    + "PRIMARY KEY (ID))");
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
    
    /* QUERIES */
    
    public void storeMessage(Message message) {
        // MySQL insert statement
        String prepareQuery = "INSERT INTO " + DB_MESSAGE_TABLE_NAME + "("
                + DB_MESSAGE_ROW_SOURCE_ID
                + "," + DB_MESSAGE_ROW_DESTINATION_ID
                + "," + DB_MESSAGE_ROW_TEXT
                + "," + DB_MESSAGE_ROW_TIMESTAMP
                + ")"
                + "VALUES (?, ?, ?, ?)";

        // Create the MySQL insert prepared statement to prevent injection
        PreparedStatement preparedStatement;
        try {
            preparedStatement = this.conn.prepareStatement(prepareQuery);
            
            preparedStatement.setInt(1, message.getSource().get_id());
            preparedStatement.setInt(2, message.getDestination().get_id());
            preparedStatement.setString(3, message.getText());
            preparedStatement.setTimestamp(4, message.getTimestamp());
            
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("(MessageDatabase) : EXCEPTION AT CREATING/EXECUTING PREPARED STATEMENT : " + ex);
            Logger.getLogger(MessageDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Retrieve messages between a given pair of users
     * Messages are returned in a list, ordered by default : oldest at list[0]
     * 
     * @param user1 - User : can be the source OR destination
     * @param user2 - User : can be the source OR destination
     * @return a list a messages, ordered by oldest first
     */
    public ArrayList<Message> retrieveOrderedMessagesByConversationBetween(User user1, User user2) {
        ArrayList<Message> resultMessages = new ArrayList<Message>();
        
        // Query to retrieve messages between user1.id and user2.id (both can be source OR destination)
        String query = "SELECT " + DB_MESSAGE_ROW_SOURCE_ID + "," + DB_MESSAGE_ROW_DESTINATION_ID
                            + "," + DB_MESSAGE_ROW_TEXT+ "," + DB_MESSAGE_ROW_TIMESTAMP 
                + " FROM " + DB_MESSAGE_TABLE_NAME
                + " WHERE " + DB_MESSAGE_ROW_SOURCE_ID + "=" + user1.get_id()
                    + " AND " + DB_MESSAGE_ROW_DESTINATION_ID + "=" + user2.get_id()
                + " OR " + DB_MESSAGE_ROW_SOURCE_ID + "=" + user2.get_id()
                    + " AND " + DB_MESSAGE_ROW_DESTINATION_ID + "=" + user1.get_id();
        
        // Execute query
        ResultSet queryResultSet = this.executeQuery(query);
        
        // Convert result set to messages array
        
        try {
            while(queryResultSet.next()){
                // Retrieve values by column name
                int sourceID  = queryResultSet.getInt(DB_MESSAGE_ROW_SOURCE_ID);
                User source = user1.get_id() == sourceID ? user1 : user2;
                int destinationID = queryResultSet.getInt(DB_MESSAGE_ROW_DESTINATION_ID);
                User destination = user1.get_id() == destinationID ? user1 : user2;
                String text = queryResultSet.getString(DB_MESSAGE_ROW_TEXT);
                Timestamp timestamp = queryResultSet.getTimestamp(DB_MESSAGE_ROW_TIMESTAMP);
                
                // Generate a message from retrieved values
                Message generatedMessage = new Message(source, destination, text, timestamp);
                
                // Add previously generated message to the returned array
                resultMessages.add(generatedMessage);
            }
        } catch (SQLException ex) {
            System.out.println("(MessageDatabase) : EXCEPTION AT CONVERTING RESULTSET TO MESSAGES : " + ex);
            Logger.getLogger(MessageDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return resultMessages;
    }
    
    /* UTILITIES */
    
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
    
    private ResultSet executeQuery(String query) {
        System.out.println("(MessageDatabase) - Query : " + query);
        
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