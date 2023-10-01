package com.example.myreportfromcsv.controller;

import com.example.myreportfromcsv.reader.CsvReader;
import com.example.myreportfromcsv.model.Order;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.Character.*;
@Controller
public class ModelController {
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
            Order temp = Order.builder()
                    .customer(data[i][0])
                    .location(data[i][1])
                    .address(data[i][2])
                    .code(data[i][3])
                    .dataReady((readDataFromTable(data, i, 4)))
                    .price(readIntFromTable(data, i, 5))
                    .dataPayment((readDataFromTable(data, i, 6)))
                    .profit(readIntFromTable(data, i, 7))
                    .orderType(data[i][8])
                    .numberAkt(data[i][9])
                    .inTheProgress(inTheProgress)
                    .toTheAkt(toTheAkt)
                    .priority(priority).build();
            priority = 0;
            toTheAkt = false;
            inTheProgress = false;
            ordersFromData.add(temp);
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

    private LocalDate readDataFromTable(String[][] data, int row, int numColumn) {
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
            //return sdf.parse(data[row][numColumn]);
            Date result = sdf.parse(data[row][numColumn]);
            Instant instant = result.toInstant(); // Convert Date to Instant
            ZoneId zoneId = ZoneId.systemDefault(); // Specify the time zone you want to use
            return instant.atZone(zoneId).toLocalDate(); // Convert Instant to LocalDate

        } catch (NumberFormatException e) {

            // Handle the case where the input is not a valid integer, if needed
            // You can throw an exception or set a default value here
            return null; // Default to 0 in case of invalid input
        } catch (ParseException e) {
            return null; // Default to 0 in case of invalid input
        }
    }
}



