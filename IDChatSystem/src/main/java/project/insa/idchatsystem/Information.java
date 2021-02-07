package project.insa.idchatsystem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import project.insa.idchatsystem.User.distanciel.User;

public class Information extends Data {
    public static final String TYPE = "information";
    
    /* CONSTRUCTORS */
    
    public Information(User source, User destination) {
        super(source, destination);
        
        this.setType(TYPE);
    }
    
    public Information(String stream) {
        this.setType(Information.TYPE); 
        
        // Apply pattern matching to extract id and username
        Pattern regex = Pattern.compile("information;(?<id>([a-zA-Z0-9-])*);(?<username>(.)*)");
        Matcher matcher = regex.matcher(stream);
        
        if(matcher.find()) {
            String id = matcher.group("id");
            String username = matcher.group("username");
                            
            this.setSource(new User(username, id, null));
        } else {
            System.out.printf("(Information) : Information cannot be extracted\n");
        }
    }

    @Override
    public String toStream() {
        String stream = this.TYPE + ";" + this.getSource().get_id() + ";" + this.getSource().get_username();
        
        return stream;
    }

    @Override
    public String toString() {
        return this.toStream();
    }
}