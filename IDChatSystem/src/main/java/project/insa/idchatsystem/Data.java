
package project.insa.idchatsystem;

import project.insa.idchatsystem.User.distanciel.User;

public abstract class Data {
    private User source;

    private User destination;

    private String type;
    
    /* CONSTRUCTORS */
    public Data(){}
    public Data(User source, User destination) {
        this.source = source;
        this.destination = destination;
        this.type = null;
    }
    
    public boolean isMessage() {
        return this.type.equals(Message.TYPE);
    }
    
    public boolean isInformation() {
        return this.type.equals(Information.TYPE);
    }
    
    public static boolean isInformation(String input) {
        return input.startsWith(Information.TYPE);
    }
    
    public abstract String toStream();

    /* GETTERS/SETTERS */
    public User getSource() {
        return this.source;
    }
    
    public User getDestination() {
        return this.destination;
    }
    
    public void setSource(User source) {
        this.source = source;
    }
    
    public void setDestination(User destination) {
        this.destination = destination;
    }
    
    public void setType(String type) {
        this.type = type;
    }
}
