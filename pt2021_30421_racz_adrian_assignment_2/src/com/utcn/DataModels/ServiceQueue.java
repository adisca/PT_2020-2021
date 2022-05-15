package com.utcn.DataModels;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A queue thread
 */
public class ServiceQueue implements Runnable {
    private LinkedBlockingQueue<Client> clients;
    private AtomicInteger waitingPeriod;
    private AtomicBoolean running;
    private AtomicInteger simulationTime;

    public ServiceQueue(AtomicInteger simulationTime) {
        clients = new LinkedBlockingQueue<>();
        waitingPeriod = new AtomicInteger(0);
        running = new AtomicBoolean(true);
        this.simulationTime = simulationTime;
    }

    public Integer getWaitingPeriod() {
        return waitingPeriod.get();
    }

    public Integer getLength() {
        return clients.size();
    }

    public Boolean isEmpty() {
        return clients.size() == 0;
    }

    public void addClient(Client client) {
        waitingPeriod.addAndGet(client.getServiceTime());
        try {
            clients.put(client);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        running.set(false);
    }

    /**
     * Processes clients
     */
    @Override
    public void run() {
        Integer oldSimulationTime = simulationTime.get();

        while (running.get()) {
            while (oldSimulationTime.equals(simulationTime.get())) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                if (!running.get())
                    return;
            }
            oldSimulationTime = simulationTime.get();
            if (clients.size() != 0) {
                waitingPeriod.decrementAndGet();
                if (clients.peek().decrementServiceTime()) {
                    clients.remove();
                }
            }
        }
    }

    @Override
    public String toString() {
        if (clients.isEmpty())
            return "closed";
        StringBuilder str = new StringBuilder();
        for (Client client : clients) {
            if (client != clients.peek())
                str.append("; ");
            str.append(client.toString());
        }
        return str.toString();
    }
}
