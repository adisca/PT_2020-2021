package com.utcn.Data;

import com.utcn.Business.DataModels.MenuItem;
import com.utcn.Business.DataModels.Order;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * Class for managing writing to files
 */
public class MyFileWriter {

    /**
     * Creates a bill for a order
     *
     * @param filePath  The file path
     * @param list      The ordered items
     * @param price     The total price
     * @param id        The id of the client
     */
    public static void createBill(String filePath, List<MenuItem> list, Integer price, Integer id) {
        try {
            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write("ClientID: " + id + "\n");
            while (list.size() > 0){
                myWriter.write(list.get(0).getTitle() + " x " + Collections.frequency(list, list.get(0)));
                for (int i = 0; i < 50 - list.get(0).getTitle().length(); i++)
                    myWriter.write(".");
                myWriter.write(list.get(0).getPrice() + "\n");
                String str = list.get(0).getTitle();
                list = list.stream().filter(menuItem -> !menuItem.getTitle().equals(str)).collect(Collectors.toList());
            }
            myWriter.write("Total");
            for (int i = 0; i < 49; i++)
                myWriter.write(".");
            myWriter.write(price + "\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Time interval of the orders – a report should be generated with the orders performed between
     * a given start hour and a given end hour regardless the date.
     *
     * @param filePath  The file path
     * @param hashMap   The hashMap of the orders
     * @param hourMin   The minimum hour
     * @param hourMax   The maximum hour
     */
    public static void intervalReport(String filePath, Map<Order, Collection<MenuItem>> hashMap, Integer hourMin,
                                      Integer hourMax) {
        Map<Order, Collection<MenuItem>> result = hashMap.entrySet().stream().filter(map ->
                map.getKey().getOrderDate().getHour() >= hourMin && map.getKey().getOrderDate().getHour() <= hourMax).
                collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
        try {
            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write(result.toString());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * The products ordered more than a specified number of times so far.
     *
     * @param filePath  The file path
     * @param hashMap   The hashMap of the orders
     * @param nbOrderedMin  The minimum number of orders done
     */
    public static void orderedReport(String filePath, Map<Order, Collection<MenuItem>> hashMap, Integer nbOrderedMin) {
        try {
            FileWriter myWriter = new FileWriter(filePath);
            Map<String, List<MenuItem>> result =
                    hashMap.values().stream().flatMap(coll -> coll.stream()).collect(groupingBy(MenuItem::getTitle));
            result.forEach((key, value) -> {
                if (value.size() >= nbOrderedMin) {
                    try {
                        myWriter.write( key + " ordered " + value.size() + " times\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The clients that have ordered more than a specified number of times and the value of
     * the order was higher than a specified amount.
     *
     * @param filePath  The file path
     * @param hashMap   The hashMap of the orders
     * @param times     The min times a client ordered
     * @param value     The min value of the orders
     */
    public static void clientsReport(String filePath, Map<Order, Collection<MenuItem>> hashMap, Integer times,
                                     Integer value) {
        try {
            FileWriter myWriter = new FileWriter(filePath);
            hashMap = hashMap.entrySet().stream().filter(k ->
                    (Integer) k.getValue().stream().mapToInt(MenuItem::computePrice).sum() < value).
                    collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
            Map<Integer, List<Order>> result = hashMap.keySet().stream().collect(groupingBy(Order::getOrderID));
            result.forEach((key, val) -> {
                if (val.size() >= times)
                    try {
                        myWriter.write(key + ", ");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            });
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The products ordered within a specified day with the number of times they have been ordered.
     *
     * @param filePath  The file path
     * @param hashMap   The hashMap of the orders
     * @param date      The date of the day
     */
    public static void dayReport(String filePath, Map<Order, Collection<MenuItem>> hashMap, LocalDate date) {
        try {
            FileWriter myWriter = new FileWriter(filePath);
            hashMap = hashMap.entrySet().stream().filter(pair -> pair.getKey().getOrderDate().getDayOfYear() !=
                    date.getDayOfYear()).collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
            Map<String, List<MenuItem>> result = hashMap.values().stream().flatMap(coll -> coll.stream()).
                    collect(groupingBy(MenuItem::getTitle));
            result.forEach((key, value) -> {
                    try {
                        myWriter.write( key + " ordered " + value.size() + " times\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            });
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
