package com.utcn.Data;

import com.utcn.Business.DataModels.MenuItem;
import com.utcn.Business.DataModels.Order;
import com.utcn.Business.DataModels.UserData;

import java.io.*;
import java.util.*;

/**
 * Class for managing serialization and deserialization from files
 */
public class Serialization {

    /**
     * Serializes any object
     *
     * @param filePath  The path of the file
     * @param object    The serializable object
     */
    public static void serialize(String filePath, Serializable object) {
        try {
            FileOutputStream file = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(object);
            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializes a list of MenuItem representing the menu
     *
     * @param filePath  The path of the file
     * @return          The menu
     */
    public static List<MenuItem> deserializeMenu(String filePath) {
        List<MenuItem> items = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(file);
            items = (List<MenuItem>) in.readObject();
            in.close();
            file.close();
        } catch (IOException e) {
            System.out.println("File " + filePath + " can't be read\n");
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return items;
    }

    /**
     * Deserializes a list of UserData representing the user credentials
     *
     * @param filePath    The path of the file
     * @return            The credentials list
     */
    public static List<UserData> deserializeUsers(String filePath) {
        List<UserData> items = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(file);
            items = (List<UserData>) in.readObject();
            in.close();
            file.close();
        } catch (IOException e) {
            System.out.println("File " + filePath + " can't be read\n");
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return items;
    }

    /**
     * Deserializes a map of Order and Collection of MenuItem representing the hashmap of the orders
     *
     * @param filePath      The path of the file
     * @return              The orders
     */
    public static Map<Order, Collection<MenuItem>> deserializeHashMap(String filePath) {
        Map<Order, Collection<MenuItem>> orders = new HashMap<>();
        try {
            FileInputStream file = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(file);
            orders = (Map<Order, Collection<MenuItem>>) in.readObject();
            in.close();
            file.close();
        } catch (IOException e) {
            System.out.println("File " + filePath + " can't be read\n");
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * Deserializes a list of Order representing the orders to be viewed by the employees
     *
     * @param filePath  The path of the file
     * @return          The orders
     */
    public static List<Order> deserializeEmployeeOrders(String filePath) {
        List<Order> items = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(file);
            items = (List<Order>) in.readObject();
            in.close();
            file.close();
        } catch (IOException e) {
            System.out.println("File " + filePath + " can't be read\n");
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return items;
    }

}
