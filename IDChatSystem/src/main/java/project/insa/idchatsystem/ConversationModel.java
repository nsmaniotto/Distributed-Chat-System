
package project.insa.idchatsystem;

import java.awt.List;
import java.util.ArrayList;

class ConversationModel implements ConversationObservable {
    private boolean isOpen;
    private User correspondent;
    private ArrayList<Message> history;
    private ArrayList<ConversationModel> conversationChildren;
    private String networkType;

    /**
     * Constructor called to instantiate the conversationHandler
     *
     * @param id1 : int - id of the current user
     */
    public ConversationModel(int id1) {
        this.isOpen = false;
        this.correspondent = null; // Because this constructor is for the handler
        this.history = null; // Because this constructor is for the handler
        this.conversationChildren = new ArrayList<ConversationModel>();
        this.networkType = "none"; // Not applicable
        
        //TODO Start the handler thread
    }

    public ConversationModel(int id1, int id2, String mode) {
        
    }

    private void storeMessage(Message message) {

    }

    private Message generateMessage(Data data) {
        return null;
    }

    private void loadConversation() {

    }

    public void onReceive(Data data) {

    }

    private void send(Data data) {

    }

    @Override
    public void addObserver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteObserver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyObservers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
