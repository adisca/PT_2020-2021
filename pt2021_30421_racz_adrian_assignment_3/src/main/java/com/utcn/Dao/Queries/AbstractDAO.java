package com.utcn.Dao.Queries;

import com.utcn.Dao.Connection.ConnectionFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Reflective class for working with the database
 *
 * @param <T>   The model class
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Creates a query that gets all rows
     *
     * @return  The query
     */
    private String createShowQuery() {
        return "SELECT * FROM " + type.getSimpleName() + "s";
    }

    /**
     * Creates a query that gets a specific row
     *
     * @return  The query
     */
    private String createSelectQuery(String field) {
        return "SELECT * FROM " + type.getSimpleName() + "s WHERE " + field + " = ?";
    }

    /**
     * Creates a query that deletes a row
     *
     * @return  The query
     */
    private String createDeleteQuery(String field) {
        return "DELETE FROM " + type.getSimpleName() + "s WHERE " + field + " = ?";
    }

    /**
     * Creates a query that inserts a new row
     *
     * @return  The query
     */
    private String createInsertQuery(ArrayList<String> data) throws IllegalAccessException {    // NEED CHANGES HERE
        StringBuilder str = new StringBuilder();
        str.append("INSERT INTO ").append(type.getSimpleName()).append("s VALUES (");
        for (String datum : data) {
            if (datum.equals(""))
                str.append("NULL, ");
            else
                str.append("'").append(datum).append("', ");
        }
        str.setLength(str.length() - 2);
        str.append(")");
        return str.toString();
    }

    /**
     * Creates a query that updates a row
     *
     * @return  The query
     */
    private String createUpdateQuery(ArrayList<String> data, String dbField, Integer targetID)
            throws IllegalAccessException {
        T predecessor = findById(targetID);
        StringBuilder str = new StringBuilder();
        str.append("UPDATE ").append(type.getSimpleName()).append("s SET ");
        int cnt = 0;
        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            if (data.get(cnt).equals("NULL"))
                str.append(field.getName()).append(" = NULL, ");
            else if (data.get(cnt).equals("")) {
                if (field.get(predecessor) == null)
                    str.append(field.getName()).append(" = NULL, ");
                else
                    str.append(field.getName()).append(" = '").append(field.get(predecessor).toString()).append("', ");
            }
            else
                str.append(field.getName()).append(" = '").append(data.get(cnt)).append("', ");
            cnt++;
        }
        str.setLength(str.length() - 2);
        str.append(" WHERE ").append(dbField).append(" = ?");
        return str.toString();
    }

    /**
     * Creates a list of objects from a resultSet
     *
     * @param resultSet The results of a select query
     * @return  The list of results in T form
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();

        try {
            while (resultSet.next()) {
                T instance = type.getConstructor().newInstance();
                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException | IntrospectionException |
                InvocationTargetException | NoSuchMethodException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: createObjects " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Gets the names of the attributes of T
     *
     * @return  A list of the names of the attributes
     */
    public final List<String> retrieveProperties() {
        ArrayList<String> properties = new ArrayList<>();
        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            properties.add(field.getName());
        }
        return properties;
    }

    /**
     * Inserts a new row in the database
     *
     * @param data  values of the new row
     * @throws SQLException             Exception from sql
     * @throws IllegalAccessException   Exception from accessing fields
     */
    public void insert(ArrayList<String> data) throws SQLException, IllegalAccessException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            String query = createInsertQuery(data);
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException sqlException) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: findById " + sqlException.getMessage());
            throw sqlException;
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Updates an existing element from the database with new values
     *
     * @param data  the new values of the row
     * @param id    the id of the row to be modified
     * @throws SQLException             Exception from sql
     * @throws IllegalAccessException   Exception from accessing fields
     */
    public void update(ArrayList<String> data, Integer id) throws SQLException, IllegalAccessException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            String query = createUpdateQuery(data, "id", id);
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException sqlException) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: findById " + sqlException.getMessage());
            throw sqlException;
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Gets all rows of the table from the database
     *
     * @return  A list with the rows as T objects
     */
    public List<T> show() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createShowQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: findById " + sqlException.getMessage());
            sqlException.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;

    }

    /**
     * Deletes an element from the database
     *
     * @param id    the id of the row to be deleted
     * @throws SQLException Exception from sql
     */
    public void delete(Integer id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException sqlException) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: delete " + sqlException.getMessage());
            throw sqlException;
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Searches for a row with matching id
     *
     * @param id    The searched id
     * @return      The row as a T object
     */
    public T findById(Integer id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            List<T> results = createObjects(resultSet);
            if (results.size() > 0)
                return results.get(0);
        } catch (SQLException sqlException) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: findById " + sqlException.getMessage());
            sqlException.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

}
