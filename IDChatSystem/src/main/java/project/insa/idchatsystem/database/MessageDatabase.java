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
public class MessageDatabase extends AbstractDatabase {
    // Singleton instance of the databases
    private static MessageDatabase INSTANCE;

    // Rows
    private final String DB_MESSAGE_ROW_ID = "ID";
    private final String DB_MESSAGE_ROW_SOURCE_ID = "SOURCE_ID";
    private final String DB_MESSAGE_ROW_DESTINATION_ID = "DESTINATION_ID";
    private final String DB_MESSAGE_ROW_TEXT = "TEXT";
    private final String DB_MESSAGE_ROW_TIMESTAMP = "TIMESTAMP";

    
    public MessageDatabase() {
        super("messages");
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
    @Override
    public void createTable() {
        System.out.println(".(MessageDatabase.java:123) - createTable : ");

        this.executeUpdate("CREATE TABLE IF NOT EXISTS " + DB_TABLE_NAME + "("
                + DB_MESSAGE_ROW_ID + " INT NOT NULL AUTO_INCREMENT,"
                + DB_MESSAGE_ROW_SOURCE_ID + " TEXT NOT NULL,"
                + DB_MESSAGE_ROW_DESTINATION_ID + " TEXT NOT NULL,"
                + DB_MESSAGE_ROW_TEXT + " TEXT NOT NULL," // TINYTEXT = 255 char, TEXT = 65535 char
                + DB_MESSAGE_ROW_TIMESTAMP + " TIMESTAMP NOT NULL,"
                + "PRIMARY KEY (ID))");
    }
    
    /* QUERIES */
    
    public void storeMessage(Message message) {
            // MySQL insert statement
            String prepareQuery = "INSERT INTO " + DB_TABLE_NAME + "("
                    + DB_MESSAGE_ROW_SOURCE_ID
                    + "," + DB_MESSAGE_ROW_DESTINATION_ID
                    + "," + DB_MESSAGE_ROW_TEXT
                    + "," + DB_MESSAGE_ROW_TIMESTAMP
                    + ")"
                    + "VALUES (?, ?, ?, ?)";

            // Create the MySQL insert prepared statement to prevent injection
            PreparedStatement preparedStatement;
            try {
                if (this.conn != null) {
                    preparedStatement = this.conn.prepareStatement(prepareQuery);

                    preparedStatement.setString(1, message.getSource().get_id());
                    preparedStatement.setString(2, message.getDestination().get_id());
                    preparedStatement.setString(3, message.getText());
                    preparedStatement.setTimestamp(4, message.getTimestamp());
                    preparedStatement.execute();
                }
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
                + " FROM " + DB_TABLE_NAME
                + " WHERE (" + DB_MESSAGE_ROW_SOURCE_ID + "='" + user1.get_id()
                    + "' AND " + DB_MESSAGE_ROW_DESTINATION_ID + "='" + user2.get_id()
                + "') OR (" + DB_MESSAGE_ROW_SOURCE_ID + "='" + user2.get_id()
                    + "' AND " + DB_MESSAGE_ROW_DESTINATION_ID + "='" + user1.get_id() + "')";
        // Execute query
        ResultSet queryResultSet = this.executeQuery(query);
        
        // Convert result set to messages array
        if(queryResultSet != null) {
            try {
                while(queryResultSet.next()){
                    // Retrieve values by column name
                    String sourceID  = queryResultSet.getString(DB_MESSAGE_ROW_SOURCE_ID);
                    User source = user1.get_id().equals(sourceID) ? user1 : user2;
                    String destinationID = queryResultSet.getString(DB_MESSAGE_ROW_DESTINATION_ID);
                    User destination = user1.get_id().equals(destinationID) ? user1 : user2;
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
        }
        
        return resultMessages;
    }

}