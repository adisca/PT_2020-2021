package com.utcn.Presentation.Panels;

import com.utcn.Business.DataModels.MenuItem;
import com.utcn.Business.DeliveryService;
import com.utcn.Data.MyFileWriter;
import com.utcn.Presentation.Controllers.Admin.ControllerAdminTable;
import com.utcn.Presentation.Controllers.Admin.ControllerCompositeTable;
import com.utcn.Util.ExistentUniqueFieldException;
import com.utcn.Util.TableColumnAdapter;
import com.utcn.Util.WrongClassException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;

/**
 * The main panel of the administrator frame.
 * Contains 4 sub-panels
 */
public class AdminPanel extends JPanel {
    private static final String intervalReportFile = "intervalReport.txt";
    private static final String orderedReportFile = "orderedReport.txt";
    private static final String clientsReportFile = "clientsReport.txt";
    private static final String dayReportFile = "dayReport.txt";

    private JTable mainTable = new JTable();
    private ControllerAdminTable controllerAdminTable;
    private ControllerCompositeTable controllerCompositeTable;
    private DeliveryService deliveryService;

    public AdminPanel(DeliveryService deliveryService) {
        setLayout(new BorderLayout());

        this.deliveryService = deliveryService;
        controllerAdminTable = new ControllerAdminTable(this.deliveryService, this);
        controllerCompositeTable = new ControllerCompositeTable(controllerAdminTable, this.deliveryService, this);

        add(new ManagePanel(), BorderLayout.WEST);
        add(new ReportPanel(), BorderLayout.SOUTH);
        add(new CompositePanel(), BorderLayout.EAST);
        add(new TablePanel(), BorderLayout.CENTER);
    }

