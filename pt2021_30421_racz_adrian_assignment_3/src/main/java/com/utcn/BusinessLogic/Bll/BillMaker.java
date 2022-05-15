package com.utcn.BusinessLogic.Bll;

import com.utcn.Dao.Queries.ClientDAO;
import com.utcn.Dao.Queries.ProductDAO;
import com.utcn.Model.Order;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for creating the bills
 */
public class BillMaker {

    /**
     * Creates a bill for a given order
     *
     * @param order The order
     */
    public static void bill(Order order) {
        ClientDAO clientDAO = new ClientDAO();
        ProductDAO productDAO = new ProductDAO();

        try {
            FileWriter myWriter = new FileWriter("bill.txt");
            myWriter.write("OrderID: " + order.getId() + "\n");
            myWriter.write("Client: " + clientDAO.findById(order.getClientId()).getName() + "\n");
            myWriter.write("Product: " +  productDAO.findById(order.getProductId()).getName() + "\n");
            myWriter.write("Quantity: " +  order.getQuantity() + "\n");
            myWriter.write("Price: " +  order.getPrice() + "\n");
            myWriter.write("\nTotal: " +  order.getPrice() * order.getQuantity() + "\n");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
