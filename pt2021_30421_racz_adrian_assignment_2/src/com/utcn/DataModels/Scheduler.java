package com.utcn.DataModels;

import com.utcn.BusinessLogic.Strategies.ConcreteStrategyLength;
import com.utcn.BusinessLogic.Strategies.ConcreteStrategyTime;
import com.utcn.BusinessLogic.Strategies.Strategy;

import java.util.ArrayList;

/**
 * The scheduler that manages the queues
 */
public class Scheduler {
    private ArrayList<ServiceQueue> serviceQueues;
    private Strategy strategy = new ConcreteStrategyTime();

    public void chooseStrategy(QueuePolicy policy) {
        if (policy == QueuePolicy.FASTEST_TIME)
            this.strategy = new ConcreteStrategyTime();
        else if (policy == QueuePolicy.SHORTEST_QUEUE)
            this.strategy = new ConcreteStrategyLength();
    }

    public void setServiceQueues(ArrayList<ServiceQueue> serviceQueues) {
        this.serviceQueues = serviceQueues;
    }

    /**
     * Starts a thread for each queue
     */
    public void startQueues() {
        for (ServiceQueue serviceQueue : serviceQueues) {
            Thread thread = new Thread(serviceQueue);
            thread.start();
        }
    }

    /**
     * Stops all queue threads
     */
    public void stopQueues() {
        for (ServiceQueue serviceQueue : serviceQueues) {
            serviceQueue.stop();
        }
    }

    /**
     * Adds a client to a queue according to the strategy
     *
     * @param client    The client to be added
     */
    public void addClient(Client client) {
        strategy.addClient(serviceQueues, client);
    }

    /**
     * Checks if all the queues are empty
     *
     * @return  True if they are, false otherwise
     */
    public Boolean areQueuesEmpty() {
        for (ServiceQueue serviceQueue : serviceQueues) {
            if (serviceQueue.getWaitingPeriod() != 0)
                return false;
        }
        return true;
    }

    /**
     * Gets the number of queues that are serving a client
     *
     * @return  the number of clients being served
     */
    public Integer getServingClients() {
        Integer nbServingClients = 0;
        for (ServiceQueue serviceQueue : serviceQueues) {
            if (!serviceQueue.isEmpty())
                nbServingClients++;
        }
        return nbServingClients;
    }

    /**
     * Gets the number of clients that are waiting in any queue
     *
     * @return  the number of clients in the queues
     */
    public Integer getWaitingClients() {
        Integer nbClients = 0;
        for (ServiceQueue serviceQueue : serviceQueues) {
            nbClients += serviceQueue.getLength();
        }
        return nbClients;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        int i = 1;
        for (ServiceQueue serviceQueue : serviceQueues) {
            str.append("Queue ").append(i++).append(": ").append(serviceQueue.toString()).append("\n");
        }
        return str.toString();
    }
}
