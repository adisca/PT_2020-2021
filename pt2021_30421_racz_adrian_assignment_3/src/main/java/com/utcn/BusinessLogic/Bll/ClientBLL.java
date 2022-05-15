package com.utcn.BusinessLogic.Bll;

import com.utcn.Dao.Queries.ClientDAO;
import com.utcn.Model.Client;

/**
 *  AbstractBLL version for clients
 */
public class ClientBLL extends AbstractBLL<Client>{
    public ClientBLL(ClientDAO abstractDAO) {
        super(abstractDAO);
    }
}
