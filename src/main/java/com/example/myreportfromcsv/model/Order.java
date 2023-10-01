package com.example.myreportfromcsv.model;




import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("order")
public class Order {
    @Id
    @Column("orderid")
    private long orderId;
    private String customer;
    private String location;
    private String address;
    private String code;
    @Column("dataready")
    private LocalDate dataReady;
    @Column("datapayment")
    private LocalDate dataPayment;
    private int price;
    private int profit;
    private int priority;
    @Column("ordertype")
    private String orderType;
    @Column("numberakt")
    private String numberAkt;
    @Column("intheprogress")
    private boolean inTheProgress;
    @Column("totheakt")
    private boolean toTheAkt;
}
