package project.insa.idchatsystem.database;

import project.insa.idchatsystem.Message;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginsBroadcastDatabase extends AbstractDatabase{
    private final String DB_ROW_ID = "ID";
    private final String DB_ROW_RECEIVER_PORT = "RECEIVER_PORT";
    public LoginsBroadcastDatabase(boolean clean) {
        super("logins_emitters_receivers");
        this.init();
        if(clean)
            this.executeUpdate("DELETE FROM "+ DB_TABLE_NAME);
    }
    @Override
    public void createTable() {
        System.out.println(".(MessageDatabase.java:123) - createTable : ");
        this.executeUpdate("CREATE TABLE IF NOT EXISTS " + DB_TABLE_NAME + " ("
                + DB_ROW_RECEIVER_PORT + " INT NOT NULL,"
                + "PRIMARY KEY ("+DB_ROW_RECEIVER_PORT+"))");
    }
    public ArrayList<Integer> getPortReceivers() {
        String query = "SELECT " + DB_ROW_RECEIVER_PORT
                + " FROM " + DB_TABLE_NAME;
        // Execute query
        ResultSet queryResultSet = this.executeQuery(query);
        // Convert result set to messages array
        ArrayList<Integer> ports = new ArrayList<>();
        if (queryResultSet != null) {
            try {
                while(queryResultSet.next()) {
                    int receiverPort = queryResultSet.getInt(DB_ROW_RECEIVER_PORT);
                    ports.add(receiverPort);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return ports;
    }
    public void writePortReceiver(int port) {
        // MySQL insert statement
        String prepareQuery = "INSERT INTO " + DB_TABLE_NAME + "(" + DB_ROW_RECEIVER_PORT
                + ")"
                + "VALUES (?)";
        // Create the MySQL insert prepared statement to prevent injection
        PreparedStatement preparedStatement;
        try {
            if (this.conn != null) {
                preparedStatement = this.conn.prepareStatement(prepareQuery);

                preparedStatement.setInt(1, port);

                preparedStatement.execute();
            }
        } catch (SQLException ex) {
            System.out.println("(MessageDatabase) : EXCEPTION AT CREATING/EXECUTING PREPARED STATEMENT : " + ex);
            Logger.getLogger(MessageDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
