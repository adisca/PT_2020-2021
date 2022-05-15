package com.utcn.BusinessLogic.Bll;

import com.utcn.Dao.Queries.OrderDAO;
import com.utcn.Model.Order;

/**
 *  AbstractBLL version for orders
 */
public class OrderBLL extends AbstractBLL<Order> {
    public OrderBLL(OrderDAO abstractDAO) {
        super(abstractDAO);
    }
}
