package eventmanagement.ui;

import eventmanagement.dao.AttendeeDao;
import eventmanagement.dao.EventDao;
import eventmanagement.ds.Search;
import eventmanagement.ds.WaitingEntry;
import eventmanagement.model.Attendee;
import eventmanagement.model.Event;
import eventmanagement.service.RegistrationService;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


public class UI extends JFrame implements ActionListener, ChangeListener, ListSelectionListener {
   
     //Backend objects (one of each, shared by all tabs)
     private EventDao eventDao = new EventDao();
     private AttendeeDao attendeeDao = new AttendeeDao();
     private RegistrationService registrationService = new RegistrationService();

     //Declaring Components
     private DefaultTableModel model;
     private JTabbedPane tabbedPane;
     private JPanel eventPanel, attendeePanel, reportPanel, waitingPanel;
     private JLabel lblEventName, lblCategory, lblDate, lblCapacity;
     private JTextField txtEventName, txtDate, txtCategory, txtCapacity, txtSearch, txtWaitingName, txtWaitingEmail, txtAttendeeName, txtEmail;
     private JButton btnAdd, btnUpdate, btnDelete, btnSearch, btnShowAll, btnJoinWaiting, btnAdmitNext, btnRemoveWaiting, btnAddAttendee, btnRemoveAttendee, btnCheckIn;
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
     private JLabel lblReportTitle;
     private JTextArea txtReport;
     private JScrollPane reportScroll;
     private JButton btnGenerateReport;
     private boolean loading = false;   //true while the dropdowns are being refilled

     //Declaring Event Table Columns
      Object[][] data = {};
      String[] columns = {"ID","Event Name","Date","Category","Attendees"};


     public UI(){

        setTitle("Event Management System");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        //Logo
        ImageIcon image = new ImageIcon("event.png");
        setIconImage(image.getImage());


        //Tabs and Panels
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

        //Labels
        lblEventName = new JLabel("Event Name:");
        lblEventName.setBounds(5, 40, 150, 30);
        eventPanel.add(lblEventName);

        lblDate = new JLabel("Date (yyyy-MM-dd):");
        lblDate.setBounds(165, 40, 130, 30);
        eventPanel.add(lblDate);

        lblCategory = new JLabel("Category:");
        lblCategory.setBounds(305, 40, 100, 30);
        eventPanel.add(lblCategory);

        lblCapacity = new JLabel("Capacity:");
        lblCapacity.setBounds(465, 40, 100, 30);
        eventPanel.add(lblCapacity);

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

        lblReportTitle = new JLabel("REPORT PANEL");
        lblReportTitle.setFont(lblReportTitle.getFont().deriveFont(Font.BOLD, 14f));
        lblReportTitle.setBounds(5, 10, 300, 25);
        reportPanel.add(lblReportTitle);

        //Text Fields
        txtEventName = new JTextField();
        txtEventName.setBounds(5, 70, 150, 32);
        eventPanel.add(txtEventName);

        txtDate = new JTextField();
        txtDate.setBounds(165, 70, 130, 32);
        eventPanel.add(txtDate);

        txtCategory = new JTextField();
        txtCategory.setBounds(305, 70, 150, 32);
        eventPanel.add(txtCategory);

        txtCapacity = new JTextField();
        txtCapacity.setBounds(465, 70, 90, 32);
        eventPanel.add(txtCapacity);

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

        //Buttons
        btnAdd = new JButton("Add Event");
        btnAdd.setBounds(570, 70, 105, 32);
        eventPanel.add(btnAdd);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(690, 70, 105, 32);
        eventPanel.add(btnUpdate);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(810, 70, 105, 32);
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

        btnCheckIn = new JButton("Check In");
        btnCheckIn.setBounds(530, 520, 130, 30);
        attendeePanel.add(btnCheckIn);

        btnGenerateReport = new JButton("Generate Attendance Report");
        btnGenerateReport.setBounds(5, 510, 970, 32);
        reportPanel.add(btnGenerateReport);

        //Tables and ScrollPane
        model = new DefaultTableModel(data, columns);
        eventTable = new JTable(model);
        scroll = new JScrollPane(eventTable);
        scroll.setBounds(5, 120, 970, 400);
        eventPanel.add(scroll);

        list = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(10, 50, 850, 350);
        attendeePanel.add(scrollPane);

        waitingModel = new DefaultListModel<>();
        waitingList = new JList<>(waitingModel);
        JScrollPane waitingScroll = new JScrollPane(waitingList);
        waitingScroll.setBounds(10, 50, 850, 350);
        waitingPanel.add(waitingScroll);

        txtReport = new JTextArea();
        txtReport.setEditable(false);
        txtReport.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        reportScroll = new JScrollPane(txtReport,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        reportScroll.setBounds(5, 45, 970, 460);
        reportPanel.add(reportScroll);
        searchType = new JComboBox<>();
        searchType.addItem("Search by Date");
        searchType.addItem("Search by Category");
        searchType.setBounds(10, 535, 370, 30);
        eventPanel.add(searchType);

        waitingEvent = new JComboBox<>();
        waitingEvent.setBounds(10, 10, 850, 30);
        waitingPanel.add(waitingEvent);

        attendeeEvent = new JComboBox<>();
        attendeeEvent.setBounds(10, 10, 850, 30);
        attendeePanel.add(attendeeEvent);

        //Priority is limited to 1-3 (3 is the default level used for normal registrations)
        waitingPriority = new JSpinner(new SpinnerNumberModel(3, 1, 3, 1));
        waitingPriority.setBounds(530, 440, 100, 30);
        waitingPanel.add(waitingPriority);

        //Adding ActionListener
        waitingEvent.addActionListener(this);
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
        btnCheckIn.addActionListener(this);
        btnGenerateReport.addActionListener(this);
        attendeeEvent.addActionListener(this);

        //Reload from the database whenever the user switches tabs (see stateChanged)
        tabbedPane.addChangeListener(this);

        //Fill the text fields when an event row is selected (see valueChanged)
        eventTable.setDefaultEditor(Object.class, null);
        eventTable.getSelectionModel().addListSelectionListener(this);

        refreshAll();
     }

    //Reloads everything from the database and redraws every tab
    private void refreshAll(){
        linkedList.clear();
        linkedList.addAll(eventDao.getAllEvents());
        showEvents();
        refreshCombos();
        showAttendees();
        showWaitingList();
    }

    //Refills both event dropdowns, keeping the current selection if it still exists
    private void refreshCombos(){
        loading = true;
        Event previousAttendee = (Event) attendeeEvent.getSelectedItem();
        Event previousWaiting = (Event) waitingEvent.getSelectedItem();
        attendeeEvent.removeAllItems();
        waitingEvent.removeAllItems();
        for(Event event : linkedList){
            attendeeEvent.addItem(event);
            waitingEvent.addItem(event);
        }
        selectEventById(attendeeEvent, previousAttendee);
        selectEventById(waitingEvent, previousWaiting);
        loading = false;
    }

    private void selectEventById(JComboBox<Event> combo, Event previous){
        if(previous == null){
            return;
        }
        for(int i = 0; i < combo.getItemCount(); i++){
            if(combo.getItemAt(i).getEventId() == previous.getEventId()){
                combo.setSelectedIndex(i);
                return;
            }
        }
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
                event.getCategory(), event.getAttendees().size() + "/" + event.getCapacity()});
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

