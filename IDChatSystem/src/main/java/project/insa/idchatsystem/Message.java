
package project.insa.idchatsystem;

import java.io.File;
import javax.swing.JList;
import java.sql.Timestamp;

class Message extends Data {
    private String text;
    private JList<File> files;
    private Timestamp timestamp;
    private ConversationModel conversationModel;

    public Timestamp generateTimeStamp() {
        return null;
    }
}
