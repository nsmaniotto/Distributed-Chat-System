
package project.insa.idchatsystem;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;

class Message extends Data {
    private String text;
    private ArrayList<File> files;
    private Timestamp timestamp;

    public Timestamp generateTimeStamp() {
        return null;
    }
    
    /**
     * toStream allows a message to be translated to a certain format
     * 
     * @return String - timestamp;message
     */
    public String toStream() {
        String stream = this.timestamp.toString() + ";" + this.text; // For now, files are not considered
                
        return stream;
    }
}
