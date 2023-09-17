package com.example.myreportfromcsv.controller;

import com.example.myreportfromcsv.model.Order;
import lombok.Getter;

import java.text.DateFormat;
import java.util.*;

public class GetReportController {
    private DateFormat df;
    private MakeModelController controller = new MakeModelController();
    private ArrayList<Order> input = controller.makeModels();
    private StringBuilder stringBuilder;
    private String textIsInTheProgress = "  в роботі: ";
    private String textToTheAct = "  готово: ";
    private String textPriority = "пріоритет <";

    @Getter
    private ArrayList<Order> dataFromReportInProgress;
    @Getter
    private ArrayList<Order> dataFromReportToTheAct;

    public String reportInProgress;

    public String reportToTheAct;

    public void initialise() {
        reportInProgress = getReportInProgress();
        reportToTheAct = getReportToTheAct();
    }

    private String getReportInProgress() {
        stringBuilder = new StringBuilder();
        ArrayList<Order> temp = new ArrayList<>();
        for (Order order : input) {
            if (order.isInTheProgress()) temp.add(order);
        }
        int count = 1;
        Comparator<Order> priorityComparator = Comparator.comparing(Order::getPriority);
        Collections.sort(temp, priorityComparator);
        dataFromReportInProgress = temp;

        stringBuilder.append(textIsInTheProgress).append("\n");
        for (Order order : temp) {
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


    private String getReportToTheAct() {
        df = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.GERMANY);

        stringBuilder = new StringBuilder();
        ArrayList<Order> temp = new ArrayList<>();
        for (Order order : input) {
            if (order.isToTheAkt()) temp.add(order);
        }
        int count = 1;

        Comparator<Order> priorityComparator = Comparator.comparing(Order::getDataReady);
        Collections.sort(temp, priorityComparator);
        dataFromReportToTheAct = temp;
        stringBuilder.append(textToTheAct).append("\n");
        for (Order order : temp) {
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
