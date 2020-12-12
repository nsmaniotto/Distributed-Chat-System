
package project.insa.idchatsystem;

import project.insa.idchatsystem.User.distanciel.User;

public class Data {
    private User source;

    private User destination;

    private String type;

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
