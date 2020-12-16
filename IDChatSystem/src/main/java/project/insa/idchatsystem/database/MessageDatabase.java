package project.insa.idchatsystem.database;

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
    private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    private final String DB_URL = "jdbc:mysql://localhost/EMP";

    // Database credentials
    private final String USER = "username";
    private final String PASS = "password";

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
            
        } catch (ClassNotFoundException ex) {
            System.out.println("(MessageDatabase) : EXCEPTION " + ex);
            Logger.getLogger(MessageDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}