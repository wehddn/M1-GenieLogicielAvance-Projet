package hubertmap.view;

import hubertmap.controller.Controller;
import hubertmap.model.Time;
import hubertmap.model.transport.Line;
import hubertmap.model.transport.Station;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CustomPopupMenu extends JPopupMenu {

    public CustomPopupMenu(Station v, HashMap<String, Line> lines) {

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
        JButton departure = new JButton("Departure");
        this.add(departure);
        c.gridx = 1;
        JButton arrival = new JButton("Arrival");
        this.add(arrival);

        departure.addActionListener(
                e -> {
                    Controller.setDeparture(v.getName());
                });

        arrival.addActionListener(
                e -> {
                    Controller.setArrival(v.getName());
                });

        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.gridheight = GridBagConstraints.REMAINDER; // set to REMAINDER to span to bottom
        c.gridy = 1;
        c.gridx = 0;
        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (Entry<String, ArrayList<Time>> entry : v.getSchedules().entrySet()) {
            if (entry.getValue() != null)
                for (Time time : entry.getValue()) {
                    listModel.addElement(time.toString());
                }
        }

        // JList<String> jList = new JList<>(listModel);

        // JScrollPane scrollPane = new JScrollPane(jList);
        // this.add(scrollPane, c);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Direction");
        model.addColumn("Time");
        JTable table = new JTable(model);

        for (Entry<String, ArrayList<Time>> entry : v.getSchedules().entrySet()) {
            if (entry.getValue() != null)
                for (Time time : entry.getValue()) {
                    listModel.addElement(time.toString());
                    Line line = lines.get(entry.getKey());
                    Station terminalStation = line.getTerminalStationArrival();
                    model.addRow(new String[] {terminalStation.getName(), time.toString()});
                }
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(200, 200));
        this.add(scrollPane, c);
    }
}
