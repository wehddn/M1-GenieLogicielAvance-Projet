package hubertmap.view;

import hubertmap.model.Time;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.*;

public class CustomPopupMenu extends JPopupMenu {

    public CustomPopupMenu(Map<String, ArrayList<Time>> schedules) {

        GridBagConstraints c = new GridBagConstraints();
        this.setLayout(new GridBagLayout());
        c.gridheight = 1;

        c.weightx = 1.0;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;

        c.gridwidth = 1;
        c.gridy = 0;
        c.gridx = 0;
        this.add(new JButton("Departure"));
        c.gridx = 1;
        this.add(new JButton("Arrival "));

        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.gridheight = GridBagConstraints.REMAINDER; // set to REMAINDER to span to bottom
        c.gridy = 1;
        c.gridx = 0;
        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (Entry<String, ArrayList<Time>> entry : schedules.entrySet()) {
            for (Time time : entry.getValue()) {
                listModel.addElement(time.toString());
            }
        }

        JList<String> jList = new JList<>(listModel);

        JScrollPane scrollPane = new JScrollPane(jList);
        this.add(scrollPane, c);
    }
}
