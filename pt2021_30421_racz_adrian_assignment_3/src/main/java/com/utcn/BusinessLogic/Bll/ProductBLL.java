package com.utcn.BusinessLogic.Bll;

import com.utcn.Dao.Queries.ProductDAO;
import com.utcn.Model.Product;

/**
 *  AbstractBLL version for products
 */
public class ProductBLL extends AbstractBLL<Product>{
    public ProductBLL(ProductDAO abstractDAO) {
        super(abstractDAO);
    }
}
