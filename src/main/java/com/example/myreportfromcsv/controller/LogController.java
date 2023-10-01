package com.example.myreportfromcsv.controller;

import ch.qos.logback.classic.spi.LoggerContextListener;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@RestController
//@RequestMapping("/Log")
public class LogController {
    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @GetMapping
    public String index() {
        logger.trace("trace massage");
        logger.debug("debug massage");
        logger.info("info massage");
        logger.warn("warn massage");
        logger.error("error massage");
        return "see to the logs";
    }
}
