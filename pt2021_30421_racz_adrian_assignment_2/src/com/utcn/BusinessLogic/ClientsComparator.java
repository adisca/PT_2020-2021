package com.utcn.BusinessLogic;

import com.utcn.DataModels.Client;

import java.util.Comparator;

/**
 * Comparator for client sorting
 */
public class ClientsComparator implements Comparator<Client> {

    /**
     * Compares 2 clients by arrival time.
     * If it is equal it also reverse compares them by service time
     *
     * @param client1   first client
     * @param client2   second client
     * @return          the result of the comparison
     */
    @Override
    public int compare(Client client1, Client client2) {
        int arrivalCompare = client1.getArrivalTime().compareTo(client2.getArrivalTime());
        int serviceCompare = client2.getServiceTime().compareTo(client1.getServiceTime());

        return arrivalCompare == 0 ? serviceCompare : arrivalCompare;
    }

}
