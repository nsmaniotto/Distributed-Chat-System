package project.insa.idchatsystem.gui;

import javax.swing.*;
import java.awt.*;

public class LoginsListRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if(value instanceof UserView) {
            UserView userview = (UserView) value;
            setText(userview.getText());
        }
        return this;
    }
}
