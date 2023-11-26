package com.example.myreportfromcsv.view;

import com.example.myreportfromcsv.service.ReportService;
import com.example.myreportfromcsv.controller.ReportController;
import com.example.myreportfromcsv.model.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class JTableView {

    // Initialize column headings.
    private String[] colHeads = {"Id in progress", "пріоритет"};
    private String[][] data;
    private String reportInProgress;
    private String reportToTheAkt;
    private String listGetPayment;
    private String yearReportText;
    private String yearReportProfitText;
    private String intensiveReport;
    JLabel jlabText = new JLabel();

    ReportController outputReports = new ReportController();
    ReportService outputData = new ReportService();

    public void initialTask() {
        outputReports.initialise();
        outputData.initialise();
        List<Order> inputDataInProgress = outputData.getDataReportInProgress();
        data = makeTableToReport(inputDataInProgress);
        reportInProgress = outputReports.getReportInProgress();
        listGetPayment = outputReports.getReportReportReadyLastWeek();
        reportToTheAkt = outputReports.getReportToTheAct();
        yearReportText = outputReports.getReportMain();
        yearReportProfitText = outputReports.getReportProfitMain();
        intensiveReport = outputReports.getIntensiveReport();
    }

    public String[][] makeTableToReport(List<Order> inputDataInProgress) {
        String[][] temp = new String[inputDataInProgress.size()][2];
        int index = 0;
        for (Order order : inputDataInProgress) {
            temp[index][0] = order.getCode();
            temp[index][1] = String.valueOf(order.getPriority());
            index++;
        }
        return temp;
    }

    public JTableView() {
        initialTask();

        JFrame frm = new JFrame("отчёт");
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setSize(500, 200);

        JTable table = new JTable(data, colHeads);
        JButton jButtonAdd = new JButton("add all to DB");
        JMenuBar menuBar = new JMenuBar();

        Button getReportInProgress = new Button("Copy to clipboard");
        JMenuItem getReportToTheAkt = new JMenuItem("list ready");
        JMenuItem forPayment = new JMenuItem("to payment");
        JMenuItem yearReport = new JMenuItem("year report");
        JMenuItem yearProfitReport = new JMenuItem("profit report");
        JMenuItem intensive = new JMenuItem("intensive");

        menuBar.add(getReportToTheAkt);
        menuBar.add(forPayment);
        menuBar.add(yearReport);
        menuBar.add(yearProfitReport);
        menuBar.add(intensive);

        JTextField daysOfReady = new JTextField(15);
        daysOfReady.setText("enter days after ready");

        frm.setLayout(new FlowLayout());
        frm.add(table);
        frm.add(jButtonAdd);
        frm.add(menuBar);
        frm.setJMenuBar(menuBar);
        frm.add(getReportInProgress);
        frm.add(daysOfReady);
        frm.setVisible(true);

        jButtonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Graphic graphic = new Graphic();
                graphic.initialTask();
            }
        });


        daysOfReady.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jlabText.setText(daysOfReady.getText());
                    int days = (Integer.parseInt(jlabText.getText()));
                    ReportService.daysBeforeTodayForPayment = days;
                    initialTask();
                } catch (NumberFormatException exception) {
                    System.out.println("enter days after ready [int] ");
                }

            }
        });

        intensive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection selection = new StringSelection(intensiveReport);
                clipboard.setContents(selection, null);
                JOptionPane.showMessageDialog(frm, intensiveReport);
            }
        });

        yearProfitReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection selection = new StringSelection(yearReportProfitText);
                clipboard.setContents(selection, null);
                JOptionPane.showMessageDialog(frm, yearReportProfitText);
            }
        });
        yearReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection selection = new StringSelection(yearReportText);
                clipboard.setContents(selection, null);
                JOptionPane.showMessageDialog(frm, yearReportText);
            }
        });
        forPayment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection selection = new StringSelection(listGetPayment);
                clipboard.setContents(selection, null);
                JOptionPane.showMessageDialog(frm, listGetPayment);
            }
        });

        getReportInProgress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection selection = new StringSelection(reportInProgress);
                clipboard.setContents(selection, null);
                JOptionPane.showMessageDialog(frm, reportInProgress);
            }
        });

        getReportToTheAkt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection selection = new StringSelection(reportToTheAkt);
                clipboard.setContents(selection, null);
                JOptionPane.showMessageDialog(frm, reportToTheAkt);
            }
        });

    }
}
