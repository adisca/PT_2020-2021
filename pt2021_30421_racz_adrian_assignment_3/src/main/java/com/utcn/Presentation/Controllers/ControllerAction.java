package com.utcn.Presentation.Controllers;

import com.utcn.BusinessLogic.Bll.AbstractBLL;
import com.utcn.Dao.Queries.AbstractDAO;
import com.utcn.Model.ObjectNotFoundException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controller for pressing the button and executing a command
 *
 * @param <T>   The model class
 * @param <BLL> The BLL class
 * @param <DAO> The DAO class
 */
public class ControllerAction<T, BLL extends AbstractBLL<T>, DAO extends AbstractDAO<T>> implements ActionListener {
    private ControllerRadioButtons controllerRadioButtons;
    private ArrayList<JTextField> textFields;
    private JTextField textFieldTargetId;
    private BLL abstractBLL;
    private JTable table;

    public ControllerAction() {
        controllerRadioButtons = null;
        textFields = null;
        textFieldTargetId = null;
        abstractBLL = null;
        table = null;
    }

    /**
     * Gets the JTable and creates it first if it is null
     *
     * @return  The JTable
     */
    public JTable getTable() {
        if (table == null) {
            table = new JTable();
            table.setModel(new DefaultTableModel());
            ((DefaultTableModel)table.getModel()).setColumnIdentifiers(abstractBLL.getTableHeaders());
            updateTable();
        }
        return table;
    }

    public void setControllerRadioButtons(ControllerRadioButtons controllerRadioButtons) {
        this.controllerRadioButtons = controllerRadioButtons;
    }

    public void setTextFieldsAndLabels(ArrayList<JTextField> textFields,
                                       JTextField textFieldTargetId) {
        this.textFields = textFields;
        this.textFieldTargetId = textFieldTargetId;
    }

    public void setTypes(Class<BLL> typeBLL, Class<DAO> typeDAO) {
        try {
            abstractBLL = typeBLL.getConstructor(typeDAO).newInstance(typeDAO.getConstructor().newInstance());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes the command chosen by the radio buttons with the BLL methods
     *
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (controllerRadioButtons.getOpConst()) {
            case ADD -> {
                try {
                    abstractBLL.insert(createDataArray());
                } catch (SQLException | IllegalAccessException exception) {
                    JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(table), exception.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            case EDIT -> {
                try {
                    abstractBLL.update(createDataArray(), Integer.valueOf(textFieldTargetId.getText()));
                } catch (SQLException | IllegalAccessException exception) {
                    JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(table), exception.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (ObjectNotFoundException objectNotFoundException) {
                    JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(table), "Invalid target id!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            case DELETE -> {
                try {
                    if (textFieldTargetId.getText().equals(""))
                        JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(table),
                                "Invalid target id!", "Error", JOptionPane.ERROR_MESSAGE);
                    else
                        abstractBLL.delete(Integer.valueOf(textFieldTargetId.getText()));
                } catch (SQLException exception) {
                    JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(table), exception.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (ObjectNotFoundException objectNotFoundException) {
                    JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(table), "Invalid target id!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        updateTable();
    }

    /**
     * Creates an array with data from the textboxes, representing the values of the row
     *
     * @return  An array of strings from the textboxes
     */
    private ArrayList<String> createDataArray() {
        ArrayList<String> data = new ArrayList<>();
        for (JTextField textField : textFields) {
            data.add(textField.getText());
        }
        return data;
    }

    /**
     * Updates the JTable
     */
    private void updateTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (String[] tableDatum : abstractBLL.getTableData()) {
            model.addRow(tableDatum);
        }
    }
}