    private void showWaitingList(){
        waitingModel.clear();
        Event event = (Event) waitingEvent.getSelectedItem();
        if(event != null){
            for(WaitingEntry entry : registrationService.getWaitlist(event.getEventId())){
                waitingModel.addElement(entry);
            }
        }
    }

    private void inputError(String message){
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    private boolean validDate(String date){
        try{
            LocalDate.parse(date);
            return true;
        }catch(DateTimeParseException ex){
            return false;
        }
    }

    //Returns the capacity typed in the field, or -1 if it is invalid
    private int readCapacity(){
        try{
            int capacity = Integer.parseInt(txtCapacity.getText().trim());
            if(capacity < 1){
                inputError("Capacity must be at least 1");
                return -1;
            }
            return capacity;
        }catch(NumberFormatException ex){
            inputError("Capacity must be a whole number");
            return -1;
        }
    }


    //Runs when the user switches tabs
    @Override
    public void stateChanged(ChangeEvent e){
        if(!loading){
            refreshAll();
        }
    }

    //Runs when a row in the event table is selected
    @Override
    public void valueChanged(ListSelectionEvent e){
        int index = eventTable.getSelectedRow();
        if(!e.getValueIsAdjusting() && index != -1 && index < displayedEvents.size()){
            Event event = displayedEvents.get(index);
            txtEventName.setText(event.getName());
            txtDate.setText(event.getDate());
            txtCategory.setText(event.getCategory());
            txtCapacity.setText(String.valueOf(event.getCapacity()));
        }
    }


    @Override
    public void actionPerformed(ActionEvent e){
        if(loading){
            return;
        }
        if(e.getSource() == btnAdd || e.getSource() == btnUpdate){
            String name = txtEventName.getText().trim();
            String date = txtDate.getText().trim();
            String category = txtCategory.getText().trim();
            if(name.isEmpty() || date.isEmpty() || category.isEmpty()){
                inputError("Please enter event name, date and category first");
                return;
            }
            if(!validDate(date)){
                inputError("Date must be in yyyy-MM-dd format");
                return;
            }
            int capacity = readCapacity();
            if(capacity == -1){
                return;
            }
            if(e.getSource() == btnAdd){
                eventDao.addEvent(new Event(0, name, date, category, capacity));
            }else{
                int indexSelected = eventTable.getSelectedRow();
                if(indexSelected == -1){
                    inputError("Please select event first");
                    return;
                }
                Event event = displayedEvents.get(indexSelected);
                if(capacity < event.getAttendees().size()){
                    inputError("Capacity cannot be lower than the current number of attendees ("
                        + event.getAttendees().size() + ")");
                    return;
                }
                event.setName(name);
                event.setDate(date);
                event.setCategory(category);
                event.setCapacity(capacity);
                eventDao.updateEvent(event);
            }
            txtEventName.setText("");
            txtDate.setText("");
            txtCategory.setText("");
            txtCapacity.setText("");
            refreshAll();
        }else if(e.getSource() == btnDelete){
            int indexSelected = eventTable.getSelectedRow();
            if(indexSelected != -1){
                Event event = displayedEvents.get(indexSelected);
                int answer = JOptionPane.showConfirmDialog(this,
                    "Delete this event together with its attendees and waiting list?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if(answer == JOptionPane.YES_OPTION){
                    eventDao.deleteEvent(event.getEventId());
                    refreshAll();
                }
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
            refreshAll();
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
                    inputError("Please enter a priority from 1 to 3");
                    return;
                }
                Attendee attendee = new Attendee(0, name, email, event.getEventId());
                String message = registrationService.joinWaitlist(attendee,
                    (Integer) waitingPriority.getValue());
                JOptionPane.showMessageDialog(this, message, "Queue List", JOptionPane.INFORMATION_MESSAGE);
                txtWaitingName.setText("");
                txtWaitingEmail.setText("");
                refreshAll();
            }
        }else if(e.getSource() == btnAdmitNext){
            Event event = (Event) waitingEvent.getSelectedItem();
            if(event == null){
                inputError("Please select event first");
            }else{
                String message = registrationService.admitNext(event.getEventId());
                JOptionPane.showMessageDialog(this, message, "Admit Next", JOptionPane.INFORMATION_MESSAGE);
                refreshAll();
            }
        }else if(e.getSource() == btnRemoveWaiting){
            Event event = (Event) waitingEvent.getSelectedItem();
            WaitingEntry entry = waitingList.getSelectedValue();
            if(event == null || entry == null){
                inputError("Please select a waiting attendee first");
            }else{
                registrationService.removeFromWaitlist(event.getEventId(), entry);
                refreshAll();
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
            }else if(!email.contains("@")){
                inputError("Please enter a valid email");
            }else{
                Attendee attendee = new Attendee(0, name, email, event.getEventId());
                String message = registrationService.register(attendee);
                JOptionPane.showMessageDialog(this, message, "Registration", JOptionPane.INFORMATION_MESSAGE);
                txtAttendeeName.setText("");
                txtEmail.setText("");
                refreshAll();
            }
        }else if(e.getSource() == btnRemoveAttendee){
            Attendee attendee = list.getSelectedValue();
            if(attendee == null){
                inputError("Please select attendee first");
            }else{
                attendeeDao.removeAttendee(attendee.getId());
                refreshAll();
            }
        }else if(e.getSource() == btnCheckIn){
            Attendee attendee = list.getSelectedValue();
            if(attendee == null){
                inputError("Please select an attendee first");
            }else if(attendee.isCheckedIn()){
                inputError("This attendee has already checked in");
            }else if(attendeeDao.checkInAttendee(attendee.getId())){
                JOptionPane.showMessageDialog(this, "Attendee checked in successfully!",
                    "Check In", JOptionPane.INFORMATION_MESSAGE);
                refreshAll();
            }else{
                inputError("Check in failed. Please try again");
            }
        }else if(e.getSource() == btnGenerateReport){
            generateReport();
        }
    }

