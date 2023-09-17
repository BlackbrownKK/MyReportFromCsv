package com.example.myreportfromcsv.view;

import com.example.myreportfromcsv.controller.GetReportController;
import com.example.myreportfromcsv.model.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.management.GarbageCollectorMXBean;
import java.util.ArrayList;

public class JTableView {

    // Initialize column headings.
    private String[] colHeads = {"Id", "пріоритет"};
    private String[][] data;
    private String reportInProgress;

    public void initialTask() {
        GetReportController getReportController = new GetReportController();
        getReportController.initialise();
        ArrayList<Order> inputDataInProgress = getReportController.getDataFromReportInProgress();
        data = makeTableToReport(inputDataInProgress);
        reportInProgress = getReportController.reportInProgress;
    }

    public String[][] makeTableToReport(ArrayList<Order> inputDataInProgress) {
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
        frm.setSize(300, 300);

        // Create the table.
        JTable table = new JTable(data, colHeads);

        // Add the table to a scroll pane.
        JScrollPane jsp = new JScrollPane(table);

        Button button = new Button("Copy to clipboard");
        // Set up the JFrame. Use default BorderLayout.

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection selection = new StringSelection(reportInProgress);
                clipboard.setContents(selection, null);
                JOptionPane.showMessageDialog(frm, reportInProgress);
            }
        });



        frm.add(jsp, BorderLayout.CENTER); // Add the JScrollPane to the center
        frm.add(button, BorderLayout.SOUTH); // Add the Button to the south

        // Display the frame.
        frm.setVisible(true);
    }

    public static void main(String[] args) {
        // Create the frame on the event dispatching thread.

        SwingUtilities.invokeLater(() -> new JTableView());
    }

}
