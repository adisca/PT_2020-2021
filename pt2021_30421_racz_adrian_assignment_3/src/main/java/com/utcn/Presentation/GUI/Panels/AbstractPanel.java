package com.utcn.Presentation.GUI.Panels;

import com.utcn.BusinessLogic.Bll.AbstractBLL;
import com.utcn.Dao.Queries.AbstractDAO;
import com.utcn.Presentation.Controllers.ControllerAction;
import com.utcn.Presentation.Controllers.ControllerRadioButtons;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

/**
 * A reflective JPanel
 *
 * @param <T>   The model class
 * @param <BLL> The BLL class
 * @param <DAO> The DAO class
 */
public class AbstractPanel<T, BLL extends AbstractBLL<T>, DAO extends AbstractDAO<T>> extends JPanel {
    private final Class<T> typeData;
    private final Class<BLL> typeBLL;
    private final Class<DAO> typeDAO;
    private final ControllerAction<T, BLL, DAO> controllerAction;
    private final ControllerRadioButtons controllerRadioButtons;

    @SuppressWarnings("unchecked")
    public AbstractPanel() {
        this.typeData = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.typeBLL = (Class<BLL>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        this.typeDAO = (Class<DAO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];

        controllerAction = new ControllerAction<>();
        controllerRadioButtons = new ControllerRadioButtons();

        controllerAction.setTypes(typeBLL, typeDAO);
        controllerAction.setControllerRadioButtons(controllerRadioButtons);

        setLayout(new BorderLayout());

        add(new ActionPanel(), BorderLayout.NORTH);
        add(new TextFieldsPanel(), BorderLayout.WEST);
        add(new TablePanel(), BorderLayout.CENTER);
    }

    /**
     * The panel with the radio buttons and the execute button
     */
    private class ActionPanel extends JPanel {
        ActionPanel() {
            ArrayList<JRadioButton> radioButtons = new ArrayList<>();

            JButton buttonExecute = new JButton("Execute");

            setBorder(BorderFactory.createLineBorder(Color.CYAN));

            JRadioButton buttonAdd = new JRadioButton("Insert");
            JRadioButton buttonEdit = new JRadioButton("Update");
            JRadioButton buttonDelete = new JRadioButton("Delete");
            buttonAdd.setSelected(true);

            ButtonGroup buttonGroup = new ButtonGroup();

            buttonGroup.add(buttonAdd);
            buttonGroup.add(buttonEdit);
            buttonGroup.add(buttonDelete);

            buttonExecute.addActionListener(controllerAction);

            radioButtons.add(buttonAdd);
            radioButtons.add(buttonEdit);
            radioButtons.add(buttonDelete);

            controllerRadioButtons.setRadioButtons(radioButtons);
            buttonAdd.addActionListener(controllerRadioButtons);
            buttonEdit.addActionListener(controllerRadioButtons);
            buttonDelete.addActionListener(controllerRadioButtons);

            setLayout(new GridBagLayout());

            GridBagConstraints gridBagConstraints = new GridBagConstraints();

            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            add(buttonAdd, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 0;
            add(buttonEdit, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 0;
            add(buttonDelete, gridBagConstraints);

            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridx = 4;
            gridBagConstraints.gridy = 0;
            add(buttonExecute, gridBagConstraints);
        }
    }

    /**
     * The panel with the table
     */
    private class TablePanel extends JPanel {
        TablePanel() {
            setLayout(new BorderLayout());

            setBorder(BorderFactory.createLineBorder(Color.ORANGE));

            add(new JScrollPane(controllerAction.getTable()), BorderLayout.CENTER);
        }
    }

    /**
     * The reflective panel for the textboxes
     */
    private class TextFieldsPanel extends JPanel {

        TextFieldsPanel() {
            ArrayList<JLabel> labels = new ArrayList<>();
            ArrayList<JTextField> textFields = new ArrayList<>();
            int cnt;

            for (Field field : typeData.getDeclaredFields()) {
                field.setAccessible(true);
                labels.add(new JLabel(field.getName() + ": "));
                textFields.add(new JTextField(20));
            }

            JLabel labelTargetId = new JLabel("Target id: ");
            JTextField textFieldTargetId = new JTextField(20);
            labelTargetId.setVisible(false);
            textFieldTargetId.setVisible(false);

            setBorder(BorderFactory.createLineBorder(Color.PINK));

            setLayout(new GridBagLayout());

            GridBagConstraints gridBagConstraints = new GridBagConstraints();

            gridBagConstraints.weightx = 0;
            gridBagConstraints.weighty = 0;

            gridBagConstraints.anchor = GridBagConstraints.LINE_END;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            add(labelTargetId, gridBagConstraints);

            cnt = 1;
            for (JLabel label : labels) {
                gridBagConstraints.anchor = GridBagConstraints.LINE_END;
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = cnt++;
                add(label, gridBagConstraints);
            }

            gridBagConstraints.anchor = GridBagConstraints.LINE_START;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 0;
            add(textFieldTargetId, gridBagConstraints);

            cnt = 1;
            for (JTextField textField : textFields) {
                gridBagConstraints.anchor = GridBagConstraints.LINE_START;
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = cnt++;
                add(textField, gridBagConstraints);
            }

            controllerAction.setTextFieldsAndLabels(textFields, textFieldTargetId);
            controllerRadioButtons.setTextFieldsAndLabels(labels, textFields, labelTargetId, textFieldTargetId);
        }
    }
}
