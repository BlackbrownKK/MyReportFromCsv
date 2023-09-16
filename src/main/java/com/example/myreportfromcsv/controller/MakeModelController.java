package com.example.myreportfromcsv.controller;

import com.example.myreportfromcsv.CsvReader;
import com.example.myreportfromcsv.model.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.Character.*;

public class MakeModelController {
    private boolean inTheProgress;
    private boolean toTheAkt;
    int priority;
    private ArrayList<Order> ordersFromData = new ArrayList<>();
    String dateFormat = "dd.MM.yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

    public ArrayList<Order> makeModels() {
        CsvReader csvReader = new CsvReader();
        csvReader.initialiseArray();
        String[][] data = csvReader.perCsV();
        for (int i = 0; i < data.length; i++) {
            Order order = new Order();
            order.setCustomer(data[i][0]);
            order.setLocation(data[i][1]);
            order.setAddress(data[i][2]);
            order.setCode(data[i][3]);
            order.setDataReady((readDataFromTable(data, i, 4)));
            order.setPrice(readIntFromTable(data, i, 5));
            order.setDataPayment((readDataFromTable(data, i, 6)));
            order.setPrice(readIntFromTable(data, i, 7));
            order.setOrderType(data[i][8]);
            order.setNumberAkt(data[i][9]);
            order.setInTheProgress(inTheProgress);
            order.setToTheAkt(toTheAkt);
            order.setPriority(priority);
            priority = 0;
            toTheAkt = false;
            inTheProgress = false;
            ordersFromData.add(order);
        }
        return ordersFromData;
    }

    private int readIntFromTable(String[][] data, int row, int column) {
        String input = data[row][column];
        if (input.isEmpty()) return 0;
        else {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                // Handle the case where the input is not a valid integer, if needed
                // You can throw an exception or set a default value here
                return 0; // Default to 0 in case of invalid input
            }
        }
    }

    private Date readDataFromTable(String[][] data, int row, int numColumn) {
        String input = data[row][numColumn];
        if (input.length() == 1) {
            char[] input2 = input.toCharArray();
            if (isDigit(input2[0])) priority = Integer.parseInt(String.valueOf(input2[0]));
            return null;
        }
        if (input.isEmpty()) return null;
        else if (input.equals("to the act")) {
            toTheAkt = true;
            return null;
        } else if (input.equals("in progress")) {
            inTheProgress = true;
            return null;
        } else try {
            return sdf.parse(data[row][numColumn]);

        } catch (NumberFormatException e) {

            // Handle the case where the input is not a valid integer, if needed
            // You can throw an exception or set a default value here
            return null; // Default to 0 in case of invalid input
        } catch (ParseException e) {
            return null; // Default to 0 in case of invalid input
        }
    }
}



