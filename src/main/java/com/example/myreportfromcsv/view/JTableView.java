package com.example.myreportfromcsv.view;

import com.example.myreportfromcsv.controller.GetReportController;
import com.example.myreportfromcsv.controller.MakeOutputController;
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


    public void initialTask() {
        MakeOutputController outputReports = new MakeOutputController();
        GetReportController outputData= new GetReportController();
        outputReports.initialise();
        outputData.initialise();

        List<Order> inputDataInProgress = outputData.getDataReportInProgress();
        data = makeTableToReport(inputDataInProgress);
        reportInProgress = outputReports.getReportInProgress();
        listGetPayment = outputReports.getReportReportReadyLastWeek();
        reportToTheAkt = outputReports.getReportToTheAct();
        yearReportText = outputReports.getReportMain();
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
        frm.setSize(500, 300);

        // Create the table.
        JTable table = new JTable(data, colHeads);

        // Add the table to a scroll pane.
        JScrollPane jsp = new JScrollPane(table);

        Button getReportInProgress = new Button("Copy to clipboard");

        Button getReportToTheAkt = new Button("List with ready reports");
        Button forPayment = new Button("get Payment");
        Button yearReport = new Button("get year report");

        JTextField daysOfReady = new JTextField(15);


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

        // todo interface and reports
        frm.add(jsp, BorderLayout.CENTER); // Add the JScrollPane to the center
        frm.add(getReportInProgress, BorderLayout.SOUTH); // Add the Button to the south
        frm.add(getReportToTheAkt, BorderLayout.EAST); // Add the Button to the south
        frm.add(forPayment, BorderLayout.WEST); // Add the Button to the south
        frm.add(yearReport, BorderLayout.NORTH); // Add the Button to the south

        // Display the frame.
        frm.setVisible(true);
    }

    public static void main(String[] args) {
        // Create the frame on the event dispatching thread.

        SwingUtilities.invokeLater(() -> new JTableView());
    }

}
