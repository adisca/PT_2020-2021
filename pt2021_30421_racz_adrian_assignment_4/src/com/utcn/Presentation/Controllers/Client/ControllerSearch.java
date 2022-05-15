package com.utcn.Presentation.Controllers.Client;

import com.utcn.Business.DataModels.CompositeProduct;
import com.utcn.Business.DataModels.MenuItem;
import com.utcn.Business.DeliveryService;
import com.utcn.Util.TableColumnAdapter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controller for managing the search function
 */
public class ControllerSearch implements ActionListener {
    private final DeliveryService deliveryService;
    private JTable mainTable;
    private JTextField textName;
    private JTextField textRatingMin;
    private JTextField textCaloriesMin;
    private JTextField textProteinMin;
    private JTextField textFatMin;
    private JTextField textSodiumMin;
    private JTextField textPriceMin;
    private JTextField textRatingMax;
    private JTextField textCaloriesMax;
    private JTextField textProteinMax;
    private JTextField textFatMax;
    private JTextField textSodiumMax;
    private JTextField textPriceMax;

    public ControllerSearch(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    public void setMainTable(JTable mainTable) {
        this.mainTable = mainTable;
        initializeTable();
    }

    public void setSearchFields (JTextField textName, JTextField textRatingMin, JTextField textCaloriesMin,
                                 JTextField textProteinMin, JTextField textFatMin, JTextField textSodiumMin,
                                 JTextField textPriceMin, JTextField textRatingMax, JTextField textCaloriesMax,
                                 JTextField textProteinMax, JTextField textFatMax, JTextField textSodiumMax,
                                 JTextField textPriceMax) {
        this.textName = textName;
        this.textRatingMin = textRatingMin;
        this.textCaloriesMin = textCaloriesMin;
        this.textProteinMin = textProteinMin;
        this.textFatMin = textFatMin;
        this.textSodiumMin = textSodiumMin;
        this.textPriceMin = textPriceMin;
        this.textRatingMax = textRatingMax;
        this.textCaloriesMax = textCaloriesMax;
        this.textProteinMax = textProteinMax;
        this.textFatMax = textFatMax;
        this.textSodiumMax = textSodiumMax;
        this.textPriceMax = textPriceMax;
    }

    /**
     * Initializes the table
     */
    private void initializeTable() {
        String[] columns = {"Title", "Rating", "Calories", "Protein", "Fat", "Sodium", "Price", "Components"};
        List<MenuItem> menu = (List<MenuItem>) deliveryService.getMenu();
        DefaultTableModel tableModel = (DefaultTableModel) mainTable.getModel();

        tableModel.setColumnIdentifiers(columns);
        tableModel.setRowCount(0);

        for (MenuItem menuItem : menu) {
            tableModel.addRow(new String[]{menuItem.getTitle(), menuItem.getRating().toString(),
                    menuItem.getCalories().toString(), menuItem.getProtein().toString(), menuItem.getFat().toString(),
                    menuItem.getSodium().toString(), menuItem.getPrice().toString(),
                    CompositeProduct.decomposeProduct(menuItem)});
        }
        TableColumnAdapter.stretchColumns(mainTable);
        tableModel.fireTableDataChanged();
    }

    /**
     * Updates the table with the given data
     *
     * @param data  Data to appear in the table
     */
    public void updateTable(List<MenuItem> data) {
        DefaultTableModel tableModel = (DefaultTableModel) mainTable.getModel();
        tableModel.setRowCount(0);

        for (MenuItem menuItem : data) {
            tableModel.addRow(new String[]{menuItem.getTitle(), menuItem.getRating().toString(),
                    menuItem.getCalories().toString(), menuItem.getProtein().toString(), menuItem.getFat().toString(),
                    menuItem.getSodium().toString(), menuItem.getPrice().toString(),
                    CompositeProduct.decomposeProduct(menuItem)});
        }
        TableColumnAdapter.stretchColumns(mainTable);
        tableModel.fireTableDataChanged();
    }

    /**
     * String to double with a twist
     *
     * @param s A string representing a double
     * @return  The double or the min double value
     */
    private Double toDoubleMin(String s) {
        if (s.isBlank())
            return -Double.MAX_VALUE;
        return Double.valueOf(s);
    }

    /**
     * String to double with a twist
     *
     * @param s A string representing a double
     * @return  The double or the max double value
     */
    private Double toDoubleMax(String s) {
        if (s.isBlank())
            return Double.MAX_VALUE;
        return Double.valueOf(s);
    }

    /**
     * String to int with a twist
     *
     * @param s A string representing a int
     * @return  The integer or the min int value
     */
    private Integer toIntegerMin(String s) {
        if (s.isBlank())
            return Integer.MIN_VALUE;
        return Integer.valueOf(s);
    }

    /**
     * String to int with a twist
     *
     * @param s A string representing a int
     * @return  The integer or the max int value
     */
    private Integer toIntegerMax(String s) {
        if (s.isBlank())
            return Integer.MAX_VALUE;
        return Integer.valueOf(s);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateTable(deliveryService.searchProduct(textName.getText(),
                toDoubleMin(textRatingMin.getText()), toDoubleMax(textRatingMax.getText()),
                toIntegerMin(textCaloriesMin.getText()), toIntegerMax(textCaloriesMax.getText()),
                toIntegerMin(textProteinMin.getText()), toIntegerMax(textProteinMax.getText()),
                toIntegerMin(textFatMin.getText()), toIntegerMax(textFatMax.getText()),
                toIntegerMin(textSodiumMin.getText()), toIntegerMax(textSodiumMax.getText()),
                toIntegerMin(textPriceMin.getText()), toIntegerMax(textPriceMax.getText())));
    }
}
