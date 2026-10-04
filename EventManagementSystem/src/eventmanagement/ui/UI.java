package eventmanagement.ui;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import eventmanagement.ds.Search;
import java.util.PriorityQueue;
import eventmanagement.ds.WaitingEntry;
import eventmanagement.model.Event;
import eventmanagement.model.Attendee;


public class UI extends JFrame /*implements ActionListener*/ {
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            UI sample = new UI();
            sample.setVisible(true);
        });
    }

     // Declaring Components
     private DefaultTableModel model;
     private JTabbedPane tabbedPane;
     private JPanel eventPanel, attendeePanel, reportPanel, waitingPanel;
     private JLabel lblEventName, lblCategory, lblDate, lblSearch;
     private JTextField txtEventName, txtDate, txtCategory, txtSearch, txtWaitingName, txtWaitingEmail, txtAttendeeName, txtEmail;
     private JButton btnAdd, btnUpdate, btnDelete, btnSearch, btnShowAll, btnJoinWaiting, btnAdmitNext, btnRemoveWaiting, btnAddAttendee, btnRemoveAttendee;
     private JTable eventTable;
     private JScrollPane scroll;
     private JComboBox<String> searchType;
     private JSpinner waitingPriority;
     private JComboBox<Event> waitingEvent;
     private JComboBox<Event> attendeeEvent;
     private JList<WaitingEntry> waitingList;
     private JList<Attendee> list;
     private DefaultListModel<WaitingEntry> waitingModel;
     private LinkedList<Event> linkedList = new LinkedList<>();
     private LinkedList<Event> displayedEvents = new LinkedList<>();
     private DefaultListModel<Attendee> listModel = new DefaultListModel<>();
     private int nextEventId = 1, nextAttendeeId = 1;
     private long nextWaitingOrder = 1;

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

        waitingPanel = new JPanel();
        waitingPanel.setLayout(null);
        tabbedPane.addTab("Queue List", waitingPanel);

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

        JLabel lblWaitingName = new JLabel("Attendee Name:");
        lblWaitingName.setBounds(10, 410, 200, 25);
        waitingPanel.add(lblWaitingName);

        JLabel lblWaitingEmail = new JLabel("Email:");
        lblWaitingEmail.setBounds(270, 410, 200, 25);
        waitingPanel.add(lblWaitingEmail);

        JLabel lblPriority = new JLabel("Priority (1 = highest):");
        lblPriority.setBounds(530, 410, 200, 25);
        waitingPanel.add(lblPriority);

        JLabel lblName = new JLabel("Attendee Name:");
        lblName.setBounds(10, 410, 200, 25);
        attendeePanel.add(lblName);


        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(270, 410, 250, 25);
        attendeePanel.add(lblEmail);

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

        txtWaitingName = new JTextField();
        txtWaitingName.setBounds(10, 440, 250, 30);
        waitingPanel.add(txtWaitingName);

        txtWaitingEmail = new JTextField();
        txtWaitingEmail.setBounds(270, 440, 250, 30);
        waitingPanel.add(txtWaitingEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(270, 440, 250, 30);
        attendeePanel.add(txtEmail);

        txtAttendeeName = new JTextField();
        txtAttendeeName.setBounds(10, 440, 250, 30);
        attendeePanel.add(txtAttendeeName);
        btnAdd = new JButton("Add Event");
        btnAdd.setBounds(620, 70, 105, 32);
        eventPanel.add(btnAdd);

                // Buttons
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

        btnJoinWaiting = new JButton("Join Queue List");
        btnJoinWaiting.setBounds(650, 440, 180, 30);
        waitingPanel.add(btnJoinWaiting);


        btnAdmitNext = new JButton("Admit Next");
        btnAdmitNext.setBounds(10, 480, 150, 30);
        waitingPanel.add(btnAdmitNext);


        btnRemoveWaiting = new JButton("Remove Selected");
        btnRemoveWaiting.setBounds(170, 480, 180, 30);
        waitingPanel.add(btnRemoveWaiting);

        btnAddAttendee = new JButton("Add");
        btnAddAttendee.setBounds(530, 440, 130, 30);
        attendeePanel.add(btnAddAttendee);

        btnRemoveAttendee = new JButton("Remove");
        btnRemoveAttendee.setBounds(530, 480, 130, 30);
        attendeePanel.add(btnRemoveAttendee);

                // Table & Scroll Pane
        model = new DefaultTableModel(data, columns);
        eventTable = new JTable(model);
        scroll = new JScrollPane(eventTable);
        scroll.setBounds(5, 120, 970, 400);
        eventPanel.add(scroll);

        list = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(10, 50, 650, 350);
        attendeePanel.add(scrollPane);

        waitingModel = new DefaultListModel<>();
        waitingList = new JList<>(waitingModel);
        JScrollPane waitingScroll = new JScrollPane(waitingList);
        waitingScroll.setBounds(10, 50, 850, 350);
        waitingPanel.add(waitingScroll);
                // Search Bar
        searchType = new JComboBox<>();
        searchType.addItem("Search by Date");
        searchType.addItem("Search by Category");
        searchType.setBounds(10, 535, 370, 30);
        eventPanel.add(searchType);

        waitingEvent = new JComboBox<>();
        waitingEvent.setBounds(10, 10, 850, 30);
        waitingPanel.add(waitingEvent);

        attendeeEvent = new JComboBox<>();
        attendeeEvent.setBounds(10, 10, 650, 30);
        attendeePanel.add(attendeeEvent);

        waitingPriority = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        waitingPriority.setBounds(530, 440, 100, 30);
        waitingPanel.add(waitingPriority);
        /*waitingEvent.addActionListener(this);
        btnJoinWaiting.addActionListener(this);
        btnAdmitNext.addActionListener(this);
        btnRemoveWaiting.addActionListener(this);
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
        eventTable.clearSelection();
        displayedEvents.clear();
        String search = txtSearch.getText().trim();
        boolean byDate = searchType.getSelectedIndex() == 0;
        if(search.isEmpty()){
            displayedEvents.addAll(linkedList);
        }else if(byDate){
            displayedEvents.addAll(Search.binarySearch(linkedList, search, byDate));
        }else{
            displayedEvents.addAll(Search.linearSearch(linkedList, search, byDate));
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
                waitingEvent.addItem(event);
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
            waitingEvent.repaint();
        }else if(e.getSource() == btnDelete){
            int indexSelected = eventTable.getSelectedRow();
            if(indexSelected != -1){
                Event event = displayedEvents.get(indexSelected);
                linkedList.remove(event);
                attendeeEvent.removeItem(event);
                waitingEvent.removeItem(event);
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
        }else if(e.getSource() == waitingEvent){
            showWaitingList();
        }else if(e.getSource() == btnJoinWaiting){
            Event event = (Event) waitingEvent.getSelectedItem();
            String name = txtWaitingName.getText().trim();
            String email = txtWaitingEmail.getText().trim();
            if(event == null){
                inputError("Please add and select event first");
            }else if(name.isEmpty() || email.isEmpty()){
                inputError("Please enter attendee name and email first");
            }else{
                try{
                    waitingPriority.commitEdit();
                }catch(java.text.ParseException ex){
                    inputError("Please enter a priority from 1 to 5");
                    return;
                }
                Attendee attendee = new Attendee(nextAttendeeId++, name, email, event.getEventId());
                event.getWaitingList().offer(new WaitingEntry(attendee,
                    (Integer) waitingPriority.getValue(), nextWaitingOrder++));
                txtWaitingName.setText("");
                txtWaitingEmail.setText("");
                showWaitingList();
            }
        }else if(e.getSource() == btnAdmitNext){
            Event event = (Event) waitingEvent.getSelectedItem();
            if(event == null || event.getWaitingList().isEmpty()){
                inputError("The selected event has no waiting attendees");
            }else{
                event.addAttendee(event.getWaitingList().poll().getAttendee());
                showWaitingList();
                showAttendees();
                showEvents();
                attendeeEvent.repaint();
                waitingEvent.repaint();
            }
        }else if(e.getSource() == btnRemoveWaiting){
            Event event = (Event) waitingEvent.getSelectedItem();
            WaitingEntry entry = waitingList.getSelectedValue();
            if(event == null || entry == null){
                inputError("Please select a waiting attendee first");
            }else{
                event.getWaitingList().remove(entry);
                showWaitingList();
            }
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
        }*/
    }
}
