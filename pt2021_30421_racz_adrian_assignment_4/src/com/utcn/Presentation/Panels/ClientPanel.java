package com.utcn.Presentation.Panels;

import com.utcn.Business.DeliveryService;
import com.utcn.Presentation.Controllers.Client.ControllerOrder;
import com.utcn.Presentation.Controllers.Client.ControllerSearch;
import com.utcn.Presentation.Controllers.Client.ControllerOrderTable;
import com.utcn.Util.IntegerMutable;
import com.utcn.Util.TableColumnAdapter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * The main panel of the client frame.
 * Contains 3 sub-panels
 */
public class ClientPanel extends JPanel {
    private final ControllerSearch controllerSearch;
    private final ControllerOrderTable controllerOrderTable;
    private final ControllerOrder controllerOrder;

    public ClientPanel(IntegerMutable id, DeliveryService deliveryService) {
        setLayout(new BorderLayout());

        controllerSearch = new ControllerSearch(deliveryService);
        controllerOrderTable = new ControllerOrderTable(deliveryService, this);
        controllerOrder = new ControllerOrder(deliveryService, this, id);

        add(new OrderPanel(), BorderLayout.WEST);
        add(new SearchPanel(), BorderLayout.SOUTH);
        add(new TablePanel(), BorderLayout.CENTER);
    }

    /**
     * Panel for ordering
     */
    private class OrderPanel extends JPanel {
        OrderPanel() {
            JButton btnAdd = new JButton("ADD");
            JButton btnRemove = new JButton("REMOVE");
            JButton btnFinish = new JButton("ORDER");
            JButton btnReset = new JButton("RESET");

            JTable table = new JTable();
            JScrollPane scrollPane = new JScrollPane(table);

            table.setModel(new DefaultTableModel());

            setLayout(new GridBagLayout());
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            add(btnAdd, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 0;
            add(btnRemove, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            add(btnFinish, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 1;
            add(btnReset, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.gridwidth = 2;
            gridBagConstraints.gridheight = 2;
            add(scrollPane, gridBagConstraints);

            controllerOrderTable.setOrderTable(table);
            controllerOrder.setOrderTable(table);
            btnAdd.addActionListener(controllerOrderTable);
            btnRemove.addActionListener(e ->{
                if (table.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this),
                            "No table row has been selected!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else ((DefaultTableModel)table.getModel()).removeRow(table.getSelectedRow());
                TableColumnAdapter.stretchColumns(table);
            });
            btnReset.addActionListener(e -> ((DefaultTableModel)table.getModel()).setRowCount(0));
            btnFinish.addActionListener(controllerOrder);
        }
    }

    /**
     * Panel for searching
     */
    private class SearchPanel extends JPanel {

        SearchPanel() {
            JLabel labelName = new JLabel("Name");
            JLabel labelRating = new JLabel("Rating");
            JLabel labelCalories = new JLabel("Calories");
            JLabel labelProtein = new JLabel("Protein");
            JLabel labelFat = new JLabel("Fat");
            JLabel labelSodium = new JLabel("Sodium");
            JLabel labelPrice = new JLabel("Price");

            JTextField textName = new JTextField(20);
            JTextField textRatingMin = new JTextField(10);
            JTextField textCaloriesMin = new JTextField(10);
            JTextField textProteinMin = new JTextField(10);
            JTextField textFatMin = new JTextField(10);
            JTextField textSodiumMin = new JTextField(10);
            JTextField textPriceMin = new JTextField(10);
            JTextField textRatingMax = new JTextField(10);
            JTextField textCaloriesMax = new JTextField(10);
            JTextField textProteinMax = new JTextField(10);
            JTextField textFatMax = new JTextField(10);
            JTextField textSodiumMax = new JTextField(10);
            JTextField textPriceMax = new JTextField(10);

            JButton btnSearch = new JButton("SEARCH");

            setLayout(new GridBagLayout());
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            add(labelName, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 0;
            add(labelRating, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 0;
            add(labelCalories, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 3;
            gridBagConstraints.gridy = 0;
            add(labelProtein, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 4;
            gridBagConstraints.gridy = 0;
            add(labelFat, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 5;
            gridBagConstraints.gridy = 0;
            add(labelSodium, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 6;
            gridBagConstraints.gridy = 0;
            add(labelPrice, gridBagConstraints);

            // 2nd row

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            add(textName, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 1;
            add(textRatingMin, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 1;
            add(textCaloriesMin, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 3;
            gridBagConstraints.gridy = 1;
            add(textProteinMin, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 4;
            gridBagConstraints.gridy = 1;
            add(textFatMin, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 5;
            gridBagConstraints.gridy = 1;
            add(textSodiumMin, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 6;
            gridBagConstraints.gridy = 1;
            add(textPriceMin, gridBagConstraints);

            // 3rd row

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 2;
            add(btnSearch, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 2;
            add(textRatingMax, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 2;
            add(textCaloriesMax, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 3;
            gridBagConstraints.gridy = 2;
            add(textProteinMax, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 4;
            gridBagConstraints.gridy = 2;
            add(textFatMax, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 5;
            gridBagConstraints.gridy = 2;
            add(textSodiumMax, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 6;
            gridBagConstraints.gridy = 2;
            add(textPriceMax, gridBagConstraints);

            controllerSearch.setSearchFields(textName, textRatingMin, textCaloriesMin, textProteinMin, textFatMin,
                    textSodiumMin, textPriceMin, textRatingMax, textCaloriesMax, textProteinMax, textFatMax,
                    textSodiumMax, textPriceMax);
            btnSearch.addActionListener(controllerSearch);
        }
    }

    /**
     * Panel with the main table
     */
    private class TablePanel extends JPanel {
        TablePanel() {
            JTable table = new JTable();
            table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
            JScrollPane scrollPane = new JScrollPane(table);

            table.setModel(new DefaultTableModel());

            setLayout(new BorderLayout());
            add(scrollPane, BorderLayout.CENTER);

            controllerSearch.setMainTable(table);
            controllerOrderTable.setMainTable(table);
        }
    }

}
