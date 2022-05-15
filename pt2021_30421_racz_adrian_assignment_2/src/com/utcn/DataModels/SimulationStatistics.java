package com.utcn.DataModels;

/**
 * Class that stores the statistics of the simulation
 */
public class SimulationStatistics {
    private Integer totalWaitTime = 0;
    private Integer totalServeTime = 0;
    private Integer peakHour = 0;
    private Integer maxClients = 0;

    public Integer getTotalWaitTime() {
        return totalWaitTime;
    }

    public void setTotalWaitTime(Integer totalWaitTime) {
        this.totalWaitTime = totalWaitTime;
    }

    public Integer getTotalServeTime() {
        return totalServeTime;
    }

    public void setTotalServeTime(Integer totalServeTime) {
        this.totalServeTime = totalServeTime;
    }

    public Integer getPeakHour() {
        return peakHour;
    }

    public void setPeakHour(Integer peakHour) {
        this.peakHour = peakHour;
    }

    public Integer getMaxClients() {
        return maxClients;
    }

    public void setMaxClients(Integer maxClients) {
        this.maxClients = maxClients;
    }
}
