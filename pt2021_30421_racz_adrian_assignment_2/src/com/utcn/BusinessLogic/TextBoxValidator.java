package com.utcn.BusinessLogic;

import com.utcn.Exceptions.MinMaxException;
import com.utcn.Exceptions.NumberNegativeException;
import com.utcn.Exceptions.NumberZeroException;

/**
 * Class that validates the text boxes
 */
public class TextBoxValidator{

    /**
     * Validates the textboxes, throwing exceptions by case
     *
     * @param fieldSimTime      simulation time textbox
     * @param fieldQueues       number of queues textbox
     * @param fieldClients      number of clients textbox
     * @param fieldArrivalMin   minimum arrival time textbox
     * @param fieldArrivalMax   maximum arrival time textbox
     * @param fieldServiceMin   minimum service time textbox
     * @param fieldServiceMax   maximum service time textbox
     *
     * @throws NumberFormatException    Textbox is empty or not numerical
     * @throws NumberNegativeException  Textbox contains a negative number
     * @throws MinMaxException          Minimum arrival/service time is higher than their maximum counterpart
     */
    public void validate(String fieldSimTime, String fieldQueues, String fieldClients, String fieldArrivalMin,
                         String fieldArrivalMax, String fieldServiceMin, String fieldServiceMax)
            throws NumberFormatException, NumberNegativeException, MinMaxException, NumberZeroException {
        int simTime = Integer.parseInt(fieldSimTime);
        int queuesNb = Integer.parseInt(fieldQueues);
        int clientsNb = Integer.parseInt(fieldClients);
        int arrivalMin = Integer.parseInt(fieldArrivalMin);
        int arrivalMax = Integer.parseInt(fieldArrivalMax);
        int serviceMin = Integer.parseInt(fieldServiceMin);
        int serviceMax = Integer.parseInt(fieldServiceMax);

        if (simTime < 0 || queuesNb < 0 || clientsNb < 0 || arrivalMin < 0 || arrivalMax < 0 || serviceMin < 0 ||
                serviceMax < 0)
            throw (new NumberNegativeException());

        if (simTime == 0 || queuesNb == 0 || clientsNb == 0 || serviceMin == 0 || serviceMax == 0)
            throw (new NumberZeroException());

        if (arrivalMin > arrivalMax || serviceMin > serviceMax) {
            throw (new MinMaxException());
        }
    }

}
