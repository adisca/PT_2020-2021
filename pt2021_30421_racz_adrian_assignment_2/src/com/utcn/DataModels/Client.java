package com.utcn.DataModels;

/**
 * The client data structure
 */
public class Client {
    private Integer id;
    private Integer arrivalTime;
    private Integer serviceTime;

    public Client(Integer id, Integer arrivalTime, Integer serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public Integer getServiceTime() {
        return serviceTime;
    }

    /**
     * Decrements serviceTime by 1
     *
     * @return  True if serviceTime is negative, false otherwise
     */
    public boolean decrementServiceTime() {
        serviceTime--;
        return serviceTime <= 0;
    }

    @Override
    public String toString() {
        return  "(" + id + ", " + arrivalTime + ", " + serviceTime + ")";
    }
}
