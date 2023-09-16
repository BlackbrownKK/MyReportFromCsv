package com.example.myreportfromcsv;

import com.example.myreportfromcsv.controller.MakeModelController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

import static java.util.Arrays.*;

@SpringBootApplication
public class MyReportFromCsvApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyReportFromCsvApplication.class, args);
		MakeModelController controller = new MakeModelController();
		controller.makeModels();

	}

}
