package com.example.myreportfromcsv.model;



import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Data
public class Order {
    private String customer;
    private String location;
    private String address;
    private String code;
    private Date dataReady;
    private Date dataPayment;
    private int price;
    private int profit;
    private String orderType;
    private int numberAkt;
}
