package com.example.myreportfromcsv.view;

import com.example.myreportfromcsv.controller.ReportController;
import com.example.myreportfromcsv.service.ReportService;

import java.util.HashMap;

public class Graphic {


    private HashMap<Integer, Integer> profitDataAllTime;
    ReportController reportController = new ReportController();
    public void initialTask() {
        reportController.initialise();
        profitDataAllTime = reportController.getProfitDataAllTime();
        analyseData();
    }

    public void analyseData( ) {
        profitDataAllTime.entrySet().stream().forEach(System.out::println);
    }

}
