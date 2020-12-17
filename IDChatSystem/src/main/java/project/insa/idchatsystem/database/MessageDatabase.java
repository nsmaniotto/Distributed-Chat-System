package project.insa.idchatsystem.database;

import java.sql.Connection;
import java.sql.DriverManager;
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
    private final String DB_URL = "jdbc:mysql://localhost:" + DB_PORT;

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
            
            // Connect to the database
            this.connect();
        } catch (ClassNotFoundException ex) {
            System.out.println("(MessageDatabase) : EXCEPTION AT REGISTERING JDBC DRIVER : " + ex);
            Logger.getLogger(MessageDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println("(MessageDatabase) : EXCEPTION AT CONNECT : " + ex);
            Logger.getLogger(MessageDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.createStatement();
    }
    
    private void connect() throws SQLException {
        System.out.println("(MessageDatabase) - Connecting to the database...");
        this.conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        
        if(this.conn != null) {
            System.out.println("(MessageDatabase) - Successfully connected to the database");
        }
    }
    
    private void createStatement() {
        this.statement = null; // Reset the statement each time the method is called
        
        if(this.conn != null) {
            System.out.println("Creating statement...");
            
            try {
                statement = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println("(MessageDatabase) : EXCEPTION AT CREATING STATEMENT : " + ex);
                Logger.getLogger(MessageDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(this.statement != null) {
                System.out.println("(MessageDatabase) - Successfully created the statement");
            }
        }
    }
}