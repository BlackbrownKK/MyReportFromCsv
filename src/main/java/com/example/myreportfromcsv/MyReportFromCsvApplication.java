package com.example.myreportfromcsv;

import com.example.myreportfromcsv.controller.GetReportController;
import com.example.myreportfromcsv.controller.MakeModelController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

import static java.util.Arrays.*;

@SpringBootApplication
public class MyReportFromCsvApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyReportFromCsvApplication.class, args);
		GetReportController getReportController = new GetReportController();
		System.out.println(getReportController.getReportInProgress());
		System.out.println(getReportController.getReportToTheAct());
	}

}
