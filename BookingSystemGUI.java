import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.*;

public class BookingSystemGUI extends JFrame {
    private static ArrayList<Facility> facilities = new ArrayList<>();
    private static ArrayList<Booking> activeBookings = new ArrayList<>();
    private static int facilityCounter = 5;

    private User currentUserSession;
    private Color bgPrimary, bgSecondary, textPrimary;

    private JComboBox<String> facilityDropdown;
    private JTextField txtHours, txtDamageDesc, txtFineAmount;
    private JComboBox<String> bookingDropdown;
    private JTextArea txtLogs;
    private JTextField txtNewFacilityName, txtNewFacilityRate, txtCourtLimit;
    private JComboBox<String> typeDropdown;
    private JPanel pnlInput, pnlPenalty, pnlCrud, pnlOutput;

    public static class MainMenu extends JFrame {
        private JTextField txtMenuName, txtMenuId;
        private JRadioButton rbMenuStudent, rbMenuStaff;
        private JComboBox<String> themeDropdown;
        private JLabel lblMenuId;
        public MainMenu() {
            if (facilities.isEmpty()) {
                facilities.add(new IndoorSport("F01", "Badminton Court Hall A", 20.0, false));
                facilities.add(new IndoorSport("F02", "Squash Room 1", 15.0, false));
                facilities.add(new OutdoorSport("F03", "Main Football Field", 40.0, 0.0));
                facilities.add(new OutdoorSport("F04", "Tennis Center Court", 25.0, 0.0));
            }

            setTitle("University Sports Facility Booking System - Login & Customization");
            setSize(480, 500);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel mainContentPanel = new JPanel();
            mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
            mainContentPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
            
            Color bgMenu = new Color(245, 247, 250);
            Color textMenu = new Color(30, 41, 59);
            Color accentMenu = new Color(37, 99, 235);
            
            mainContentPanel.setBackground(bgMenu);

            JLabel lblTitle = new JLabel("University Facility Hub");
            lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
            lblTitle.setForeground(accentMenu);
            lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel lblSubtitle = new JLabel("Identity Authorization & Preferences");
            lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            lblSubtitle.setForeground(new Color(100, 116, 139));
            lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel pnlDesignation = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
            pnlDesignation.setBackground(bgMenu);
            rbMenuStudent = new JRadioButton("Student Profile", true);
            rbMenuStaff = new JRadioButton("Staff Administrator");
            rbMenuStudent.setBackground(bgMenu);
            rbMenuStudent.setForeground(textMenu);
            rbMenuStudent.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            rbMenuStaff.setBackground(bgMenu);
            rbMenuStaff.setForeground(textMenu);
            rbMenuStaff.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            
            ButtonGroup bg = new ButtonGroup();
            bg.add(rbMenuStudent);
            bg.add(rbMenuStaff);
            pnlDesignation.add(rbMenuStudent);
            pnlDesignation.add(rbMenuStaff);
            pnlDesignation.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel pnlName = new JPanel(new BorderLayout(10, 0));
            pnlName.setBackground(bgMenu);
            pnlName.setMaximumSize(new Dimension(400, 35));
            JLabel lblName = new JLabel("User Full Name: ");
            lblName.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lblName.setForeground(textMenu);
            lblName.setPreferredSize(new Dimension(110, 25));
            txtMenuName = new JTextField();
            txtMenuName.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            txtMenuName.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                BorderFactory.createEmptyBorder(4, 6, 4, 6)
            ));
            pnlName.add(lblName, BorderLayout.WEST);
            pnlName.add(txtMenuName, BorderLayout.CENTER);

