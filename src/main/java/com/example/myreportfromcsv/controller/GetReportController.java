package com.example.myreportfromcsv.controller;

import com.example.myreportfromcsv.model.Order;

import java.text.DateFormat;
import java.util.*;

public class GetReportController {
    DateFormat df;



    MakeModelController controller = new MakeModelController();
    ArrayList<Order> input =  controller.makeModels();
    StringBuilder stringBuilder;
    String textIsInTheProgress = "  в роботі: ";
    String textToTheAct = "  готово: ";
    String textPriority = "пріоритет <";


    public String getReportInProgress(){
        stringBuilder =   new StringBuilder();
        ArrayList<Order> temp =  new ArrayList<>();
        for (Order order:input ) {
            if(order.isInTheProgress()) temp.add(order);
        }
        int count = 1;
        Comparator<Order> priorityComparator = Comparator.comparing(Order::getPriority);
        Collections.sort(temp, priorityComparator);


        stringBuilder.append(textIsInTheProgress).append("\n");
        for (Order order:temp ) {
            stringBuilder
                    .append(count)
                    .append("). ")
                    .append(order.getCode())
                    .append("\t")
                    .append(" - ")
                    .append(textPriority)
                    .append(order.getPriority())
                    .append(">")
                    .append("\n");
        count++;
        }
       return stringBuilder.toString();
    }


    public String getReportToTheAct(){
        df = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.GERMANY);

        stringBuilder =   new StringBuilder();
        ArrayList<Order> temp =  new ArrayList<>();
        for (Order order:input ) {
            if(order.isToTheAkt()) temp.add(order);
        }
        int count = 1;

        Comparator<Order> priorityComparator = Comparator.comparing(Order::getDataReady);
        Collections.sort(temp, priorityComparator);

        stringBuilder.append(textToTheAct).append("\n");
        for (Order order:temp ) {
            stringBuilder
                    .append(count)
                    .append("). ")
                    .append(order.getCode())
                    .append("\t")
                    .append(" - ")
                    .append(df.format(order.getDataReady()))
                    .append("\n");
            count++;
        }
        return stringBuilder.toString();
    }

}
