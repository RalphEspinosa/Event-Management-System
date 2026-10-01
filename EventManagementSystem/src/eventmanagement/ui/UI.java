/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventmanagement.ui;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import eventmanagement.model.Event;
import eventmanagement.model.Attendee;

/**
 *
 * @author reirii
 */
public class UI extends JFrame implements ActionListener {
    private LinkedList<Event> linkedList = new LinkedList<>();
    private LinkedList<Event> displayedEvents = new LinkedList<>();
    private DefaultListModel<Attendee> listModel = new DefaultListModel<>();
    private JList<Attendee> list;
    private JComboBox<Event> attendeeEvent;
    private JTextField txtAttendeeName, txtEmail;
    private JButton btnAddAttendee, btnRemoveAttendee;
    private int nextEventId = 1, nextAttendeeId = 1;
    
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
        attendeeEvent = new JComboBox<>();
        attendeeEvent.setBounds(10, 10, 650, 30);
        attendeePanel.add(attendeeEvent);
        list = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(10, 50, 650, 350);
        attendeePanel.add(scrollPane);
        JLabel lblName = new JLabel("Attendee Name:");
        lblName.setBounds(10, 410, 200, 25);
        attendeePanel.add(lblName);
        txtAttendeeName = new JTextField();
        txtAttendeeName.setBounds(10, 440, 250, 30);
        attendeePanel.add(txtAttendeeName);
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(270, 410, 250, 25);
        attendeePanel.add(lblEmail);
        txtEmail = new JTextField();
        txtEmail.setBounds(270, 440, 250, 30);
        attendeePanel.add(txtEmail);
        btnAddAttendee = new JButton("Add");
        btnAddAttendee.setBounds(530, 440, 130, 30);
        attendeePanel.add(btnAddAttendee);
        btnRemoveAttendee = new JButton("Remove");
        btnRemoveAttendee.setBounds(530, 480, 130, 30);
        attendeePanel.add(btnRemoveAttendee);
        btnAdd.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnSearch.addActionListener(this);
        btnShowAll.addActionListener(this);
        btnAddAttendee.addActionListener(this);
        btnRemoveAttendee.addActionListener(this);
        attendeeEvent.addActionListener(this);
        eventTable.setDefaultEditor(Object.class, null);
        eventTable.getSelectionModel().addListSelectionListener(e -> {
            int index = eventTable.getSelectedRow();
            if(!e.getValueIsAdjusting() && index != -1){
                Event event = displayedEvents.get(index);
                txtEventName.setText(event.getName());
                txtDate.setText(event.getDate());
                txtCategory.setText(event.getCategory());
            }
        });
        
        
        
     }

    
    private void showEvents(){
        displayedEvents.clear();
        String search = txtSearch.getText().trim();
        for(Event event : linkedList){
            String value = searchType.getSelectedIndex() == 0 ? event.getDate() : event.getCategory();
            if(search.isEmpty() || value.equalsIgnoreCase(search)){
                displayedEvents.add(event);
            }
        }
        model.setRowCount(0);
        for(Event event : displayedEvents){
            model.addRow(new Object[]{event.getEventId(), event.getName(), event.getDate(),
                event.getCategory(), event.getAttendees().size()});
        }
    }

    private void showAttendees(){
        listModel.clear();
        Event event = (Event) attendeeEvent.getSelectedItem();
        if(event != null){
            for(Attendee attendee : event.getAttendees()){
                listModel.addElement(attendee);
            }
        }
    }

    private void inputError(String message){
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == btnAdd || e.getSource() == btnUpdate){
            String name = txtEventName.getText().trim();
            String date = txtDate.getText().trim();
            String category = txtCategory.getText().trim();
            if(name.isEmpty() || date.isEmpty() || category.isEmpty()){
                inputError("Please enter event name, date and category first");
                return;
            }
            Event event;
            if(e.getSource() == btnAdd){
                event = new Event(nextEventId++, name, date);
                event.setCategory(category);
                linkedList.add(event);
                attendeeEvent.addItem(event);
            }else{
                int indexSelected = eventTable.getSelectedRow();
                if(indexSelected == -1){
                    inputError("Please select event first");
                    return;
                }
                event = displayedEvents.get(indexSelected);
                event.setName(name);
                event.setDate(date);
                event.setCategory(category);
            }
            txtEventName.setText("");
            txtDate.setText("");
            txtCategory.setText("");
            showEvents();
            attendeeEvent.repaint();
        }else if(e.getSource() == btnDelete){
            int indexSelected = eventTable.getSelectedRow();
            if(indexSelected != -1){
                Event event = displayedEvents.get(indexSelected);
                linkedList.remove(event);
                attendeeEvent.removeItem(event);
                showEvents();
            }else{
                inputError("Please select event first");
            }
        }else if(e.getSource() == btnSearch){
            if(txtSearch.getText().trim().isEmpty()){
                inputError("Please enter date or category first");
            }else{
                showEvents();
            }
        }else if(e.getSource() == btnShowAll){
            txtSearch.setText("");
            showEvents();
        }else if(e.getSource() == attendeeEvent){
            showAttendees();
        }else if(e.getSource() == btnAddAttendee){
            Event event = (Event) attendeeEvent.getSelectedItem();
            String name = txtAttendeeName.getText().trim();
            String email = txtEmail.getText().trim();
            if(event == null){
                inputError("Please add and select event first");
            }else if(name.isEmpty() || email.isEmpty()){
                inputError("Please enter attendee name and email first");
            }else{
                Attendee attendee = new Attendee(nextAttendeeId++, name, email, event.getEventId());
                event.getAttendees().add(attendee);
                listModel.addElement(attendee);
                txtAttendeeName.setText("");
                txtEmail.setText("");
                showEvents();
                attendeeEvent.repaint();
            }
        }else if(e.getSource() == btnRemoveAttendee){
            Event event = (Event) attendeeEvent.getSelectedItem();
            int indexSelected = list.getSelectedIndex();
            if(event != null && indexSelected != -1){
                event.getAttendees().remove(indexSelected);
                listModel.removeElementAt(indexSelected);
                showEvents();
                attendeeEvent.repaint();
            }else{
                inputError("Please select attendee first");
            }
        }
    }
}
