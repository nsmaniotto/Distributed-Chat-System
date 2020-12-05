
package project.insa.idchatsystem;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message extends Data {
    private String text;
    private ArrayList<File> files;
    private Timestamp timestamp;
    
    /**
     * Constructor called when translating network data to a message
     * 
     * @param stream : String - raw input retrieved on the network
     */
    public Message(String stream) {
        // Apply pattern matching to extract timstamp and text message
        Pattern regex = Pattern.compile("([\\d]+)[;](.+)");
        Matcher matcher = regex.matcher(stream);
        
        if(matcher.find()) {
            this.timestamp = new Timestamp(Long.parseLong(matcher.group(1)));
            this.text = matcher.group(2);
        } else {
            this.timestamp = this.generateTimeStamp();
            this.text = stream;
            //this.text = "ERROR: MESSAGE COULD NOT BE DETERMINED";
        }
    }

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
    
    public String getTimestamp() {
        return this.timestamp.toString();
    }

    @Override
    public String toString() {
        return String.format("%s, %s",this.text,this.timestamp.toString());
    }
    
}
