
package project.insa.idchatsystem;

import project.insa.idchatsystem.User.distanciel.User;

public class Data {
    private User source;

    private User destination;

    private String type;
    
    /* CONSTRUCTORS */
    
    public Data() {
        
    }
    
    public Data(User source, User destination) {
        this.source = source;
        this.destination = destination;
    }

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
}
