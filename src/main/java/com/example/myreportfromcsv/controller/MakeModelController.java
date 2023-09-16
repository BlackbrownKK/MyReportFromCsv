package com.example.myreportfromcsv.controller;

import com.example.myreportfromcsv.CsvReader;
import com.example.myreportfromcsv.model.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MakeModelController {

    public ArrayList<Order> ordersFromData = new ArrayList<>();
    String dateFormat = "dd.MM.yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

    public void makeModels() {// todo parce data and int from the String [] []
        CsvReader csvReader = new CsvReader();
        csvReader.initialiseArray();
        String[][] data = csvReader.perCsV();
        for (int i = 0; i < data.length; i++) {
            Order order = new Order();
            order.setCustomer(data[i][0]);
            order.setLocation(data[i][1]);
            order.setAddress(data[i][2]);
            order.setCode(data[i][3]);
            order.setOrderType(data[i][8]);
            try {
                order.setDataReady(sdf.parse(data[i][4]));
                order.setDataPayment(sdf.parse(data[i][5]));
                order.setPrice(Integer.getInteger(data[i][6]));
                order.setPrice(Integer.getInteger(data[i][7]));
                order.setNumberAkt(Integer.getInteger(data[i][9]));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            ordersFromData.add(order);
        }
    }
}

