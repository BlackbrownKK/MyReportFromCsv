package com.example.myreportfromcsv;

import com.example.myreportfromcsv.controller.GetReportController;
import com.example.myreportfromcsv.controller.MakeModelController;
import com.example.myreportfromcsv.view.JTableView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.util.Arrays;

import static java.util.Arrays.*;

@SpringBootApplication
public class MyReportFromCsvApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyReportFromCsvApplication.class, args);

	}

}
