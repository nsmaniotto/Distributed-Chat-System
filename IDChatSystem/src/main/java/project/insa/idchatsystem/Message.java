
package project.insa.idchatsystem;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import project.insa.idchatsystem.User.distanciel.User;

public class Message extends Data {
    private String text;
    private ArrayList<File> files;
    private Timestamp timestamp;
    
    /* CONSTRUCTORS */
    
    public Message(User source, User destination, String text, Timestamp timestamp) {
        super(source, destination);
        
        this.text = text;
        this.timestamp = timestamp;
    }
    
    /**
     * Constructor called when translating network data to a message
     * 
     * @param stream : String - raw input retrieved on the network
     */
    public Message(String stream) {
        // Apply pattern matching to extract timstamp and text message
        Pattern regex = Pattern.compile("(?<timestamp>\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3});(?<text>.*)");
        Matcher matcher = regex.matcher(stream);
        
        if(matcher.find()) {
            this.timestamp = Timestamp.valueOf(matcher.group("timestamp"));
            this.text = matcher.group("text");
        } else {
            System.out.printf("TimeStamp cannot be determined\n");
            this.timestamp = this.generateTimeStamp();//Good solution ?? If the timestamp is not retrieved the user will see the generated timestamp without kowing where is the pb
            this.text = stream;
            //this.text = "ERROR: MESSAGE COULD NOT BE DETERMINED";
        }
    }
    
    /* UTILITIES */

    public Timestamp generateTimeStamp() {
        return ( new Timestamp(System.currentTimeMillis()) );
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
    
    /* GETTERS/SETTERS */
    
    public String getText() {
        return this.text;
    }
    
    public Timestamp getTimestamp() {
        return this.timestamp;
    }
    
    public String getTimestampString() {
        return this.timestamp.toString();
    }

    @Override
    public String toString() {
        return String.format("%s, %s",this.text,this.timestamp.toString());
    }
    
}