    /**
     * Panel for management of base products
     */
    private class ManagePanel extends JPanel {
        public ManagePanel() {
            JLabel labelName = new JLabel("Name: ");
            JLabel labelRating = new JLabel("Rating: ");
            JLabel labelCalories = new JLabel("Calories: ");
            JLabel labelProtein = new JLabel("Protein: ");
            JLabel labelFat = new JLabel("Fat: ");
            JLabel labelSodium = new JLabel("Sodium: ");
            JLabel labelPrice = new JLabel("Price: ");

            JTextField textName = new JTextField(10);
            JTextField textRating = new JTextField(10);
            JTextField textCalories = new JTextField(10);
            JTextField textProtein = new JTextField(10);
            JTextField textFat = new JTextField(10);
            JTextField textSodium = new JTextField(10);
            JTextField textPrice = new JTextField(10);

            JButton btnAdd = new JButton("ADD");
            JButton btnModify = new JButton("EDIT");
            JButton btnDelete = new JButton("DELETE");
            JButton btnSearch = new JButton("SEARCH");

            setLayout(new GridBagLayout());
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;

            gridBagConstraints.anchor = GridBagConstraints.LINE_END;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            add(labelName, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.LINE_START;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 0;
            add(textName, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.LINE_END;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            add(labelRating, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.LINE_START;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 1;
            add(textRating, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.LINE_END;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 2;
            add(labelCalories, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.LINE_START;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 2;
            add(textCalories, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.LINE_END;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 3;
            add(labelProtein, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.LINE_START;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 3;
            add(textProtein, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.LINE_END;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 4;
            add(labelFat, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.LINE_START;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 4;
            add(textFat, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.LINE_END;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 5;
            add(labelSodium, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.LINE_START;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 5;
            add(textSodium, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.LINE_END;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 6;
            add(labelPrice, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.LINE_START;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 6;
            add(textPrice, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 7;
            add(btnAdd, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 7;
            add(btnModify, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 8;
            add(btnSearch, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 8;
            add(btnDelete, gridBagConstraints);

            controllerAdminTable.setSearchFields(textName, textRating, textCalories, textProtein, textFat, textSodium,
                    textPrice);
            btnAdd.addActionListener(controllerAdminTable);
            btnModify.addActionListener(e -> {
                if (mainTable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this),
                            "No table row has been selected!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    String newTitle = "";
                    double newRating = -1.;
                    int newCalories = -1;
                    int newProteins = -1;
                    int newFat = -1;
                    int newSodium = -1;
                    int newPrice = -1;


                    if (!textName.getText().isBlank())
                            newTitle = textName.getText();
                    if (!textRating.getText().isBlank()) {
                        if (Double.parseDouble(textRating.getText()) >= 0. &&
                                Double.parseDouble(textRating.getText()) <= 5.)
                            newRating = Double.parseDouble(textRating.getText());
                        else {
                            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this),
                                    "Rating out of bounds", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    if (!textCalories.getText().isBlank()) {
                        if (Integer.parseInt(textCalories.getText()) >= 0)
                            newCalories = Integer.parseInt(textCalories.getText());
                        else {
                            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this),
                                    "Calories out of bounds", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    if (!textProtein.getText().isBlank()) {
                        if (Integer.parseInt(textProtein.getText()) >= 0)
                            newProteins = Integer.parseInt(textProtein.getText());
                        else {
                            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this),
                                    "Proteins out of bounds", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    if (!textFat.getText().isBlank()) {
                        if (Integer.parseInt(textFat.getText()) >= 0)
                            newFat = Integer.parseInt(textFat.getText());
                        else {
                            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this),
                                    "Fat out of bounds", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    if (!textSodium.getText().isBlank()) {
                        if (Integer.parseInt(textSodium.getText()) >= 0)
                            newSodium = Integer.parseInt(textSodium.getText());
                        else {
                            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this),
                                    "Sodium out of bounds", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    if (!textPrice.getText().isBlank()) {
                        if (Integer.parseInt(textPrice.getText()) >= 0)
                            newPrice = Integer.parseInt(textPrice.getText());
                        else {
                            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this),
                                    "Price out of bounds", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    try {
                        deliveryService.modifyProduct(
                                (String) mainTable.getModel().getValueAt(mainTable.getSelectedRow(), 0),
                                newTitle, newRating, newCalories, newProteins, newFat, newSodium, newPrice);
                        controllerAdminTable.updateTable((java.util.List<MenuItem>) deliveryService.getMenu());
                        TableColumnAdapter.stretchColumns(mainTable);
                    } catch (WrongClassException ex) {
                        JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this),
                                "Only base products can be modified in this way",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (ExistentUniqueFieldException ex) {
                        JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this),
                                "Name exists", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            btnDelete.addActionListener(e -> {
                deliveryService.removeProduct((String) mainTable.getModel().
                        getValueAt(mainTable.getSelectedRow(), 0));
                controllerAdminTable.updateTable((java.util.List<MenuItem>) deliveryService.getMenu());
                TableColumnAdapter.stretchColumns(mainTable);
            });

            btnSearch.addActionListener(e ->
                    JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this),
                            """
                                    Not implemented
                                    Had a nice idea but not enough time
                                    It is a nice placeholder, making the panel look a little better""",
                            "Under Construction", JOptionPane.INFORMATION_MESSAGE));

        }
    }

    /**
     * Panel for management of reports
     */
    private class ReportPanel extends JPanel {
        public ReportPanel() {
            JTextField textHourMin = new JTextField(10);
            JTextField textHourMax = new JTextField(10);
            JTextField textOrderedMin = new JTextField(10);
            JTextField textClientTimesMin = new JTextField(10);
            JTextField textClientValueMin = new JTextField(10);
            JTextField textDay = new JTextField(10);

            JButton btnInterval = new JButton("INTERVAL REPORT");
            JButton btnOrdered = new JButton("ORDERED REPORT");
            JButton btnClients = new JButton("CLIENTS REPORT");
            JButton btnDay = new JButton("DAY REPORT");

            setLayout(new GridBagLayout());
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;

            // 1st column

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            add(textHourMin, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            add(textHourMax, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 2;
            add(btnInterval, gridBagConstraints);

            // 2nd column

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridheight = 2;
            add(textOrderedMin, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.gridheight = 1;
            add(btnOrdered, gridBagConstraints);

            // 3rd column

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 0;
            add(textClientTimesMin, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 1;
            add(textClientValueMin, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 2;
            add(btnClients, gridBagConstraints);

            // 4th column

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 3;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridheight = 2;
            add(textDay, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 3;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.gridheight = 1;
            add(btnDay, gridBagConstraints);

            btnInterval.addActionListener(e -> {
                MyFileWriter.intervalReport(intervalReportFile,
                    deliveryService.getOrders(), Integer.valueOf(textHourMin.getText()),
                    Integer.valueOf(textHourMax.getText()));
                JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this),
                        "Report created", "Success", JOptionPane.INFORMATION_MESSAGE);
            });
            btnOrdered.addActionListener(e -> {
                MyFileWriter.orderedReport(orderedReportFile, deliveryService.getOrders(),
                        Integer.valueOf(textOrderedMin.getText()));
                JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this),
                        "Report created", "Success", JOptionPane.INFORMATION_MESSAGE);
            });
            btnClients.addActionListener(e -> {
                MyFileWriter.clientsReport(clientsReportFile, deliveryService.getOrders(),
                        Integer.valueOf(textClientTimesMin.getText()), Integer.valueOf(textClientValueMin.getText()));
                JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this),
                        "Report created", "Success", JOptionPane.INFORMATION_MESSAGE);
            });
            btnDay.addActionListener(e -> {
                MyFileWriter.dayReport(dayReportFile, deliveryService.getOrders(),
                        LocalDate.parse(textDay.getText()));
                JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this),
                        "Report created", "Success", JOptionPane.INFORMATION_MESSAGE);
            });
        }
    }

    /**
     * Panel for management of composite products
     */
    private class CompositePanel extends JPanel {
        public CompositePanel() {
            JLabel labelName = new JLabel("Name: ");
            JTextField textName = new JTextField(20);

            JButton btnImport = new JButton("IMPORT");
            JTextField textImport = new JTextField(20);

            JButton btnAdd = new JButton("ADD");
            JButton btnCreate = new JButton("CREATE");
            JButton btnModify = new JButton("EDIT");
            JButton btnDelete = new JButton("REMOVE");

            JTable table = new JTable();
            table.setModel(new DefaultTableModel());
            JScrollPane scrollPane = new JScrollPane(table);

            setLayout(new GridBagLayout());
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;

            gridBagConstraints.anchor = GridBagConstraints.LINE_END;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            add(labelName, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.LINE_START;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 0;
            add(textName, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            add(btnAdd, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 1;
            add(btnCreate, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 2;
            add(btnModify, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 2;
            add(btnDelete, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 3;
            gridBagConstraints.gridheight = 2;
            gridBagConstraints.gridwidth = 2;
            add(scrollPane, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.LINE_END;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 5;
            gridBagConstraints.gridheight = 1;
            gridBagConstraints.gridwidth = 1;
            add(btnImport, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.LINE_START;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 5;
            add(textImport, gridBagConstraints);

            controllerCompositeTable.setText(textName);
            controllerCompositeTable.setCompositeTable(table);

            btnCreate.addActionListener(controllerCompositeTable);
            btnAdd.addActionListener(e -> controllerCompositeTable.addProduct());
            btnDelete.addActionListener(e -> controllerCompositeTable.removeProduct());
            btnModify.addActionListener(e -> controllerCompositeTable.editProduct());
            btnImport.addActionListener(e -> {
                try {
                    deliveryService.importCSV(textImport.getText());
                    controllerAdminTable.updateTable((java.util.List<MenuItem>) deliveryService.getMenu());
                    controllerCompositeTable.resetProduct();
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this),
                            "File can't be read", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
    }

    /**
     * Panel with the main table
     */
    private class TablePanel extends JPanel {
        public TablePanel() {
            JScrollPane scrollPane = new JScrollPane(mainTable);

            mainTable.setModel(new DefaultTableModel());

            setLayout(new BorderLayout());
            add(scrollPane, BorderLayout.CENTER);

            controllerAdminTable.setMainTable(mainTable);
            controllerCompositeTable.setMainTable(mainTable);
        }
    }

}
