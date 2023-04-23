package hubertmap.view;

import hubertmap.controller.Controller;
import hubertmap.model.Time;
import hubertmap.model.transport.Line;
import hubertmap.model.transport.Station;
import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

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

        DefaultTableModel model =
                new DefaultTableModel() {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        // All cells are non-editable
                        return false;
                    }
                };
        model.addColumn("Direction");
        model.addColumn("Time");
        JTable table = new JTable(model);

        for (Entry<String, ArrayList<Time>> entry : v.getSchedules().entrySet()) {
            if (entry.getValue() != null)
                for (Time time : entry.getValue()) {
                    Line line = lines.get(entry.getKey());
                    Station terminalStation = line.getTerminalStationArrival();
                    model.addRow(new String[] {terminalStation.getName(), time.toString()});
                }
        }

        setUpModel(model, table);

        JScrollPane scrollPane = new JScrollPane(table);

        int rowTime = getRowTime(table);

        scrollToRow(table, scrollPane, rowTime);

        scrollPane.setPreferredSize(new Dimension(200, 200));
        this.add(scrollPane, c);
    }

    private void scrollToRow(JTable table, JScrollPane scrollPane, int rowTime) {
        Rectangle cellBounds = table.getCellRect(rowTime, 0, true);
        scrollPane.getViewport().scrollRectToVisible(cellBounds);
    }

    private void setUpModel(DefaultTableModel model, JTable table) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);

        table.setRowSorter(sorter);
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();

        Set<Object> uniqueValues = new HashSet<>();

        // Iterate over the rows of the model
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            Object value = model.getValueAt(i, 1); // Get the value from the second column
            if (uniqueValues.contains(value)) { // If the value is already in the Set
                model.removeRow(i); // Remove the row from the model
            } else {
                uniqueValues.add(value); // Add the value to the Set
            }
        }
    }

    private int getRowTime(JTable table) {
        LocalTime currentTime = LocalTime.now();

        for (int i = 0; i < table.getRowCount(); i++) {
            String timeString = (String) table.getValueAt(i, 1);
            LocalTime rowTime = LocalTime.parse(timeString);

            if (rowTime.isAfter(currentTime)) {
                return i;
            }
        }

        return -1;
    }
}
