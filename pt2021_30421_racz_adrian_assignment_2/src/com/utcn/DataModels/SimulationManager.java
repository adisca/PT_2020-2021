package com.utcn.DataModels;

import com.utcn.BusinessLogic.ClientsComparator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A simulation manager
 */
public class SimulationManager implements Runnable {
    private AtomicBoolean running = new AtomicBoolean(false);
    private AtomicInteger simulationTime = new AtomicInteger(0);
    private Integer simulationTimeMax;
    private Integer serviceTimeMin;
    private Integer serviceTimeMax;
    private Integer arrivalTimeMin;
    private Integer arrivalTimeMax;
    private Integer nbOfQueues;
    private Integer nbOfClients;
    private Scheduler scheduler = new Scheduler();
    private SimulationStatistics simulationStatistics;
    private LinkedList<Client> incomingClients = new LinkedList<>();
    private LinkedBlockingQueue<String> messages = new LinkedBlockingQueue<>();

    public Scheduler getScheduler() {
        return scheduler;
    }

    public LinkedBlockingQueue<String> getMessages() {
        return messages;
    }

    public boolean isRunning() {
        return running.get();
    }

    public void initialize(Integer simulationTimeMax, Integer nbOfQueues, Integer nbOfClients, Integer arrivalTimeMin,
                           Integer arrivalTimeMax, Integer serviceTimeMin, Integer serviceTimeMax) {
        this.simulationTimeMax = simulationTimeMax;
        this.serviceTimeMin = serviceTimeMin;
        this.serviceTimeMax = serviceTimeMax;
        this.arrivalTimeMin = arrivalTimeMin;
        this.arrivalTimeMax = arrivalTimeMax;
        this.nbOfQueues = nbOfQueues;
        this.nbOfClients = nbOfClients;
        simulationTime.set(0);
        running.set(false);
        incomingClients.clear();
        simulationStatistics = new SimulationStatistics();
    }

    /**
     * Starts the simulation.
     * Generates the queues and random clients, then starts the simulation thread.
     */
    public void startSimulation() {
        Random random = new Random();
        for (int i = 0; i < nbOfClients; i++) {
            incomingClients.add(new Client(i + 1,
                    random.nextInt(arrivalTimeMax - arrivalTimeMin + 1) + arrivalTimeMin,
                    random.nextInt(serviceTimeMax - serviceTimeMin + 1) + serviceTimeMin));
        }
        incomingClients.sort(new ClientsComparator());
        ArrayList<ServiceQueue> serviceQueues = new ArrayList<>();
        for (int i = 0; i < nbOfQueues; i++) {
            serviceQueues.add(new ServiceQueue(simulationTime));
        }
        scheduler.setServiceQueues(serviceQueues);
        running.set(true);
        scheduler.startQueues();
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Stops the simulation and all queue threads
     */
    public void stopSimulation() {
        scheduler.stopQueues();
        running.set(false);
    }

    /**
     * Sends all clients that have arrived to the queues
     */
    private void sendClients() {
        Iterator<Client> iterator = incomingClients.iterator();

        while (iterator.hasNext()) {
            Client client = iterator.next();
            if (client.getArrivalTime().equals(simulationTime.get())) {
                scheduler.addClient(client);
                iterator.remove();
            } else
                break;
        }
    }

    /**
     * Updates the statistics
     */
    private void updateStatistics() {
        simulationStatistics.setTotalWaitTime(simulationStatistics.getTotalWaitTime() + scheduler.getWaitingClients());
        simulationStatistics.setTotalServeTime(simulationStatistics.getTotalServeTime()+scheduler.getServingClients());
        if (simulationStatistics.getMaxClients() < scheduler.getWaitingClients()) {
            simulationStatistics.setMaxClients(scheduler.getWaitingClients());
            simulationStatistics.setPeakHour(simulationTime.get());
        }
    }

    /**
     * Puts the statistics in the message BlockingQueue
     *
     * @throws InterruptedException the thread has been interrupted
     */
    private void sendStatistics() throws InterruptedException {
        if (nbOfClients - incomingClients.size() == 0) {
            messages.put("Average Waiting Time: 0\nAverage Serving Time: 0\nPeak Hour: 0\n");
            return;
        }

        messages.put("Average Waiting Time: " + simulationStatistics.getTotalWaitTime().floatValue() /
                (nbOfClients - incomingClients.size()) + "\nAverage Serving Time: " +
                simulationStatistics.getTotalServeTime().floatValue() / (nbOfClients - incomingClients.size()) +
                "\nPeak Hour: " + simulationStatistics.getPeakHour() + "\n\n");
    }

    /**
     * The main thread.
     * Manages the clients, messages and the passing of time.
     */
    @Override
    public void run() {
        try {
            while (running.get()) {
                sendClients();
                updateStatistics();

                messages.put(this + "\n");
                simulationTime.incrementAndGet();

                Thread.sleep(1000);
                if (simulationTime.get() >= simulationTimeMax ||
                        (incomingClients.isEmpty() && scheduler.areQueuesEmpty()))
                    stopSimulation();
            }
            messages.put(this + "\n");
            sendStatistics();
        } catch (InterruptedException e) {
            e.printStackTrace();
            stopSimulation();
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Time " + simulationTime + "\n" + "Waiting clients: ");
        if (incomingClients.isEmpty())
            str.append("none");
        for (Client client : incomingClients) {
            if (client != incomingClients.getFirst())
                str.append("; ");
            str.append(client.toString());
        }
        str.append("\n").append(scheduler.toString());
        return str.toString();
    }
}
