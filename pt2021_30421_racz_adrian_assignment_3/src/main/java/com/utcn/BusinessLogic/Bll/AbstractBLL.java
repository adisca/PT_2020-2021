package com.utcn.BusinessLogic.Bll;

import com.utcn.Dao.Queries.AbstractDAO;
import com.utcn.Model.ObjectNotFoundException;
import com.utcn.Model.Order;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Reflective class for business logic functions on database
 *
 * @param <T>   The model class
 */
public class AbstractBLL<T> {
    private AbstractDAO<T> abstractDAO;

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractBLL(AbstractDAO<T> abstractDAO) {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.abstractDAO = abstractDAO;
    }

    /**
     * Gets all the table's fields and returns it as a matrix for the swing table
     *
     * @return  The table data as a string matrix
     */
    public String[][] getTableData() {
        ArrayList<T> data = (ArrayList<T>) abstractDAO.show();
        String[][] result = new String[data.size()][];

        int i = 0;
        int j;
        for (T datum : data) {
            j = 0;
            result[i] = new String[type.getDeclaredFields().length];
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    if (field.get(datum) == null)
                        result[i][j++] = "";
                    else
                        result[i][j++] = field.get(datum).toString();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            i++;
        }
        return result;
    }

    /**
     * Uses reflection to get the column names from the table
     *
     * @return  The array of headers
     */
    public String[] getTableHeaders() {
        ArrayList<String> data = (ArrayList<String>) abstractDAO.retrieveProperties();
        String[] result = new String[data.size()];
        int i = 0;

        for (String datum : data) {
            result[i++] = datum;
        }
        return result;
    }

    /**
     * Inserts a new element in the database
     *
     * @param data  the values of the new row
     * @throws SQLException             Exception from sql
     * @throws IllegalAccessException   Exception from accessing fields
     */
    public void insert(ArrayList<String> data) throws SQLException, IllegalAccessException {
        abstractDAO.insert(data);
        T instance = abstractDAO.findById(Integer.valueOf(data.get(0)));
        if (instance instanceof Order)
            BillMaker.bill((Order)instance);
    }

    /**
     * Updates an existing element from the database with new values
     *
     * @param data      the new values of the row
     * @param targetID  the id of the row to be modified
     * @throws SQLException             Exception from sql
     * @throws IllegalAccessException   Exception from accessing fields
     * @throws ObjectNotFoundException  There is no row with that id in the table
     */
    public void update(ArrayList<String> data, Integer targetID) throws SQLException, IllegalAccessException,
            ObjectNotFoundException {
        if (abstractDAO.findById(targetID) == null)
            throw new ObjectNotFoundException();
        abstractDAO.update(data, targetID);
    }

    /**
     * Deletes an element from the database
     *
     * @param targetID  the id of the row to be deleted
     * @throws SQLException             Exception from sql
     * @throws ObjectNotFoundException  There is no row with that id in the table
     */
    public void delete(Integer targetID) throws SQLException, ObjectNotFoundException {
        if (abstractDAO.findById(targetID) == null)
            throw new ObjectNotFoundException();
        abstractDAO.delete(targetID);
    }

}
