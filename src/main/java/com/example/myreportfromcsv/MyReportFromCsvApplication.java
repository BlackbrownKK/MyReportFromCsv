package com.example.myreportfromcsv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//todo export to postgresSQL to make a reserve copy

//todo check every data if it was changed and make changes in the DB


@SpringBootApplication
public class MyReportFromCsvApplication {



    public static void main(String[] args) {
        SpringApplication.run(MyReportFromCsvApplication.class, args);
        System.out.println();
    }
}