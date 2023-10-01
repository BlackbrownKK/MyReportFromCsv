package com.example.myreportfromcsv.service;

import com.example.myreportfromcsv.controller.ModelController;
import com.example.myreportfromcsv.model.Order;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class ReportService {
    LocalDate localDateToday;

    private final ModelController controller = new ModelController();
    @Getter
    private final ArrayList<Order> orders = controller.makeModels();
    @Getter
    private List<Order> dataReportInProgress;
    @Getter
    private List<Order> dataFromReportToTheAct;
    @Getter
    private List<Order> dataFromPaymentReadyLastWeek;
    @Getter
    private HashMap<Integer, List<Order>> yearsDividedTable;
    @Getter
    private HashMap<Integer, HashMap<Integer, Integer>> tableByMonths;
    @Getter
    private HashMap<Integer, HashMap<Integer, Integer>> profitByMonths;

    @Getter
    private HashMap<Integer, Integer> intensiveReport;

    private HashSet<Integer> yearsSet;

    public static int daysBeforeTodayForPayment = 4;


    public void initialise() {
        localDateToday = LocalDate.now();
        yearsSet = setYearsSet();
        dataReportInProgress = getReportInProgress();
        dataFromReportToTheAct = getReportToTheAct();
        dataFromPaymentReadyLastWeek = forPaymentReadyLastWeek();
        yearsDividedTable = divideTableByYear();
        tableByMonths = analysisTableByMonths();
        profitByMonths = analysisProfitByMonths();
        intensiveReport = intensiveReportPerOneWeek();
    }

    private HashSet<Integer> setYearsSet() {
        HashSet<Integer> years = new HashSet<>();
        for (Order order : orders) {
            if (!order.isInTheProgress())
                years.add(order.getDataReady().getYear());
        }
        return years;
    }

    private List<Order> getReportInProgress() {
        List<Order> result = orders
                .stream()
                .filter(Order::isInTheProgress)
                .collect(Collectors.toList());
        Comparator<Order> priorityComparator = Comparator.comparing(Order::getPriority);
        result.sort(priorityComparator);
        return result;
    }

    private List<Order> forPaymentReadyLastWeek() {
        List<Order> result = orders
                .stream()
                .filter(Order::isToTheAkt)
                .filter(order -> checkWeekDate(order.getDataReady()))
                .collect(Collectors.toList());
        Comparator<Order> priorityComparator = Comparator.comparing(Order::getDataReady);
        result.sort(priorityComparator);
        return result;
    }

    private boolean checkWeekDate(LocalDate dataReady) {
        int daysDifference = (int) (localDateToday.toEpochDay() - dataReady.toEpochDay());
        return daysDifference >= daysBeforeTodayForPayment;
    }

    private List<Order> getReportToTheAct() {
        List<Order> result = orders
                .stream()
                .filter(Order::isToTheAkt)
                .collect(Collectors.toList());
        Comparator<Order> priorityComparator = Comparator.comparing(Order::getDataReady);
        result.sort(priorityComparator);
        return result;
    }

    private HashMap<Integer, List<Order>> divideTableByYear() {
        HashMap<Integer, List<Order>> reportAllYears = new HashMap<>();
        for (int year : yearsSet) {
            List<Order> sortByYearCollection =
                    orders.stream()
                            .filter(order -> !order.isInTheProgress())
                            .filter(order -> order.getDataReady().getYear() == year)
                            .collect(Collectors.toList());
            reportAllYears.put(year, sortByYearCollection);
        }
        return reportAllYears;
    }

    private HashMap<Integer, Integer> intensiveReportPerOneWeek() {
        HashMap<Integer, Integer> reportAllYears = new HashMap<>();
        for (Month month : Month.values()) {
            Long count = orders.stream()
                    .filter(order -> !order.isInTheProgress())
                    .filter(order -> order.getDataReady().getYear() == localDateToday.getYear())
                    .filter(order -> order.getDataReady().getMonth() == month)
                    .count();

            reportAllYears.put(month.getValue(), Math.toIntExact(count));
        }
        return reportAllYears;
    }


    private HashMap<Integer, HashMap<Integer, Integer>> analysisTableByMonths() {

        HashMap<Integer, HashMap<Integer, Integer>> result = new HashMap<>();

        List<Integer> keys = yearsDividedTable.keySet().stream().toList();

        for (Integer key : keys) {
            HashMap<Integer, Integer> temp = new HashMap<>();
            List<Order> orders = yearsDividedTable.get(key);
            for (Month month : Month.values()) {
                int sum = orders
                        .stream()
                        .filter(order -> order.getDataReady().getMonth() == month)
                        .mapToInt(Order::getPrice)
                        .sum();

                temp.put(month.getValue(), sum);
            }
            result.put(key, temp);
        }
        return result;
    }

    private HashMap<Integer, HashMap<Integer, Integer>> analysisProfitByMonths() {
        HashMap<Integer, HashMap<Integer, Integer>> result = new HashMap<>();
        List<Integer> keys = yearsDividedTable.keySet().stream().toList();
        for (Integer key : keys) {
            HashMap<Integer, Integer> temp = new HashMap<>();
            List<Order> orders = yearsDividedTable.get(key);
            for (Month month : Month.values()) {
                int sum = orders
                        .stream()
                        .filter(order -> order.getDataReady().getMonth() == month)
                        .mapToInt(Order::getProfit)
                        .sum();

                temp.put(month.getValue(), sum);
            }
            result.put(key, temp);
        }
        return result;
    }


    public int getSumOfYear(HashMap<Integer, Integer> input) {
        return input
                .values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