            JPanel pnlId = new JPanel(new BorderLayout(10, 0));
            pnlId.setBackground(bgMenu);
            pnlId.setMaximumSize(new Dimension(400, 35));
            lblMenuId = new JLabel("Matric Number:");
            lblMenuId.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lblMenuId.setForeground(textMenu);
            lblMenuId.setPreferredSize(new Dimension(110, 25));
            txtMenuId = new JTextField();
            txtMenuId.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            txtMenuId.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                BorderFactory.createEmptyBorder(4, 6, 4, 6)
            ));
            pnlId.add(lblMenuId, BorderLayout.WEST);
            pnlId.add(txtMenuId, BorderLayout.CENTER);

            JPanel pnlTheme = new JPanel(new BorderLayout(10, 0));
            pnlTheme.setBackground(bgMenu);
            pnlTheme.setMaximumSize(new Dimension(400, 35));
            JLabel lblTheme = new JLabel("System Theme:  ");
            lblTheme.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lblTheme.setForeground(textMenu);
            lblTheme.setPreferredSize(new Dimension(110, 25));
            String[] themes = { "Classic Blue", "Dark Mode", "Emerald Green", "Crimson Red", "Cyberpunk Gold" };
            themeDropdown = new JComboBox<>(themes);
            themeDropdown.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            pnlTheme.add(lblTheme, BorderLayout.WEST);
            pnlTheme.add(themeDropdown, BorderLayout.CENTER);

            rbMenuStudent.addActionListener(e -> lblMenuId.setText("Matric Number:"));
            rbMenuStaff.addActionListener(e -> lblMenuId.setText("Staff ID:"));

            JButton btnLaunch = new JButton("Initialize Session Workspace");
            btnLaunch.setFont(new Font("Segoe UI", Font.BOLD, 13));
            btnLaunch.setBackground(accentMenu);
            btnLaunch.setForeground(Color.WHITE);
            btnLaunch.setFocusPainted(false);
            btnLaunch.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnLaunch.setMaximumSize(new Dimension(280, 40));

            mainContentPanel.add(lblTitle);
            mainContentPanel.add(Box.createVerticalStrut(4));
            mainContentPanel.add(lblSubtitle);
            mainContentPanel.add(Box.createVerticalStrut(20));
            mainContentPanel.add(pnlDesignation);
            mainContentPanel.add(Box.createVerticalStrut(15));
            mainContentPanel.add(pnlName);
            mainContentPanel.add(Box.createVerticalStrut(15));
            mainContentPanel.add(pnlId);
            mainContentPanel.add(Box.createVerticalStrut(15));
            mainContentPanel.add(pnlTheme);
            mainContentPanel.add(Box.createVerticalStrut(25));
            mainContentPanel.add(btnLaunch);

            add(mainContentPanel);

            btnLaunch.addActionListener(e -> {
                String name = txtMenuName.getText().trim();
                String id = txtMenuId.getText().trim();
                if (name.isEmpty() || id.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "System Security Error: Identity cells cannot remain blank.",
                            "Login Refused", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                User sessionUser = rbMenuStudent.isSelected() ? new Student(name, id) : new Staff(name, id);
                String selectedTheme = (String) themeDropdown.getSelectedItem();

                this.dispose();
                new BookingSystemGUI(sessionUser, selectedTheme).setVisible(true);
            });
        }
    }

    public BookingSystemGUI(User userSession, String themeName) {
        this.currentUserSession = userSession;
        applyThemeColors(themeName);

        setTitle("Facility Management Engine - Active User: " + currentUserSession.getName());
        setSize(1150, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
        
        // Add outer margin padding to the main window
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- NEW: TOP NAVIGATION BAR PANEL (BACK BUTTON AREA) ---
        JPanel pnlTopNav = new JPanel(new BorderLayout());
        pnlTopNav.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JButton btnBack = new JButton("⬅ Log Out / Back to Main Menu");
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 12));
        pnlTopNav.add(btnBack, BorderLayout.WEST);

        // Dynamic Greeting Header label text
        JLabel lblHeaderInfo = new JLabel(
                "Logged Session: " + currentUserSession.getUserDetails() + " | Theme Focus: " + themeName,
                JLabel.RIGHT);
        lblHeaderInfo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        pnlTopNav.add(lblHeaderInfo, BorderLayout.EAST);
        add(pnlTopNav, BorderLayout.NORTH);

        // --- PANEL 1: BOOKING SYSTEM ---
        pnlInput = new JPanel(new GridLayout(4, 2, 10, 10));
        pnlInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Step 1: Place Dynamic Booking"),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        pnlInput.add(new JLabel("Select Facility Asset:"));
        facilityDropdown = new JComboBox<>();
        refreshFacilityDropdown();
        pnlInput.add(facilityDropdown);

        pnlInput.add(new JLabel("Current Logged Client Profile:"));
        pnlInput.add(new JLabel(currentUserSession.getUserDetails()));

        pnlInput.add(new JLabel("Rental Window Duration (Hours):"));
        txtHours = new JTextField();
        pnlInput.add(txtHours);

        JButton btnBook = new JButton("Confirm Booking Allocation");
        pnlInput.add(btnBook);

        // --- PANEL 2: COMPLAINT REPORTING ---
        pnlPenalty = new JPanel(new GridLayout(4, 2, 10, 10));
        pnlPenalty.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Step 2: Damage & Incident Diagnostics"),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        pnlPenalty.add(new JLabel("Active Selection Session ID:"));
        bookingDropdown = new JComboBox<>();
        if (activeBookings.isEmpty()) {
            bookingDropdown.addItem("No active sessions");
        }
        pnlPenalty.add(bookingDropdown);

        pnlPenalty.add(new JLabel("Incident Complain Log Note:"));
        txtDamageDesc = new JTextField();
        pnlPenalty.add(txtDamageDesc);

        pnlPenalty.add(new JLabel("Fine Amount (RM) [Staff Administration Only]:"));
        txtFineAmount = new JTextField();

        if (currentUserSession instanceof Student) {
            txtFineAmount.setText("Locked - Students Cannot Apply Fines");
            txtFineAmount.setEditable(false);
        }
        pnlPenalty.add(txtFineAmount);

        JButton btnReportDamage = new JButton("Submit Performance Log");
        pnlPenalty.add(btnReportDamage);
        
        JButton btnEndSession = new JButton("End Session");
        pnlPenalty.add(btnEndSession);

        // --- PANEL 3: ADMINISTRATIVE SYSTEM MODIFIERS ---
        pnlCrud = new JPanel(new GridLayout(6, 2, 10, 10));
        pnlCrud.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Step 3: Database Administration Modifiers"),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        pnlCrud.add(new JLabel("New Facility Structural Name:"));
        txtNewFacilityName = new JTextField();
        pnlCrud.add(txtNewFacilityName);

        pnlCrud.add(new JLabel("Base Hourly Allocation Rate (RM):"));
        txtNewFacilityRate = new JTextField();
        pnlCrud.add(txtNewFacilityRate);

        pnlCrud.add(new JLabel("Facility Court Limit:"));
        txtCourtLimit = new JTextField("1");
        pnlCrud.add(txtCourtLimit);

        pnlCrud.add(new JLabel("Infrastructure Arena Class Type:"));
        typeDropdown = new JComboBox<>(new String[] { "Indoor Facility", "Outdoor Facility" });
        pnlCrud.add(typeDropdown);

        JButton btnAddFacility = new JButton("Add New Facility Asset");
        JButton btnUpdateFacility = new JButton("Update Selected Asset");
        JButton btnDeleteFacility = new JButton("Delete Chosen Asset");
        JButton btnOpenSession = new JButton("Open Session");
        
        pnlCrud.add(btnAddFacility);
        pnlCrud.add(btnUpdateFacility);
        pnlCrud.add(btnDeleteFacility);
        pnlCrud.add(btnOpenSession);

        if (currentUserSession instanceof Student) {
            txtNewFacilityName.setEnabled(false);
            txtNewFacilityRate.setEnabled(false);
            txtCourtLimit.setEnabled(false);
            typeDropdown.setEnabled(false);
            btnAddFacility.setEnabled(false);
            btnUpdateFacility.setEnabled(false);
            btnDeleteFacility.setEnabled(false);
            btnOpenSession.setEnabled(false);
            pnlCrud.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder("Step 3: Database Modifiers [RESTRICTED TO STAFF ONLY]"),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        }

        JPanel pnlLeftPanel = new JPanel();
        pnlLeftPanel.setLayout(new BoxLayout(pnlLeftPanel, BoxLayout.Y_AXIS));
        pnlLeftPanel.add(pnlInput);
        pnlLeftPanel.add(Box.createVerticalStrut(15));
        pnlLeftPanel.add(pnlPenalty);
        pnlLeftPanel.add(Box.createVerticalStrut(15));
        pnlLeftPanel.add(pnlCrud);
        
        JScrollPane scrollPane = new JScrollPane(pnlLeftPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.WEST);
        // --- PANEL 4: DIAGNOSTICS LOG ---
        pnlOutput = new JPanel(new BorderLayout());
        pnlOutput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Live Telemetry System Monitoring Stream"),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        txtLogs = new JTextArea(25, 50);
        txtLogs.setEditable(false);
        txtLogs.setFont(new Font("Consolas", Font.PLAIN, 12));
        pnlOutput.add(new JScrollPane(txtLogs), BorderLayout.CENTER);
        add(pnlOutput, BorderLayout.CENTER);
        
        styleComponentTree(this);
        pnlTopNav.setBackground(bgSecondary); 
        btnBack.setBackground(new Color(220, 38, 38)); 
        btnBack.setForeground(Color.WHITE);
        lblHeaderInfo.setForeground(Color.WHITE);
        // --- CONTROLLER ACTION ROUTINES ---
        // Navigation Handler Action: Safely drop working space and reopen the main
        btnBack.addActionListener(e -> {
            int verify = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to log out and return to the main config menu?",
                    "Confirm Session Reset", JOptionPane.YES_NO_OPTION);
            if (verify == JOptionPane.YES_OPTION) {
                this.dispose(); // Close dashboard completely
                new MainMenu().setVisible(true); // Re-launch fresh Main Menu window
            }
        });
        btnBook.addActionListener(e -> {
            try {
                int index = facilityDropdown.getSelectedIndex();
                Facility facility = facilities.get(index);

                if (facility.getStatus().equals("Under Maintenance")) {
                    throw new Exception("Lockout Error: Chosen asset is completely offline for emergency repairs.");
                }
                if (facility.getStatus().equals("Stopped")) {
                    throw new Exception("Facility is currently stopped. Please wait for Staff to open the session.");
                }
                if (facility.getStatus().equals("Fully Booked")) {
                    throw new Exception("Facility is fully booked. Court limit reached.");
                }

                String cleanHours = txtHours.getText().replaceAll("[^0-9]", "");
                if (cleanHours.isEmpty())
                    throw new Exception("Numeric execution mismatch. Please input active hour counts.");
                int hours = Integer.parseInt(cleanHours);

                if (hours <= 0 || hours > 5)
                    throw new Exception("Safety Window Constraint Boundary violation: Must reside within 1-5 hours.");
                double cost = facility.calculateBookingFee(hours);
                String bId = "BKN-" + (activeBookings.size() + 1001);
                Booking newBooking = new Booking(bId, currentUserSession, facility, hours);
                activeBookings.add(newBooking);
                if (getActiveBookingCountForFacility(facility) >= facility.courtLimit) {
                    facility.setStatus("Fully Booked");
                }

                if (activeBookings.size() == 1) {
                    bookingDropdown.removeAllItems();
                }
                bookingDropdown.addItem(bId + " - " + facility.getName());
                refreshFacilityDropdown();
                facilityDropdown.setSelectedIndex(index);

                txtLogs.append(">>> BOOKING SYSTEM SUCCESSFUL RECORDION <<<\n" +
                        "ID Hash Value: " + bId + "\n" +
                        currentUserSession.getUserDetails() + "\n" +
                        "Target Asset Class: " + facility.getName() + "\n" +
                        "Calculated Invoice Charge: RM " + cost + "\n----------------------------------------\n");

                // Export receipt to file
                exportReceiptToFile(bId, currentUserSession, facility, hours, cost);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Booking Interrupted Exception",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnReportDamage.addActionListener(e -> {
            try {
                if (activeBookings.isEmpty())
                    return;
                int index = bookingDropdown.getSelectedIndex();
                Booking target = activeBookings.get(index);
                String desc = txtDamageDesc.getText().trim();

                if (desc.isEmpty())
                    throw new Exception("Incident report validation breakdown: Note details cannot be blank.");
                double fine = 0.0;
                if (currentUserSession instanceof Staff) {
                    String fineInput = txtFineAmount.getText().trim();
                    if (fineInput.isEmpty())
                        throw new Exception("Administrative staff must apply explicit financial fine constants.");
                    fine = Double.parseDouble(fineInput);
                    if (fine < 0)
                        throw new Exception("Financial accounting constraints block negative fine numbers.");
                }

                String outcomeMessage = target.issuePenalty(currentUserSession, desc, fine);
                txtLogs.append(outcomeMessage + "----------------------------------------\n");
                refreshFacilityDropdown();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Reporting Failure", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAddFacility.addActionListener(e -> {
            try {
                String name = txtNewFacilityName.getText().trim();
                double rate = Double.parseDouble(txtNewFacilityRate.getText().trim());
                if (name.isEmpty())
                    throw new Exception("Data entry blank.");
                int limit = Integer.parseInt(txtCourtLimit.getText().trim());
                if (limit <= 0) throw new Exception("Court limit must be greater than 0.");

                Facility f = (typeDropdown.getSelectedIndex() == 0)
                        ? new IndoorSport("F0" + (facilityCounter++), name, rate, false)
                        : new OutdoorSport("F0" + (facilityCounter++), name, rate, 0.0);
                f.courtLimit = limit;

                facilities.add(f);
                refreshFacilityDropdown();
                txtLogs.append("[SYSTEM ACTION] Admin Added Asset Configuration: " + name + "\n");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Data formatting error: " + ex.getMessage(),
                        "Action Aborted", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnDeleteFacility.addActionListener(e -> {
            if (facilities.size() <= 1)
                return;
            int idx = facilityDropdown.getSelectedIndex();
            Facility removed = facilities.remove(idx);
            refreshFacilityDropdown();
            txtLogs.append("[SYSTEM ACTION] Admin Deleted Asset Configuration: " + removed.getName() + "\n");
        });

        // Auto-populate facility details when selected from facilityDropdown
        facilityDropdown.addActionListener(e -> {
            int idx = facilityDropdown.getSelectedIndex();
            if (idx >= 0 && idx < facilities.size()) {
                Facility f = facilities.get(idx);
                txtNewFacilityName.setText(f.name);
                txtNewFacilityRate.setText(String.valueOf(f.baseRate));
                txtCourtLimit.setText(String.valueOf(f.courtLimit));
                if (f instanceof IndoorSport) {
                    typeDropdown.setSelectedIndex(0);
                } else if (f instanceof OutdoorSport) {
                    typeDropdown.setSelectedIndex(1);
                }
            }
        });

        btnEndSession.addActionListener(e -> {
            try {
                if (activeBookings.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No active booking sessions to end.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                int index = bookingDropdown.getSelectedIndex();
                if (index < 0) return;

                Booking target = activeBookings.remove(index);
                target.getFacility().setStatus("Stopped"); // Set to Stopped upon ending session

                bookingDropdown.removeAllItems();
                if (activeBookings.isEmpty()) {
                    bookingDropdown.addItem("No active sessions");
                } else {
                    for (Booking b : activeBookings) {
                        bookingDropdown.addItem(b.getBookingId() + " - " + b.getFacility().getName());
                    }
                }

                refreshFacilityDropdown();
                txtLogs.append("[SYSTEM ACTION] Ended Booking Session: " + target.getBookingId() + " for " + target.getFacility().getName() + " (Court status shifted to Stopped)\n");
                JOptionPane.showMessageDialog(this, "Booking session " + target.getBookingId() + " ended. Court is now Stopped until Staff reopens it.", "Session Terminated", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error ending session: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnUpdateFacility.addActionListener(e -> {
            try {
                int idx = facilityDropdown.getSelectedIndex();
                if (idx < 0) {
                    JOptionPane.showMessageDialog(this, "No facility selected to update.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Facility f = facilities.get(idx);

                String name = txtNewFacilityName.getText().trim();
                if (name.isEmpty()) throw new Exception("Facility name cannot be empty.");
                double rate = Double.parseDouble(txtNewFacilityRate.getText().trim());
                int limit = Integer.parseInt(txtCourtLimit.getText().trim());
                if (limit <= 0) throw new Exception("Court limit must be greater than 0.");

                f.name = name;
                f.baseRate = rate;
                f.courtLimit = limit;

                boolean isIndoor = (typeDropdown.getSelectedIndex() == 0);
                if (isIndoor && !(f instanceof IndoorSport)) {
                    f = new IndoorSport(f.facilityId, name, rate, false);
                    facilities.set(idx, f);
                } else if (!isIndoor && !(f instanceof OutdoorSport)) {
                    f = new OutdoorSport(f.facilityId, name, rate, 0.0);
                    facilities.set(idx, f);
                }
                
                // Re-evaluate status under the new limit
                if (f.getStatus().equals("Fully Booked") && getActiveBookingCountForFacility(f) < f.courtLimit) {
                    f.setStatus("Available");
                } else if (f.getStatus().equals("Available") && getActiveBookingCountForFacility(f) >= f.courtLimit) {
                    f.setStatus("Fully Booked");
                }

                refreshFacilityDropdown();
                facilityDropdown.setSelectedIndex(idx);
                txtLogs.append("[SYSTEM ACTION] Admin Updated Asset: " + name + " (Rate: RM " + rate + ", Court Limit: " + limit + ")\n");
                JOptionPane.showMessageDialog(this, "Facility updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to update facility: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnOpenSession.addActionListener(e -> {
            try {
                int idx = facilityDropdown.getSelectedIndex();
                if (idx < 0) {
                    JOptionPane.showMessageDialog(this, "No facility selected to open.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Facility f = facilities.get(idx);

                if (getActiveBookingCountForFacility(f) < f.courtLimit) {
                    f.setStatus("Available");
                } else {
                    f.setStatus("Fully Booked");
                }

                refreshFacilityDropdown();
                facilityDropdown.setSelectedIndex(idx);
                txtLogs.append("[SYSTEM ACTION] Staff Reopened Session for Facility: " + f.getName() + "\n");
                JOptionPane.showMessageDialog(this, "Session reopened for facility " + f.getName() + ". Students can now book again.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error reopening session: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void refreshFacilityDropdown() {
        facilityDropdown.removeAllItems();
        for (Facility f : facilities) {
            facilityDropdown.addItem(f.getName() + " - RM" + String.format("%.2f", f.baseRate) + "/hour (" + f.getStatus() + ")");
        }
    }

    private int getActiveBookingCountForFacility(Facility f) {
        int count = 0;
        for (Booking b : activeBookings) {
            if (b.getFacility().facilityId.equals(f.facilityId)) {
                count++;
            }
        }
        return count;
    }

    private void exportReceiptToFile(String bookingId, User user, Facility facility, int hours, double cost) {
        try {
            // Create receipts folder if it doesn't exist
            File receiptsDir = new File("receipts");
            if (!receiptsDir.exists()) {
                receiptsDir.mkdir();
            }

            // Generate timestamp for unique filename
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");
            String timestamp = now.format(formatter);

            // Create receipt filename
            String filename = "receipts/" + bookingId + "_" + timestamp + ".txt";
            
            // Write receipt to file
            PrintWriter writer = new PrintWriter(filename);
            writer.println("========================================");
            writer.println("     FACILITY BOOKING RECEIPT");
            writer.println("========================================");
            writer.println("Booking ID: " + bookingId);
            writer.println("Date/Time: " + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            writer.println("----------------------------------------");
            writer.println("User: " + user.getUserDetails());
            writer.println("Facility: " + facility.getName());
            writer.println("Status: " + facility.getStatus());
            writer.println("----------------------------------------");
            writer.println("Duration: " + hours + " hour(s)");
            writer.println("Hourly Rate: RM " + String.format("%.2f", facility.baseRate));
            writer.println("Total Cost: RM " + String.format("%.2f", cost));
            writer.println("----------------------------------------");
            writer.println("This receipt has been saved for your records.");
            writer.println("Please keep it for reference.");
            writer.println("========================================");
            writer.close();

            // Show success dialog with file location
            String absolutePath = new File(filename).getAbsolutePath();
            JOptionPane.showMessageDialog(this, 
                "Receipt exported successfully!\n\nFile saved at:\n" + absolutePath,
                "Receipt Saved", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Failed to export receipt: " + ex.getMessage(),
                "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void applyThemeColors(String theme) {
        switch (theme) {
            case "Dark Mode":
                bgPrimary = new Color(30, 41, 59); // Slate 800
                bgSecondary = new Color(79, 70, 229); // Indigo 600
                textPrimary = new Color(243, 244, 246); // Gray 100
                break;
            case "Emerald Green":
                bgPrimary = new Color(240, 253, 244); // Green 50
                bgSecondary = new Color(5, 150, 105); // Green 600
                textPrimary = new Color(6, 78, 59); // Green 900
                break;
            case "Crimson Red":
                bgPrimary = new Color(254, 242, 242); // Red 50
                bgSecondary = new Color(220, 38, 38); // Red 600
                textPrimary = new Color(127, 29, 29); // Red 900
                break;
            case "Cyberpunk Gold":
                bgPrimary = new Color(24, 24, 27); // Zinc 900
                bgSecondary = new Color(245, 158, 11); // Amber 500
                textPrimary = new Color(251, 191, 36); // Amber 400
                break;
            case "Classic Blue":
            default:
                bgPrimary = new Color(241, 245, 249); // Slate 100
                bgSecondary = new Color(37, 99, 235); // Blue 600
                textPrimary = new Color(15, 23, 42); // Slate 900
                break;
        }
    }

    private void styleComponentTree(Component comp) {
        comp.setBackground(bgPrimary);
        comp.setForeground(textPrimary);

        if (comp instanceof JPanel) {
            JPanel panel = (JPanel) comp;
            panel.setBackground(bgPrimary);
            if (panel.getBorder() instanceof javax.swing.border.TitledBorder) {
                javax.swing.border.TitledBorder tb = (javax.swing.border.TitledBorder) panel.getBorder();
                tb.setTitleFont(new Font("Segoe UI", Font.BOLD, 13));
                tb.setTitleColor(bgSecondary);
                tb.setBorder(BorderFactory.createLineBorder(new Color(218, 224, 233), 1, true));
            }
            for (Component child : panel.getComponents()) {
                styleComponentTree(child);
            }
        } else if (comp instanceof JLabel) {
            comp.setFont(new Font("Segoe UI", Font.BOLD, 12));
            comp.setForeground(textPrimary);
        } else if (comp instanceof JComboBox || comp instanceof JTextField || comp instanceof JTextArea) {
            comp.setBackground(Color.WHITE);
            comp.setForeground(new Color(30, 41, 59));
            comp.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            if (comp instanceof JTextField || comp instanceof JTextArea) {
                JComponent jc = (JComponent) comp;
                jc.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                    BorderFactory.createEmptyBorder(4, 6, 4, 6)
                ));
            }
        } else if (comp instanceof JButton) {
            JButton btn = (JButton) comp;
            // Only override background if it isn't the red log out button
            if (!"⬅ Log Out / Back to Main Menu".equals(btn.getText())) {
                btn.setBackground(bgSecondary);
            }
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
            btn.setFocusPainted(false);
        }
    }
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {}
        }
        SwingUtilities.invokeLater(() -> new MainMenu().setVisible(true));
    }
}