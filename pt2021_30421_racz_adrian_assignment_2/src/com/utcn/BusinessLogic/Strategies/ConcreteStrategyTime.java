package com.utcn.BusinessLogic.Strategies;

import com.utcn.DataModels.Client;
import com.utcn.DataModels.ServiceQueue;

import java.util.List;

/**
 * Strategy that puts the client in the queue with the lowest waiting time
 */
public class ConcreteStrategyTime implements Strategy{

    /**
     * Adds a client in the queue with the lowest waiting time
     *
     * @param serviceQueues The queues
     * @param client        The client to be added
     */
    @Override
    public void addClient(List<ServiceQueue> serviceQueues, Client client) {
        ServiceQueue fastestServiceQueue = serviceQueues.get(0);
        for (ServiceQueue serviceQueue : serviceQueues) {
            if (serviceQueue.getWaitingPeriod() < fastestServiceQueue.getWaitingPeriod())
                fastestServiceQueue = serviceQueue;
        }
        fastestServiceQueue.addClient(client);
    }
}
