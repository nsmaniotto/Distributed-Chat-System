
package project.insa.idchatsystem;

import javax.swing.JList;

class ConversationModel implements ConversationObservable {
    private boolean isOpen;
    private AgentController agentController;
    private User correspondent;
    private JList<Message> history;
    private JList<ConversationModel> conversationChildren;
    private String networkType;

    public ConversationModel(int id1) {

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
