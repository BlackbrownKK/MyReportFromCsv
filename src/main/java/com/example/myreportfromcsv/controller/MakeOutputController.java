package com.example.myreportfromcsv.controller;

import com.example.myreportfromcsv.model.Order;
import lombok.Getter;
import java.util.*;


public class MakeOutputController {
    GetReportController controller;
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

    private final String textIsInTheProgress = "  в роботі: ";
    private final String textToTheAct = "  готово: ";
    private final String textForPayment = "  в Акт: ";
    private final String textPriority = "пріоритет <";
    private final String textIntensive = "інтенсивність";

    public void initialise() {
        controller = new GetReportController();
        controller.initialise();
        reportInProgress = getReportInProgress(controller.getDataReportInProgress());
        reportReportReadyLastWeek = getReportReadyLastWeek(controller.getDataFromPaymentReadyLastWeek());
        reportToTheAct = getReportToTheAllAct(controller.getDataFromReportToTheAct());
        reportMain =  getReportFromAllTime(controller.getTableByMonths());
        reportProfitMain = getReportProfitAllTime(controller.getProfitByMonths());
        intensiveReport = getIntensiveReportToResult(controller.getIntensiveReport());
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
            int sumOfYear = controller.getSumOfYear(innerMap);
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
            int sumOfYear = controller.getSumOfYear(innerMap);
            stringBuilder.append(year).append("-").append(sumOfYear).append(" : ");
            innerMap.forEach((month, value) -> {
                stringBuilder.append(month).append("-").append(value).append("; ");
            });
            stringBuilder.append("\n");
        });
        return stringBuilder.toString();
    }

    public static HashMap<Integer, HashMap<Integer, Integer>> sortByKeyBigForYear(HashMap<Integer, HashMap<Integer, Integer>> unsortedMap) {
        // Convert the HashMap to a TreeMap, which automatically sorts by keys
        TreeMap<Integer, HashMap<Integer, Integer>> sortedMap = new TreeMap<>(unsortedMap);

        // Create a new LinkedHashMap to store the sorted elements (if LinkedHashMap is needed)
        HashMap<Integer, HashMap<Integer, Integer>> result = new LinkedHashMap<>(sortedMap);

        return result;
    }
}
