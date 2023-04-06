package hubertmap.view;

import edu.uci.ics.jung.graph.Graph;
import hubertmap.model.transport.EdgeTransport;
import hubertmap.model.transport.Station;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * The Panel class extends JPanel and represents a panel for displaying search and graph elements.
 */
public class Panel extends JPanel {

    /** A List object to hold data. */
    private List<String> data;

    /** A JTextField object for search functionality. */
    private JTextField searchField;

    /** A JList object for store displaying data. */
    private JList<String> dataList;

    /** A JScrollPane object for wrapping the JList. */
    private JScrollPane scrollPaneResult;

    /** A JTable object for displaying data stored in dataList. */
    private JTable table;

    /** A GraphPanelJung object for drawing the graph using the JUNG library. */
    GraphPanelJung graphPanel;

    /**
     * Constructs a new Panel object with the specified edges.
     *
     * @param graph the list of edges to be displayed in the panel
     */
    public Panel(Graph<Station, EdgeTransport> graph) {
        JPanel searchPanel = new JPanel();
        graphPanel = new GraphPanelJung(graph);

        setSearchPanel(searchPanel);

        this.add(searchPanel);
        this.add(graphPanel);
    }

    /**
     * It sets up the panel to search in the list and display the corresponding table.
     *
     * @param searchPanel JPanel for adding elements
     */
    private void setSearchPanel(JPanel searchPanel) {

        searchPanel.setPreferredSize(new Dimension(200, 300));

        setData();

        searchField = new JTextField(10);

        // Sets a SimpleDocumentListener for dynamically filter data based on input
        searchField
                .getDocument()
                .addDocumentListener(
                        (SimpleDocumentListener)
                                e -> {
                                    filterListData();
                                });

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(
                e -> {
                    setTable();
                });

        dataList = new JList<>();
        dataList.setVisibleRowCount(5);

        setListData(data);

        JScrollPane scrollPaneData = new JScrollPane(dataList);

        scrollPaneResult = new JScrollPane();
        table = new JTable();

        scrollPaneResult = new JScrollPane(table);
        scrollPaneResult.setVisible(false);

        JPanel panelHorizontal = new JPanel();
        panelHorizontal.add(searchField);
        panelHorizontal.add(searchButton);

        JPanel panelVertical = new JPanel();
        panelVertical.add(panelHorizontal);
        panelVertical.add(scrollPaneData);
        panelVertical.setLayout(new BoxLayout(panelVertical, BoxLayout.Y_AXIS));

        searchPanel.add(panelVertical);
        searchPanel.add(scrollPaneResult);
    }

    /** Sets up table with default values and settings */
    private void setTable() {
        String dataListValue = dataList.getSelectedValue();
        if (dataListValue != null) {

            String[] columnNames = {"column1", "column2"};
            Object[][] tableData = {
                {dataListValue, 2},
                {"data2", 3}
            };

            DefaultTableModel dataModel = new DefaultTableModel(tableData, columnNames);

            table.setModel(dataModel);
            table.setPreferredScrollableViewportSize(
                    new Dimension(
                            table.getPreferredSize().width,
                            table.getRowHeight() * tableData.length));
            scrollPaneResult.setVisible(true);
            this.revalidate();
        }
    }

    /** Filters the data in the list by a line from the search bar */
    private void filterListData() {
        String searchFieldText = searchField.getText().toLowerCase();
        List<String> filteredData = new ArrayList<>();
        for (String string : data) {
            if (string.toLowerCase().contains(searchFieldText)) filteredData.add(string);
        }
        setListData(filteredData);
    }

    private void setData() {
        data = new ArrayList<>();
        data.add("apple");
        data.add("apk");
        data.add("banana");
        data.add("Apple");
        data.add("Banana");
        data.add("Cherry");
        data.add("Grape");
        data.add("Kiwi");
        data.add("Orange");
        data.add("Peach");
        data.add("Pear");
        data.add("Pineapple");
        data.add("Strawberry");
    }

    /**
     * It takes a list of strings and converts it into an array of strings
     *
     * @param data The data to be displayed in the list.
     */
    private void setListData(List<String> data) {
        String[] listData = new String[data.size()];
        for (int i = 0; i < data.size(); i++) listData[i] = data.get(i);
        dataList.setListData(listData);
    }
}
