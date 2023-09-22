package com.example.myreportfromcsv.controller;

import com.example.myreportfromcsv.model.Order;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class GetReportController {
    LocalDate localDateToday;
    private final MakeModelController controller = new MakeModelController();
    private final ArrayList<Order> input = controller.makeModels();
    @Getter
    private List<Order> dataReportInProgress;
    @Getter
    private List<Order> dataFromReportToTheAct;
    @Getter
    private List<Order> dataFromPaymentReadyLastWeek;
    @Getter
    private HashMap<Integer, List<Order>> yearsDividedTable;
    @Getter
    private HashMap<Integer, HashMap<Integer, Integer>> TableByMonths;
    private HashSet<Integer> yearsSet;

    public String yearReport;

    public void initialise() {
        localDateToday = LocalDate.now();
        yearsSet = setYearsSet();
        dataReportInProgress = getReportInProgress();
        dataFromReportToTheAct = getReportToTheAct();
        dataFromPaymentReadyLastWeek = forPaymentReadyLastWeek();
        yearsDividedTable = divideTableByYear();
        TableByMonths = analysisTableByMonths();
    }

    private HashSet<Integer> setYearsSet() {
        HashSet<Integer> years = new HashSet<>();
        for (Order order : input) {
            if (!order.isInTheProgress())
                years.add(order.getDataReady().getYear());
        }
        return years;
    }

    private List<Order> getReportInProgress() {
        List<Order> result = input
                .stream()
                .filter(Order::isInTheProgress)
                .collect(Collectors.toList());
        Comparator<Order> priorityComparator = Comparator.comparing(Order::getPriority);
        result.sort(priorityComparator);
        return result;
    }

    private List<Order> forPaymentReadyLastWeek() {
        List<Order> result = input
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
        return daysDifference > 4;
    }

    private List<Order> getReportToTheAct() {
        List<Order> result = input
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
                    input.stream()
                            .filter(order -> !order.isInTheProgress())
                            .filter(order -> order.getDataReady().getYear() == year)
                            .collect(Collectors.toList());
            reportAllYears.put(year, sortByYearCollection);
        }
        return reportAllYears;
    }

    private HashMap<Integer, HashMap<Integer, Integer>> analysisTableByMonths() {
        BinaryOperator<Integer> getSum = Integer::sum;

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


    public int getSumOfYear(HashMap<Integer, Integer> input) {
            return input
                    .values()
                    .stream()
                    .mapToInt(Integer::intValue)
                    .sum();
        }
}
