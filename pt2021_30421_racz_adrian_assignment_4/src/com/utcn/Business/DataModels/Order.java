package com.utcn.Business.DataModels;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class for the order data
 */
public class Order implements Serializable {
    private final Integer orderID;
    private final Integer clientID;
    private final LocalDateTime orderDate;

    public Order(Integer orderID, Integer clientID, LocalDateTime orderDate) {
        this.orderID = orderID;
        this.clientID = clientID;
        this.orderDate = orderDate;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public Integer getClientID() {
        return clientID;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderID, order.orderID) && Objects.equals(clientID, order.clientID) &&
                Objects.equals(orderDate, order.orderDate);
    }

    /**
     * Overwritten hashCode for the hashMap encription
     *
     * @return  The result of the hash function
     */
    @Override
    public int hashCode() {
        return Objects.hash(orderID, clientID, orderDate);
    }
}
