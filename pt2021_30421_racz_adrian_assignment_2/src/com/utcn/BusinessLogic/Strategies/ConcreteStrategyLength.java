package com.utcn.BusinessLogic.Strategies;

import com.utcn.DataModels.Client;
import com.utcn.DataModels.ServiceQueue;

import java.util.List;

/**
 * Strategy that puts the client in the queue with the fewest clients
 */
public class ConcreteStrategyLength implements Strategy{

    /**
     * Adds a client in the shortest queue
     *
     * @param serviceQueues The queues
     * @param client        The client to be added
     */
    @Override
    public void addClient(List<ServiceQueue> serviceQueues, Client client) {
        ServiceQueue shortestServiceQueue = serviceQueues.get(0);
        for (ServiceQueue serviceQueue : serviceQueues) {
            if (serviceQueue.getLength() < shortestServiceQueue.getLength())
                shortestServiceQueue = serviceQueue;
        }
        shortestServiceQueue.addClient(client);
    }
}
