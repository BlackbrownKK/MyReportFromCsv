package com.example.myreportfromcsv;

import com.example.myreportfromcsv.view.JTableView;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import javax.swing.*;

public class WindowApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JTableView());

//        Logger logger = LogManager.getLogger(WindowApp.class);
//        logger.debug("Debug message");
//        logger.info("Info message");
//        logger.warn("Warning message");
//        logger.error("Error message");
//        logger.debug("Parsing character {} of input {}");

    }
}
