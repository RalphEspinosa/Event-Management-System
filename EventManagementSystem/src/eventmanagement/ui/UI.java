/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventmanagement.ui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author reirii
 */
public class UI extends JFrame implements ActionListener {

    // Declaring Components
     private DefaultTableModel model;
     private JTabbedPane tabbedPane;
     private JPanel eventPanel, attendeePanel, reportPanel;
     private JLabel lblEventName, lblCategory, lblDate, lblSearch;
     private JTextField txtEventName, txtDate, txtCategory, txtSearch;
     private JButton btnAdd, btnUpdate, btnDelete, btnSearch, btnShowAll;
     private JTable eventTable;
     private JScrollPane scroll;
     private JComboBox<String> searchType;

     // Declaring Table Columns
      Object[][] data = {};
      String[] columns = {"ID","Event Name","Date","Category","Attendees"};


     UI(){

        setTitle("Event Management System");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);


        //Tabs & Panels
        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(0,0,1000,650);

        eventPanel = new JPanel();
        eventPanel.setLayout(null);
        tabbedPane.addTab("Events", eventPanel);

        attendeePanel = new JPanel();
        attendeePanel.setLayout(null);
        tabbedPane.addTab("Attendees", attendeePanel);

        reportPanel = new JPanel();
        reportPanel.setLayout(null);
        tabbedPane.addTab("Reports", reportPanel);
        add(tabbedPane);


                 // Event Panel

        // Labels
        lblEventName = new JLabel("Event Name:");
        lblEventName.setBounds(5, 40, 150, 30);
        eventPanel.add(lblEventName);

        lblDate = new JLabel("Date:");
        lblDate.setBounds(210, 40, 100, 30);
        eventPanel.add(lblDate);

        lblCategory = new JLabel("Category:");
        lblCategory.setBounds(415, 40, 100, 30);
        eventPanel.add(lblCategory);

        // TextFields
        txtEventName = new JTextField();
        txtEventName.setBounds(5, 70, 195, 32);
        eventPanel.add(txtEventName);

        txtDate = new JTextField();
        txtDate.setBounds(210, 70, 195, 32);
        eventPanel.add(txtDate);

        txtCategory = new JTextField();
        txtCategory.setBounds(415, 70, 195, 32);
        eventPanel.add(txtCategory);

        txtSearch = new JTextField();
        txtSearch.setBounds(390, 535, 190, 30);
        eventPanel.add(txtSearch);

        // Buttons
        btnAdd = new JButton("Add Event");
        btnAdd.setBounds(620, 70, 105, 32);
        eventPanel.add(btnAdd);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(740, 70, 105, 32);
        eventPanel.add(btnUpdate);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(860, 70, 105, 32);
        eventPanel.add(btnDelete);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(587, 535, 93, 30);
        eventPanel.add(btnSearch);

        btnShowAll = new JButton("Show all");
        btnShowAll.setBounds(687, 535, 93, 30);
        eventPanel.add(btnShowAll);

        // Table & Scroll Pane
        model = new DefaultTableModel(data, columns);
        eventTable = new JTable(model);
        scroll = new JScrollPane(eventTable);
        scroll.setBounds(5, 120, 970, 400);
        eventPanel.add(scroll);

        // Search Bar
        searchType = new JComboBox<>();
        searchType.addItem("Search by Date");
        searchType.addItem("Search by Category");
        searchType.setBounds(210, 535, 170, 30);
        eventPanel.add(searchType);



     }


    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
