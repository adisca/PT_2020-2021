package com.utcn.BusinessLogic.Strategies;

import com.utcn.DataModels.Client;
import com.utcn.DataModels.ServiceQueue;

import java.util.List;

/**
 * Strategy interface
 */
public interface Strategy {
    /**
     * Adds a client in a queue
     *
     * @param serviceQueues The queues
     * @param client        The client to be added
     */
    void addClient(List<ServiceQueue> serviceQueues, Client client);

}
