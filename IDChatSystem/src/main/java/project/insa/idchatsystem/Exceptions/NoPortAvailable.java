package project.insa.idchatsystem.Exceptions;

public class NoPortAvailable extends Exception{
    public NoPortAvailable(String src) {
        super("No port available was found for the object");
    }
}
