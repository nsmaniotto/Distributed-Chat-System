/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conversation;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import project.insa.idchatsystem.Conversation.ConversationHandler;
import project.insa.idchatsystem.Message;

/**
 *
 * @author smani
 */
public class TestMain {
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(() -> {
            testCase(2);
        });
    }
    
    private static void testCase(int testNumber) {
        switch (testNumber) {
            case 1 -> {
                ClientTestNathan clientTestNathan = new ClientTestNathan();
            }
            case 2 -> {
                ClientTestRobin clientTestRobin = new ClientTestRobin();
            }
            default -> {
            }
        }
    }
}
