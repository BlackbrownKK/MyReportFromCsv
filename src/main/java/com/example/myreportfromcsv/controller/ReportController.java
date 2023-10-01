package com.example.myreportfromcsv.controller;

import com.example.myreportfromcsv.model.Order;
import com.example.myreportfromcsv.service.ReportService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;


public class ReportController {
    ReportService reportService;
    private StringBuilder stringBuilder;
    @Getter
    private String reportInProgress;
    @Getter
    private String reportReportReadyLastWeek;
    @Getter
    private String reportToTheAct;
    @Getter
    private String reportMain;
    @Getter
    private String reportProfitMain;
    @Getter
    private String intensiveReport;
    @Getter
    private HashMap<Integer, Integer> profitDataAllTime;
    private final String textIsInTheProgress = "  в роботі: ";
    private final String textToTheAct = "  готово: ";
    private final String textForPayment = "  в Акт: ";
    private final String textPriority = "пріоритет <";
    private final String textIntensive = "інтенсивність";
    @Getter
    private static ArrayList<Order> orders;

    public void initialise() {
        reportService = new ReportService();
        reportService.initialise();
        orders = reportService.getOrders();
        reportInProgress = getReportInProgress(reportService.getDataReportInProgress());
        reportReportReadyLastWeek = getReportReadyLastWeek(reportService.getDataFromPaymentReadyLastWeek());
        reportToTheAct = getReportToTheAllAct(reportService.getDataFromReportToTheAct());
        reportMain = getReportFromAllTime(reportService.getTableByMonths());
        reportProfitMain = getReportProfitAllTime(reportService.getProfitByMonths());
        intensiveReport = getIntensiveReportToResult(reportService.getIntensiveReport());
        profitDataAllTime = getDataProfitAllTime(reportService.getProfitByMonths(), 2023);
    }

    private String getIntensiveReportToResult(HashMap<Integer, Integer> input) {
        stringBuilder = new StringBuilder();
        stringBuilder.append(textIntensive).append("\n");
        for (Integer month : input.keySet()) {
            stringBuilder
                    .append(month.intValue())
                    .append(" - ")
                    .append(input.get(month))
                    .append("\n");
        }
        int sum = input.values().stream().mapToInt(Integer::intValue).sum();
        stringBuilder.append("Year - " + sum);
        return stringBuilder.toString();
    }


    private String getReportInProgress(List<Order> input) {
        stringBuilder = new StringBuilder();
        stringBuilder.append(textIsInTheProgress).append("\n");
        int count = 1;
        for (Order order : input) {
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

    private String getReportReadyLastWeek(List<Order> input) {
        stringBuilder = new StringBuilder();
        int count = 1;
        stringBuilder.append(textForPayment).append("\n");
        for (Order order : input) {
            stringBuilder
                    .append(count)
                    .append("). ")
                    .append(order.getCode())
                    .append("\t")
                    .append(" - ")
                    .append(order.getDataReady())
                    .append("\n");
            count++;
        }
        return stringBuilder.toString();
    }

    private String getReportToTheAllAct(List<Order> input) {
        stringBuilder = new StringBuilder();
        int count = 1;
        stringBuilder.append(textToTheAct).append("\n");
        for (Order order : input) {
            stringBuilder
                    .append(count)
                    .append("). ")
                    .append(order.getCode())
                    .append("\t")
                    .append(" - ")
                    .append(order.getDataReady())
                    .append("\n");
            count++;
        }
        return stringBuilder.toString();
    }

    private String getReportFromAllTime(HashMap<Integer, HashMap<Integer, Integer>> input) {
        HashMap<Integer, HashMap<Integer, Integer>> inputSort = sortByKeyBigForYear(input);
        stringBuilder = new StringBuilder();
        inputSort.forEach((year, innerMap) -> {
            int sumOfYear = reportService.getSumOfYear(innerMap);
            stringBuilder.append(year).append("-").append(sumOfYear).append(" : ");
            innerMap.forEach((month, value) -> {
                stringBuilder.append(month).append("-").append(value).append("; ");
            });
            stringBuilder.append("\n");
        });
        return stringBuilder.toString();
    }

    private String getReportProfitAllTime(HashMap<Integer, HashMap<Integer, Integer>> input) {
        HashMap<Integer, HashMap<Integer, Integer>> inputSort = sortByKeyBigForYear(input);
        stringBuilder = new StringBuilder();
        inputSort.forEach((year, innerMap) -> {
            int sumOfYear = reportService.getSumOfYear(innerMap);
            stringBuilder.append(year).append("-").append(sumOfYear).append(" : ");
            innerMap.forEach((month, value) -> {
                stringBuilder.append(month).append("-").append(value).append("; ");
            });
            stringBuilder.append("\n");
        });
        return stringBuilder.toString();
    }

    private HashMap<Integer, Integer> getDataProfitAllTime(HashMap<Integer, HashMap<Integer, Integer>> input, int CurrentYear) {
        HashMap<Integer, HashMap<Integer, Integer>> inputSort = sortByKeyBigForYear(input);
        HashMap<Integer, Integer> result = new HashMap<>();
        inputSort.forEach((year, innerMap) -> {
            if (year == CurrentYear) {
                innerMap.forEach((month, value) -> {
                    result.put(month, value);
                });
            }
        });
        return result;
    }


    public static HashMap<Integer, HashMap<Integer, Integer>> sortByKeyBigForYear(HashMap<Integer, HashMap<Integer, Integer>> unsortedMap) {
        // Convert the HashMap to a TreeMap, which automatically sorts by keys
        TreeMap<Integer, HashMap<Integer, Integer>> sortedMap = new TreeMap<>(unsortedMap);

        // Create a new LinkedHashMap to store the sorted elements (if LinkedHashMap is needed)
        HashMap<Integer, HashMap<Integer, Integer>> result = new LinkedHashMap<>(sortedMap);

        return result;
    }
}
