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

/**
 * The SchedulesPanel class displays the schedules of a given station in a table format. It allows
 * the user to select the departure or arrival station by clicking on a button. The schedules are
 * sorted by time, with duplicates removed.
 */
public class SchedulesPanel extends JPanel {

    /**
     * Creates a new SchedulesPanel with the schedules of the given station and lines.
     *
     * @param v the station whose schedules will be displayed
     * @param lines the lines that serve the station
     */
    public SchedulesPanel(Station v, HashMap<String, Line> lines) {
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

    /**
     * Scrolls the given JScrollPane to the row with the given index in the given JTable.
     *
     * @param table the JTable to scroll to the row
     * @param scrollPane the JScrollPane to be scrolled
     * @param rowTime the index of the row to scroll to
     */
    private void scrollToRow(JTable table, JScrollPane scrollPane, int rowTime) {
        Rectangle cellBounds = table.getCellRect(rowTime, 0, true);
        scrollPane.getViewport().scrollRectToVisible(cellBounds);
    }

    /**
     * Sets up the given DefaultTableModel for the given JTable, sorting it by time and removing
     * duplicates.
     *
     * @param model the DefaultTableModel to set up
     * @param table the JTable to set up
     */
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

    /**
     * Returns the index of the first row in the given JTable that has a time after the current
     * time. If there is no such row, returns -1.
     *
     * @param table the JTable to search
     * @return the index of the first row with a time after the current time, or -1 if there is no
     *     such row
     */
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