    //Generates the attendance report and shows it in the report scroll pane
    private void generateReport(){
        StringBuilder sb = new StringBuilder();
        sb.append("EVENT MANAGEMENT SYSTEM - ATTENDANCE REPORT\n");
        sb.append("Generated: ").append(LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))).append("\n");
        sb.append("=".repeat(70)).append("\n\n");
        int totalAttendees = 0;
        if(linkedList.isEmpty()){
            sb.append("No events found.\n\n");
        }
        for(Event ev : linkedList){
            sb.append("Event #").append(ev.getEventId()).append(": ").append(ev.getName()).append("\n");
            sb.append("Date      : ").append(ev.getDate()).append("\n");
            sb.append("Category  : ").append(ev.getCategory()).append("\n");
            sb.append("Attendees : ").append(ev.getAttendees().size()).append("\n");
            if(ev.getAttendees().isEmpty()){
                sb.append("   (no attendees registered)\n");
            }else{
                int n = 1;
                for(Attendee a : ev.getAttendees()){
                    sb.append(String.format("   %d. %-25s %s%n", n++, a.getName(), a.getEmail()));
                }
            }
            sb.append("\n").append("-".repeat(70)).append("\n\n");
            totalAttendees += ev.getAttendees().size();
        }
        sb.append("SUMMARY\n");
        sb.append("Total events        : ").append(linkedList.size()).append("\n");
        sb.append("Total attendees     : ").append(totalAttendees).append("\n");
        txtReport.setText(sb.toString());
        txtReport.setCaretPosition(0);
    }
}