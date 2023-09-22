package com.example.myreportfromcsv.model;



import lombok.Data;

import java.time.LocalDate;

@Data
public class Order {
    private String customer;
    private String location;
    private String address;
    private String code;
    private LocalDate dataReady;
    private LocalDate dataPayment;
    private int price;
    private int profit;
    private int priority;
    private String orderType;
    private String numberAkt;
    private boolean inTheProgress;
    private boolean toTheAkt;
}
