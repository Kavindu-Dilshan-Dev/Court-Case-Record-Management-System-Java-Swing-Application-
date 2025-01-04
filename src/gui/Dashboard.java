/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import com.formdev.flatlaf.FlatClientProperties;
import static gui.SplashWindow.logger;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.ButtonModel;
import javax.swing.JOptionPane;
import model.MySQL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.FinesToInvoice;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author KAVINDU DILSHAN
 */
public class Dashboard extends javax.swing.JFrame {

    HashMap<String, String> courtTypeMap = new HashMap<>();
    HashMap<String, String> CaseTypeMap = new HashMap<>();
    HashMap<String, String> CaseStatusMap = new HashMap<>();
    HashMap<String, FinesToInvoice> fineDetailMap = new HashMap<>();
    HashMap<String, String> adminHashMap = new HashMap<>();

    public Dashboard(String username) {

        initComponents();
         this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/recources/softwareIcon.png")));
        jButton10.grabFocus();
        setAdminFunctions(username);
        setAdminEmail(username);
        loadAdmins();
        setTableContentCenter();
        loadPlaceHolders();
        loadToDaybookTable("");
        loadToDayBookCombobox();
        loadMotion("");
        setMotionUpdate();
        loadMotionPass("");
        loadFinesToTable();
        generateFineId();
        setDateFine();
        generateInvoiceID();
        jFormattedTextField2.setEditable(false);
        jFormattedTextField3.setEditable(false);
        jFormattedTextField1.setEditable(false);
        jButton30.setEnabled(false);
        loadCasesWhenFind();
        loadFindCasesWithOutNotesWhenFind();
        showPieChart1();
        showPieChart2();
        loadCaseToDashoard("");
        setEndedStstatus();
        changeTable(jTable9, 5);
        changeTable2(jTable9, 2);

      

    }
    
    
    public void changeTable(JTable table, int column_index) {
        table.getColumnModel().getColumn(column_index).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String st_val = String.valueOf(table.getValueAt(row, 5).toString());
               
                if (st_val.equals("Not Avialable")) {
                    c.setBackground(Color.RED);
                } else {
                    c.setBackground(Color.lightGray);
                }
                return c;
            }
        });
    }
    
    public void changeTable2(JTable table, int column_index) {
        table.getColumnModel().getColumn(column_index).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String st_val = String.valueOf(table.getValueAt(row, 2).toString());
               
                if (st_val.equals("Ended Case")) {
                    c.setBackground(Color.RED);
                } else {
                    c.setBackground(Color.YELLOW);
                }
                return c;
            }
        });
    }

    private static final String MYSQLDUMP_PATH = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin";
    private static final String BACKUP_DIRECTORY = "D:\\Backup";

    private void setAdminFunctions(String AdminUsername) {

        if (!AdminUsername.equals("kavindu4543@gmail.com")) {
            jButton1.setEnabled(false);
        }
    }
    
    

    public void showPieChart1() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        String query = "SELECT `casetype`.`name`,COUNT(`case_no`) AS `count` FROM `case` INNER JOIN `casetype`ON "
                + "`case`.`caseType_id` = `casetype`.`id` GROUP BY `casetype`.`name`"; // Adjust the limit if needed

        try {
            ResultSet rs = MySQL.executeSearch(query);
            while (rs.next()) {
                String caseType = rs.getString("name");
                double caseCount = rs.getDouble("count");
                dataset.setValue(caseType, caseCount);
            }
        } catch (Exception e) {
            logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
        }

        JFreeChart pieChart = ChartFactory.createRingChart(
                "", // Chart title
                dataset, // Dataset
                true, // Show legend
                true, // Use tooltips
                false // Generate URLs?
        );

        pieChart.setBackgroundPaint(new Color(210, 174, 109)); // Chart background color

        // Make the plot background transparent
        pieChart.getPlot().setBackgroundPaint(new Color(0, 0, 0, 0)); // Transparent plot background
        pieChart.getPlot().setOutlinePaint(new Color(0, 0, 0, 0)); // Transparent plot outline

        // Make the chart border transparent
        pieChart.setBorderPaint(new Color(0, 0, 0, 0)); // Transparent border paint

        // Adjust the plot to make it a doughnut chart
        org.jfree.chart.plot.RingPlot plot = (org.jfree.chart.plot.RingPlot) pieChart.getPlot();
        plot.setSectionDepth(0.50); // Adjust depth to create a doughnut effect

        // Set section colors to lighter variants
        int i = 0;
        Color[] colors = {new Color(255, 0, 0), new Color(71, 119, 113), new Color(101, 149, 143), new Color(141, 189, 183)};

        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(442, 340)); // Set preferred size
        jPanel12.removeAll();
        jPanel12.setLayout(new BorderLayout());
        jPanel12.add(chartPanel, BorderLayout.CENTER);
        jPanel12.validate();
        jPanel12.repaint(); // Force repaint
    }

    public void showPieChart2() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        String query = "SELECT `courttype`.`name`,COUNT(`case_no`) AS `count` FROM `case` INNER JOIN `courttype` ON `case`.`courtType_id` = `courttype`.`id` GROUP BY `courttype`.`name`"; // Adjust the limit if needed

        try {
            ResultSet rs = MySQL.executeSearch(query);
            while (rs.next()) {
                String caseType = rs.getString("name");
                double caseCount = rs.getDouble("count");
                dataset.setValue(caseType, caseCount);
            }
        } catch (Exception e) {
            logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
        }

        JFreeChart pieChart = ChartFactory.createRingChart(
                "", // Chart title
                dataset, // Dataset
                true, // Show legend
                true, // Use tooltips
                false // Generate URLs?
        );

        pieChart.setBackgroundPaint(new Color(210, 174, 109)); // Chart background color

        // Make the plot background transparent
        pieChart.getPlot().setBackgroundPaint(new Color(0, 0, 0, 0)); // Transparent plot background
        pieChart.getPlot().setOutlinePaint(new Color(0, 0, 0, 0)); // Transparent plot outline

        // Make the chart border transparent
        pieChart.setBorderPaint(new Color(0, 0, 0, 0)); // Transparent border paint

        // Adjust the plot to make it a doughnut chart
        org.jfree.chart.plot.RingPlot plot = (org.jfree.chart.plot.RingPlot) pieChart.getPlot();
        plot.setSectionDepth(0.50); // Adjust depth to create a doughnut effect

        // Set section colors to lighter variants
        int i = 0;
        Color[] colors = {new Color(255, 0, 0), new Color(71, 119, 113), new Color(101, 149, 143), new Color(141, 189, 183)};

        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(442, 340)); // Set preferred size
        jPanel11.removeAll();
        jPanel11.setLayout(new BorderLayout());
        jPanel11.add(chartPanel, BorderLayout.CENTER);
        jPanel11.validate();
        jPanel11.repaint(); // Force repaint
    }

    private void loadCaseToDashoard(String caseNo) {
        DefaultTableModel dtm = (DefaultTableModel) jTable9.getModel();
        dtm.setRowCount(0);
        try {
            ResultSet rs = MySQL.executeSearch("SELECT * FROM `case` INNER JOIN `casetype` ON `case`.`caseType_id` = `casetype`.`id` "
                    + "INNER JOIN `category` ON `case`.`category_id` = `category`.`id` INNER JOIN `casenote` ON `case`.`caseNote_id` = `casenote`.`id` "
                    + "INNER JOIN `testerreports` ON `case`.`testerReports_id` = `testerreports`.`id` "
                    + "INNER JOIN `production` ON `case`.`production_id` = `production`.`id` "
                    + "INNER JOIN `medicalrecords` ON `case`.`medicalRecords_id` = `medicalrecords`.`id`  WHERE `case_no` LIKE '" + caseNo + "%' ");
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("case_no"));
                v.add(rs.getString("category.name"));
                v.add(rs.getString("nextdate"));
                v.add(rs.getString("casetype.name"));
                v.add(rs.getString("transferto"));
                v.add(rs.getString("casenote.status"));
                v.add(rs.getString("testerreports.status"));
                v.add(rs.getString("production.status"));
                v.add(rs.getString("medicalrecords.status"));
                dtm.addRow(v);
            }
            jTable9.setModel(dtm);
        } catch (Exception e) {
            logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
        }

    }

    private void loadFindCasesWithOutNotesWhenFind() {

        DefaultTableModel model = (DefaultTableModel) jTable7.getModel();
        model.setRowCount(0);
        try {
            String query = "SELECT * FROM `case` INNER JOIN `casenote` ON `case`.`caseNote_id` = `casenote`.`id`  WHERE `caseNote_id` = '2' ORDER BY `case`.`nextdate` ASC ";
            ResultSet resultSet = MySQL.executeSearch(query);
            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("case_no"));
                vector.add(resultSet.getString("previousdate"));
                vector.add(resultSet.getString("nextdate"));
                vector.add(resultSet.getString("casenote.status"));

                model.addRow(vector);
            }
            jTable7.setModel(model);

        } catch (Exception e) {
            logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
        }
    }

    private void loadFindCasesWithOutNotes() {

        DefaultTableModel model = (DefaultTableModel) jTable7.getModel();
        model.setRowCount(0);
        try {

            String query = "SELECT * FROM `case` INNER JOIN `casenote` ON `case`.`caseNote_id` = `casenote`.`id`  WHERE `caseNote_id` = '2' ";

            Date start;
            Date end;

            Date d = new Date();

            if (FjDateChooser4.getDate() == null /*&& TjDateChooser3.getDate() == null*/) {
                JOptionPane.showMessageDialog(this, "Please Select a Date to Find", "Warning", JOptionPane.WARNING_MESSAGE);

            } else {
                start = FjDateChooser4.getDate();
//                end = TjDateChooser3.getDate();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String fStart = format.format(start);
//                String fEnd = format.format(end);
//
                query += "AND `nextdate`  = '" + fStart + "' ";

            }

            /* else if (FjDateChooser4.getDate() != null && TjDateChooser3.getDate() == null) {*/
//
//                start = FjDateChooser4.getDate();
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                String fStart = format.format(start);
//
//                query += " AND `nextdate` >= '" + fStart + "'   ";
//
//            } else if (FjDateChooser4.getDate() == null && TjDateChooser3.getDate() != null) {
//
//                end = TjDateChooser3.getDate();
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//
//                String fEnd = format.format(end);
//
//                query += " AND `nextdate` <= '" + fEnd + "' ";
//
//            } else if ((FjDateChooser4.getDate() != null && TjDateChooser3.getDate() != null) && TjDateChooser3.getDate().after(FjDateChooser4.getDate())) {
//
//                start = FjDateChooser4.getDate();
//                end = TjDateChooser3.getDate();
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                String fStart = format.format(start);
//                String fEnd = format.format(end);
//
//                query += "AND `nextdate`  >= '" + fStart + "' AND `nextdate` <= '" + fEnd + "'  ";
//
//            } else {
//                JOptionPane.showMessageDialog(this, "Date Mismatched", "Warning", JOptionPane.WARNING_MESSAGE);
//
//            }
            ResultSet resultSet = MySQL.executeSearch(query);
            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("case_no"));
                vector.add(resultSet.getString("previousdate"));
                vector.add(resultSet.getString("nextdate"));
                vector.add(resultSet.getString("casenote.status"));

                model.addRow(vector);
            }
            jTable7.setModel(model);

        } catch (Exception e) {
            logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
        }

    }

    private void loadCasesWhenFind() {

        DefaultTableModel model = (DefaultTableModel) jTable8.getModel();
        model.setRowCount(0);
        try {
            String query = "SELECT * FROM `case` INNER JOIN `complainant` \n"
                    + "ON `case`.`complainant_id` = `complainant`.`id` INNER JOIN `casetype` \n"
                    + "ON `case`.`caseType_id` = `casetype`.`id` INNER JOIN `category` \n"
                    + "ON `case`.`category_id` = `category`.`id` INNER JOIN `courttype` \n"
                    + "ON `case`.`courtType_id` = `courtType`.`id` INNER JOIN `casenote` \n"
                    + "ON `case`.`caseNote_id` = `casenote`.`id`  ORDER BY `case`.`nextdate` ASC ";
            ResultSet resultSet = MySQL.executeSearch(query);
            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("case_no"));
                vector.add(resultSet.getString("current_date"));
                vector.add(resultSet.getString("previousdate"));
                vector.add(resultSet.getString("nextdate"));
                vector.add(resultSet.getString("complainant.name"));
                vector.add(resultSet.getString("casetype.name"));
                vector.add(resultSet.getString("category.name"));
                vector.add(resultSet.getString("courttype.name"));
                vector.add(resultSet.getString("transferto"));
                vector.add(resultSet.getString("casenote.status"));
                model.addRow(vector);
            }
            jTable8.setModel(model);

        } catch (Exception e) {
            logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
        }
    }

    private void loadCaseToRolBook() {

        DefaultTableModel model = (DefaultTableModel) jTable8.getModel();
        model.setRowCount(0);
        try {

            String query = "SELECT * FROM `case` INNER JOIN `complainant` \n"
                    + "ON `case`.`complainant_id` = `complainant`.`id` INNER JOIN `casetype` \n"
                    + "ON `case`.`caseType_id` = `casetype`.`id` INNER JOIN `category` \n"
                    + "ON `case`.`category_id` = `category`.`id` INNER JOIN `courttype` \n"
                    + "ON `case`.`courtType_id` = `courtType`.`id` INNER JOIN `casenote` \n"
                    + "ON `case`.`caseNote_id` = `casenote`.`id` ";

            Date start;
            Date end;

            Date d = new Date();

            if (jDateChooser1.getDate() == null /*&& jDateChooser2.getDate() == null*/) {
                JOptionPane.showMessageDialog(this, "Please Select a Date to Find", "Warning", JOptionPane.WARNING_MESSAGE);

            } else {

                start = jDateChooser1.getDate();
//                end = jDateChooser2.getDate();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String fStart = format.format(start);
                query += "AND `nextdate`  = '" + fStart + "' AND `nextdate` != 'Ended Case' ";

            }
            /* else if (jDateChooser1.getDate() != null && jDateChooser2.getDate() == null) {*/
//
//                start = jDateChooser1.getDate();
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                String fStart = format.format(start);
//
//                query += " AND `nextdate` >= '" + fStart + "' AND `nextdate` != 'Ended Case' ";
//
//            } else if (jDateChooser1.getDate() == null && jDateChooser2.getDate() != null) {
//
//                end = jDateChooser2.getDate();
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//
//                String fEnd = format.format(end);
//
//                query += " AND `nextdate` <= '" + fEnd + "' AND `nextdate` != 'Ended Case' ";
//
//            } else if ((jDateChooser1.getDate() != null && jDateChooser2.getDate() != null) && jDateChooser2.getDate().after(jDateChooser1.getDate())) {
//
//                start = jDateChooser1.getDate();
//                end = jDateChooser2.getDate();
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                String fStart = format.format(start);
//                String fEnd = format.format(end);
//
//                query += "AND `nextdate`  >= '" + fStart + "' AND `nextdate` <= '" + fEnd + "' AND `nextdate` != 'Ended Case' ";
//
//            } else {
//                JOptionPane.showMessageDialog(this, "Date Mismatched", "Warning", JOptionPane.WARNING_MESSAGE);
//
//            }

            ResultSet resultSet = MySQL.executeSearch(query);
            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("case_no"));
                vector.add(resultSet.getString("current_date"));
                vector.add(resultSet.getString("previousdate"));
                vector.add(resultSet.getString("nextdate"));
                vector.add(resultSet.getString("complainant.name"));
                vector.add(resultSet.getString("casetype.name"));
                vector.add(resultSet.getString("category.name"));
                vector.add(resultSet.getString("courttype.name"));
                vector.add(resultSet.getString("transferto"));
                vector.add(resultSet.getString("casenote.status"));
                model.addRow(vector);
            }
            jTable8.setModel(model);

        } catch (Exception e) {
            logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
        }
    }

//    private double payment = 0;
//    private double total = 0;
//    private double balance = 0;
    private void calculate() {

        String payment = jFormattedTextField2.getText();
        if (payment.equals("")) {
            JOptionPane.showMessageDialog(this, "Missing Payment", "warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {

                int iPayment = Integer.parseInt(payment);
                if (iPayment <= 0) {
                    JOptionPane.showMessageDialog(this, "Invalid Payment", "warning", JOptionPane.WARNING_MESSAGE);
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid Payment", "warning", JOptionPane.WARNING_MESSAGE);
                logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
            }

        }

        double total = Double.parseDouble(jFormattedTextField1.getText());
        double dPayment = Double.parseDouble(payment);

        if (total < 0) {
            JOptionPane.showMessageDialog(this, "Invalid Total amount", "warning", JOptionPane.WARNING_MESSAGE);
        }

        double balance = dPayment - total;

        if (balance == 0) {
            jButton30.setEnabled(true);
        } else {
            jButton30.setEnabled(false);
        }

        jFormattedTextField3.setText(String.valueOf(balance));

    }

    private void loadFinesToInvoice() {

        DefaultTableModel model = (DefaultTableModel) jTable6.getModel();
        model.setRowCount(0);
        double total = 0;

        for (FinesToInvoice fine : fineDetailMap.values()) {
            Vector vector = new Vector();
            vector.add(fine.getFineNum());
            vector.add(fine.getCaseNo());
            vector.add(fine.getRecievedFrom());
            vector.add(fine.getAmount());
            vector.add(fine.getDate());

            double tot = fine.getAmount();
            total += tot;

            model.addRow(vector);

        }
        jFormattedTextField2.setEditable(true);
        jFormattedTextField1.setText(String.valueOf(total));
//        jTable6.setModel(model);
    }

    private void setAdminEmail(String username) {

        try {

            ResultSet rs = MySQL.executeSearch("SELECT * FROM `admin` WHERE `username` = '" + username + "' ");
            if (rs.next()) {
                jTextField20.setText(rs.getString("email"));
                adminHashMap.put(rs.getString("email"), rs.getString("username"));

            } else if (username.equals("kavindu4543@gmail.com")) {
                jTextField20.setText("kavindu4543@gmail.com");

            }

        } catch (Exception e) {
            logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
        }
    }

    private void setDateFine() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fomatDate = dateFormat.format(currentDate);
        jTextField14.setText(fomatDate);

    }

    private void generateFineId() {
        long id = System.currentTimeMillis();
        String FNId = String.valueOf(id);
        jTextField17.setText("FN" + FNId);
        jTextField17.setEditable(false);
    }

    private void generateInvoiceID() {
        long id = System.currentTimeMillis();
        String INVCId = String.valueOf(id);
        jTextField21.setText("INVC" + INVCId);
        jTextField21.setEditable(false);
        jFormattedTextField1.setEditable(false);
    }

    private void loadFinesToTable() {
        jTextField19.setEditable(false);
        jTextField20.setEditable(false);

        try {
            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `fine`");
            DefaultTableModel model = (DefaultTableModel) jTable5.getModel();
            model.setRowCount(0);
            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("fine_no"));
                vector.add(resultSet.getString("caseNo"));
                vector.add(resultSet.getString("receivedFrom"));
                vector.add(resultSet.getString("amount"));
                vector.add(resultSet.getString("date"));
                model.addRow(vector);
            }
            jTable5.setModel(model);
        } catch (Exception e) {
            logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
        }

    }

    private void setDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fomatDate = dateFormat.format(currentDate);
        jTextField10.setText(fomatDate);
        jTextField10.setEditable(false);

    }

    private void checkBox() {
        int row = jTable3.getSelectedRow();

        if (row != -1) {
            if (jCheckBox2.isSelected()) {
                setDate();
                jTextField10.setEditable(false);
            } else {
                String motionDate = String.valueOf(jTable3.getValueAt(row, 1));
                jTextField10.setText(motionDate);
                jTextField10.setEditable(true);

            }

        } else {
            jCheckBox2.setSelected(false);
            loadMotion("");
            jTextField11.setText("");
            jTextField10.setText("");
            JOptionPane.showMessageDialog(this, "Select row", "Warning1", JOptionPane.WARNING_MESSAGE);

        }

    }

    private void loadMotion(String caseNo) {

        try {
            ResultSet rs = MySQL.executeSearch("SELECT * FROM `case` INNER JOIN `courttype` ON `case`.`courtType_id` = `courttype`.`id`  WHERE `case_no` LIKE '" + caseNo + "%' ORDER BY `case_no` ASC  ");
            DefaultTableModel dtm = (DefaultTableModel) jTable3.getModel();
            dtm.setRowCount(0);
            while (rs.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(rs.getString("case_no"));
                vector.add(rs.getString("motiondate"));
                vector.add(rs.getString("nextdate"));
                vector.add(rs.getString("courttype.name"));
                dtm.addRow(vector);

            }

            jTable3.setModel(dtm);

        } catch (Exception e) {
            logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
        }

    }

    private void setMotionUpdate() {

        try {
            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `case`");

            while (resultSet.next()) {
                if (resultSet.getString("motiondate") == null) {

                    MySQL.executeIUD("UPDATE `case` SET `motiondate` = 'Not Updated' ");
                }

            }

        } catch (Exception e) {
            logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
        }

    }

    private void loadMotionPass(String caseNo) {

        try {
            ResultSet rs = MySQL.executeSearch("SELECT * FROM `case` INNER JOIN `courttype` ON `case`.`courtType_id` = `courttype`.`id`  WHERE `case_no` LIKE '" + caseNo + "%' ORDER BY `case_no` ASC  ");
            DefaultTableModel dtm = (DefaultTableModel) jTable4.getModel();
            dtm.setRowCount(0);
            while (rs.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(rs.getString("case_no"));
                vector.add(rs.getString("motiondate"));
                vector.add(rs.getString("nextdate"));
                vector.add(rs.getString("courttype.name"));
                dtm.addRow(vector);

            }

            jTable4.setModel(dtm);

        } catch (Exception e) {
            logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
        }

    }

    private void loadToDayBookCombobox() {

        try {
            ResultSet rs = MySQL.executeSearch("SELECT * FROM `courttype`");
            ResultSet rs2 = MySQL.executeSearch("SELECT * FROM `casetype`");
            ResultSet rs3 = MySQL.executeSearch("SELECT * FROM `casestatus`");
            Vector<String> v = new Vector<>();
            v.add("Select");
            while (rs.next()) {
                v.add(rs.getString("name"));
                courtTypeMap.put(rs.getString("name"), rs.getString("id"));
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(v);
            jComboBox2.setModel(model);

            Vector<String> v2 = new Vector<>();
            v2.add("Select");
            while (rs2.next()) {
                v2.add(rs2.getString("name"));
                CaseTypeMap.put(rs2.getString("name"), rs2.getString("id"));
            }
            DefaultComboBoxModel model2 = new DefaultComboBoxModel(v2);
            jComboBox3.setModel(model2);

            Vector<String> v3 = new Vector<>();
            v3.add("Select");
            while (rs3.next()) {
                v3.add(rs3.getString("status"));
                CaseStatusMap.put(rs3.getString("status"), rs3.getString("id"));
            }
            DefaultComboBoxModel model3 = new DefaultComboBoxModel(v3);
            jComboBox1.setModel(model3);

        } catch (Exception e) {
            logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
        }

    }

    private void setTableContentCenter() {

        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);

        jTable1.setDefaultRenderer(Object.class, render);
        jTable2.setDefaultRenderer(Object.class, render);
        jTable3.setDefaultRenderer(Object.class, render);
        jTable4.setDefaultRenderer(Object.class, render);
        jTable5.setDefaultRenderer(Object.class, render);
        jTable6.setDefaultRenderer(Object.class, render);
        jTable7.setDefaultRenderer(Object.class, render);
        jTable8.setDefaultRenderer(Object.class, render);
        jTable9.setDefaultRenderer(Object.class, render);
    }

    private void loadPlaceHolders() {

        jTextField2.putClientProperty("Jcomponent.roundRect", true);
        jTextField2.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter email here");
        jTextField2.putClientProperty(FlatClientProperties.STYLE, "margin:0,10,0,10");  // top , left  , bottom , right
        jTextField2.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        jTextField1.putClientProperty("Jcomponent.roundRect", true);
        jTextField1.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter mobile here");
        jTextField1.putClientProperty(FlatClientProperties.STYLE, "margin:0,10,0,10");  // top , left  , bottom , right
        jTextField1.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        jTextField4.putClientProperty("Jcomponent.roundRect", true);
        jTextField4.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter first name here");
        jTextField4.putClientProperty(FlatClientProperties.STYLE, "margin:0,10,0,10");  // top , left  , bottom , right
        jTextField4.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        jTextField3.putClientProperty("Jcomponent.roundRect", true);
        jTextField3.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter last name here");
        jTextField3.putClientProperty(FlatClientProperties.STYLE, "margin:0,10,0,10");  // top , left  , bottom , right
        jTextField3.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        jTextField5.putClientProperty("Jcomponent.roundRect", true);
        jTextField5.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter address here");
        jTextField5.putClientProperty(FlatClientProperties.STYLE, "margin:0,10,0,10");  // top , left  , bottom , right
        jTextField5.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        jTextField6.putClientProperty("Jcomponent.roundRect", true);
        jTextField6.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter username here");
        jTextField6.putClientProperty(FlatClientProperties.STYLE, "margin:0,10,0,10");  // top , left  , bottom , right
        jTextField6.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        jPasswordField1.putClientProperty("Jcomponent.roundRect", true);
        jPasswordField1.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter password here");
        jPasswordField1.putClientProperty(FlatClientProperties.STYLE, "margin:0,10,0,10");  // top , left  , bottom , right
        jPasswordField1.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        jTextField7.putClientProperty("Jcomponent.roundRect", true);
        jTextField7.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search by Case Number");
        jTextField7.putClientProperty(FlatClientProperties.STYLE, "margin:0,10,0,10");  // top , left  , bottom , right
        jTextField7.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        jTextField11.putClientProperty("Jcomponent.roundRect", true);
        jTextField11.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Case Number");
        jTextField11.putClientProperty(FlatClientProperties.STYLE, "margin:0,10,0,10");  // top , left  , bottom , right
        jTextField11.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        jTextField10.putClientProperty("Jcomponent.roundRect", true);
        jTextField10.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter motion date");
        jTextField10.putClientProperty(FlatClientProperties.STYLE, "margin:0,10,0,10");  // top , left  , bottom , right
        jTextField10.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        jTextField12.putClientProperty("Jcomponent.roundRect", true);
        jTextField12.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Case Number");
        jTextField12.putClientProperty(FlatClientProperties.STYLE, "margin:0,10,0,10");  // top , left  , bottom , right
        jTextField12.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        jTextField13.putClientProperty("Jcomponent.roundRect", true);
        jTextField13.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter next date");
        jTextField13.putClientProperty(FlatClientProperties.STYLE, "margin:0,10,0,10");  // top , left  , bottom , right
        jTextField13.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        jTextField22.putClientProperty("Jcomponent.roundRect", true);
        jTextField22.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search by Case Number");
        jTextField22.putClientProperty(FlatClientProperties.STYLE, "margin:0,10,0,10");  // top , left  , bottom , right
        jTextField22.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

    }

    private void loadToDaybookTable(String caseId) {

        try {
            ResultSet rs = MySQL.executeSearch("SELECT * FROM `case` INNER JOIN `courttype` ON `case`.`courtType_id` = `courttype`.`id` INNER JOIN"
                    + " `casetype` ON `case`.`caseType_id` = `casetype`.`id` INNER JOIN `casestatus` ON `case`.`caseStatus_id` = `casestatus`.`id` WHERE `case_no` LIKE '" + caseId + "%' ORDER BY `current_date` ASC ");
            DefaultTableModel dtm = (DefaultTableModel) jTable2.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(rs.getString("case_no"));
                vector.add(rs.getString("courttype.name"));
                vector.add(rs.getString("casetype.name"));
                vector.add(rs.getString("casestatus.status"));
                vector.add(rs.getString("current_date"));
                vector.add(rs.getString("previousdate"));
                vector.add(rs.getString("nextdate"));
                dtm.addRow(vector);
            }

            jTable2.setModel(dtm);
        } catch (Exception e) {
            logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
        }

    }
    
    private void setEndedStstatus(){
        
        try {
            MySQL.executeIUD("UPDATE `case` SET `nextdate` = 'Ended Case' WHERE `caseStatus_id` = '2' ");
            
        } catch (Exception e) {
            logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
        }
    
    }
    
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable9 = new javax.swing.JTable();
        jTextField22 = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel16 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel18 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTextField7 = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jButton17 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jTextField23 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jCheckBox2 = new javax.swing.JCheckBox();
        jButton16 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel30 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jButton29 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jTextField20 = new javax.swing.JTextField();
        jTextField21 = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jButton30 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        jLabel39 = new javax.swing.JLabel();
        jButton24 = new javax.swing.JButton();
        FjDateChooser4 = new com.toedter.calendar.JDateChooser();
        jLabel52 = new javax.swing.JLabel();
        jButton35 = new javax.swing.JButton();
        jButton36 = new javax.swing.JButton();
        jLabel54 = new javax.swing.JLabel();
        jButton37 = new javax.swing.JButton();
        jButton38 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable8 = new javax.swing.JTable();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jButton28 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jButton32 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jButton34 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Case Record Management System  |  Hambantota");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(210, 174, 109));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recources/lawBook.png"))); // NOI18N

        jButton10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton10.setText("Dashboard");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jButton10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton10KeyPressed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setText("Admin Management");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton4.setText("New Plaints");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jButton4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton4KeyPressed(evt);
            }
        });

        jButton5.setText("Day Books & Customization");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jButton5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton5KeyPressed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton6.setText("Motion Management");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jButton6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton6KeyPressed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton7.setText("Fines & Invoicing");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton3.setText("Cases With Out Other Recources");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jButton3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton3KeyPressed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton8.setText("Roll Book");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jButton8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton8KeyPressed(evt);
            }
        });

        jButton9.setText("Backup ");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jButton9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton9KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(43, 43, 43))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 720));

        jPanel1.setBackground(new java.awt.Color(53, 27, 2));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable9.setBackground(new java.awt.Color(204, 204, 204));
        jTable9.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Case Number", "Case Category", "Next Date", "Case Type", "Transfer To", "Case Note", "Tester Report", "Production", "Medical Report"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable9.setShowGrid(true);
        jTable9.getTableHeader().setReorderingAllowed(false);
        jScrollPane9.setViewportView(jTable9);

        jPanel1.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 950, 310));

        jTextField22.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField22.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField22KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField22KeyReleased(evt);
            }
        });
        jPanel1.add(jTextField22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 950, 34));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 370, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 230, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 450, 370, 230));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Cases According to Court Type");
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 410, 370, -1));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 370, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 230, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 450, 370, 230));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText(" Cases According to Case Type");
        jLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 410, 370, -1));

        jTabbedPane1.addTab("tab1", jPanel1);

        jPanel4.setBackground(new java.awt.Color(53, 27, 2));

        jLabel4.setBackground(new java.awt.Color(210, 174, 109));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Admin Profile Registeration & Updates");
        jLabel4.setOpaque(true);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("First Name");

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Email");

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Mobile");

        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Last Name");

        jTextField4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Gender");

        jTextField5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Username");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton1.setText("Male");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton2.setText("Female");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Address");

        jTextField6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Password");

        jTable1.setBackground(new java.awt.Color(204, 204, 204));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Full Name", "Mobile", "Username", "Password"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setShowGrid(true);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton12.setBackground(new java.awt.Color(0, 102, 0));
        jButton12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setText("Create Acount");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setBackground(new java.awt.Color(210, 174, 109));
        jButton13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton13.setForeground(new java.awt.Color(0, 0, 0));
        jButton13.setText("Update Profile");
        jButton13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton13MouseClicked(evt);
            }
        });
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setBackground(new java.awt.Color(210, 174, 109));
        jButton14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton14.setForeground(new java.awt.Color(0, 0, 0));
        jButton14.setText("Clear All");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jPasswordField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel18.setBackground(new java.awt.Color(210, 174, 109));
        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Admin Detail Table");
        jLabel18.setOpaque(true);

        jButton2.setText("View");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton2MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton2MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 1021, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField5)
                            .addComponent(jTextField2)
                            .addComponent(jTextField1)
                            .addComponent(jTextField3)
                            .addComponent(jTextField4)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField6)
                            .addComponent(jPasswordField1)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(33, 33, 33)
                                        .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(52, 52, 52)
                                        .addComponent(jRadioButton2))
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2)))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 712, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(92, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
        );

        jRadioButton1.setActionCommand("3");
        jRadioButton2.setActionCommand("4");

        jTabbedPane1.addTab("tab3", jPanel4);

        jPanel7.setBackground(new java.awt.Color(53, 27, 2));

        jTable2.setBackground(new java.awt.Color(204, 204, 204));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Case No", "Court", "Case Type", "Case Status", "Reg Date", "Previous Date", "Next Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setShowGrid(true);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jTextField7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField7KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField7KeyReleased(evt);
            }
        });

        jButton11.setBackground(new java.awt.Color(210, 174, 109));
        jButton11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton11.setForeground(new java.awt.Color(0, 0, 0));
        jButton11.setText("Clear All");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jButton11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton11KeyPressed(evt);
            }
        });

        jButton15.setBackground(new java.awt.Color(210, 174, 109));
        jButton15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton15.setForeground(new java.awt.Color(0, 0, 0));
        jButton15.setText("Update");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jButton15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton15KeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Case status");

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox1KeyPressed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Court Type");

        jComboBox2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox2KeyPressed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Case Type");

        jComboBox3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox3KeyPressed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Previous Date");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Next Date");

        jTextField8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField8KeyPressed(evt);
            }
        });

        jTextField9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField9KeyPressed(evt);
            }
        });

        jLabel24.setBackground(new java.awt.Color(210, 174, 109));
        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 0));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Customization");
        jLabel24.setOpaque(true);

        jLabel25.setBackground(new java.awt.Color(210, 174, 109));
        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Day Books");
        jLabel25.setOpaque(true);

        jButton17.setBackground(new java.awt.Color(102, 102, 102));
        jButton17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton17.setForeground(new java.awt.Color(255, 255, 255));
        jButton17.setText("Category Management");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        jButton17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton17KeyPressed(evt);
            }
        });

        jButton20.setBackground(new java.awt.Color(102, 102, 102));
        jButton20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton20.setForeground(new java.awt.Color(255, 255, 255));
        jButton20.setText("Case Record Transfer Book");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });
        jButton20.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton20KeyPressed(evt);
            }
        });

        jButton21.setBackground(new java.awt.Color(102, 102, 102));
        jButton21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton21.setForeground(new java.awt.Color(255, 255, 255));
        jButton21.setText("Other Updates");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });
        jButton21.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton21KeyPressed(evt);
            }
        });

        jButton18.setBackground(new java.awt.Color(102, 102, 102));
        jButton18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton18.setForeground(new java.awt.Color(255, 255, 255));
        jButton18.setText("Complainant Management");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        jButton18.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton18KeyPressed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Edit Case No.");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox2, 0, 139, Short.MAX_VALUE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox3, 0, 139, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField8, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField9, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 343, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                            .addComponent(jButton17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton20, javax.swing.GroupLayout.DEFAULT_SIZE, 685, Short.MAX_VALUE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap(19, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 1014, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1023, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(74, 74, 74)
                                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(76, 76, 76)
                                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(88, 88, 88))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel25)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel21)
                    .addComponent(jLabel7)
                    .addComponent(jLabel20)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(jLabel24)
                .addGap(26, 26, 26)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(85, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab6", jPanel7);

        jPanel9.setBackground(new java.awt.Color(53, 27, 2));

        jLabel26.setBackground(new java.awt.Color(210, 174, 109));
        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Motion");
        jLabel26.setOpaque(true);

        jTable3.setBackground(new java.awt.Color(204, 204, 204));
        jTable3.setForeground(new java.awt.Color(0, 0, 0));
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Case No", "Motion Date", "Next Date", "Court"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setShowGrid(true);
        jTable3.getTableHeader().setReorderingAllowed(false);
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Case Number");

        jTextField10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField10.setForeground(new java.awt.Color(0, 0, 0));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Next Date");

        jTextField11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField11.setForeground(new java.awt.Color(0, 0, 0));
        jTextField11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField11KeyReleased(evt);
            }
        });

        jCheckBox2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox2.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox2.setText("Mark as motion");
        jCheckBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox2ItemStateChanged(evt);
            }
        });
        jCheckBox2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCheckBox2MouseClicked(evt);
            }
        });

        jButton16.setBackground(new java.awt.Color(210, 174, 109));
        jButton16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton16.setForeground(new java.awt.Color(0, 0, 0));
        jButton16.setText("Clear All");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton19.setBackground(new java.awt.Color(210, 174, 109));
        jButton19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton19.setForeground(new java.awt.Color(0, 0, 0));
        jButton19.setText("Update");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jLabel29.setBackground(new java.awt.Color(210, 174, 109));
        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 0));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Motion Pass");
        jLabel29.setOpaque(true);

        jTable4.setBackground(new java.awt.Color(204, 204, 204));
        jTable4.setForeground(new java.awt.Color(0, 0, 0));
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Case No", "Motion Date", "Next Date", "Court"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable4.setShowGrid(true);
        jTable4.getTableHeader().setReorderingAllowed(false);
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable4MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable4);

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Case Number");

        jTextField12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField12.setForeground(new java.awt.Color(0, 0, 0));
        jTextField12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField12KeyReleased(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Motion Date");

        jTextField13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField13.setForeground(new java.awt.Color(0, 0, 0));
        jTextField13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField13KeyReleased(evt);
            }
        });

        jButton22.setBackground(new java.awt.Color(210, 174, 109));
        jButton22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton22.setForeground(new java.awt.Color(0, 0, 0));
        jButton22.setText("Update");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        jButton23.setBackground(new java.awt.Color(210, 174, 109));
        jButton23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton23.setForeground(new java.awt.Color(0, 0, 0));
        jButton23.setText("Clear All");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1021, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jCheckBox2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel26)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(48, 48, 48)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28))
                        .addGap(24, 24, 24)
                        .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab8", jPanel9);

        jPanel10.setBackground(new java.awt.Color(53, 27, 2));

        jLabel32.setBackground(new java.awt.Color(210, 174, 109));
        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Fines");
        jLabel32.setOpaque(true);

        jTable5.setBackground(new java.awt.Color(204, 204, 204));
        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fine No", "Case No", "Received From", "Amount", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable5.setShowGrid(true);
        jTable5.getTableHeader().setReorderingAllowed(false);
        jTable5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable5MouseClicked(evt);
            }
        });
        jTable5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable5KeyReleased(evt);
            }
        });
        jScrollPane5.setViewportView(jTable5);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Fine Number");

        jTextField14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel34.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("Amount");

        jTextField15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField15KeyReleased(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("Received From");

        jTextField16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("Date");

        jButton25.setBackground(new java.awt.Color(210, 174, 109));
        jButton25.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton25.setForeground(new java.awt.Color(0, 0, 0));
        jButton25.setText("Update");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        jButton26.setBackground(new java.awt.Color(210, 174, 109));
        jButton26.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton26.setForeground(new java.awt.Color(0, 0, 0));
        jButton26.setText("Clear All");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });

        jButton27.setBackground(new java.awt.Color(210, 174, 109));
        jButton27.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton27.setForeground(new java.awt.Color(0, 0, 0));
        jButton27.setText("Add");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        jLabel37.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Case Number");

        jTextField17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jTextField18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel33.setBackground(new java.awt.Color(210, 174, 109));
        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 0));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("Invoice");
        jLabel33.setOpaque(true);

        jTable6.setBackground(new java.awt.Color(204, 204, 204));
        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fine No", "Case No", "Received From", "Amount", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable6.setShowGrid(true);
        jTable6.getTableHeader().setReorderingAllowed(false);
        jTable6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable6MouseClicked(evt);
            }
        });
        jTable6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable6KeyReleased(evt);
            }
        });
        jScrollPane6.setViewportView(jTable6);
        if (jTable6.getColumnModel().getColumnCount() > 0) {
            jTable6.getColumnModel().getColumn(4).setHeaderValue("Date");
        }

        jButton29.setBackground(new java.awt.Color(210, 174, 109));
        jButton29.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton29.setForeground(new java.awt.Color(0, 0, 0));
        jButton29.setText("Clear All");
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Selected Fine Number");

        jTextField19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Admin Email");

        jTextField20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jTextField21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel40.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Invoice Number");

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Total");

        jLabel42.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Balance");

        jLabel43.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("Payment");

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jFormattedTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField1ActionPerformed(evt);
            }
        });

        jFormattedTextField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jFormattedTextField2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jFormattedTextField2MouseClicked(evt);
            }
        });
        jFormattedTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFormattedTextField2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jFormattedTextField2KeyTyped(evt);
            }
        });

        jFormattedTextField3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jFormattedTextField3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jFormattedTextField3MouseClicked(evt);
            }
        });

        jButton30.setBackground(new java.awt.Color(210, 174, 109));
        jButton30.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton30.setForeground(new java.awt.Color(0, 0, 0));
        jButton30.setText("Print Invoice");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton26, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                        .addComponent(jButton27, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel10Layout.createSequentialGroup()
                                                .addGap(24, 24, 24)
                                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField16))
                                .addGap(40, 40, 40))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 1086, Short.MAX_VALUE)
                                .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel10Layout.createSequentialGroup()
                                                .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addGap(100, 100, 100)
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel10Layout.createSequentialGroup()
                                                .addGap(29, 29, 29)
                                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel10Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(jPanel10Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jFormattedTextField1))))
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addGap(184, 184, 184)
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel42, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel43, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jFormattedTextField2)
                                            .addComponent(jFormattedTextField3))))))
                        .addGap(31, 31, 31))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel37))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36)
                            .addComponent(jLabel34))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel33)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel41)
                            .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );

        jTabbedPane1.addTab("tab9", jPanel10);

        jPanel8.setBackground(new java.awt.Color(53, 27, 2));

        jTable7.setBackground(new java.awt.Color(204, 204, 204));
        jTable7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Case No", "Previous Date", "Next Date", "Case Note"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable7.setShowGrid(true);
        jTable7.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(jTable7);

        jLabel39.setBackground(new java.awt.Color(210, 174, 109));
        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 0, 0));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("Cases WithOut Case Notes");
        jLabel39.setOpaque(true);

        jButton24.setBackground(new java.awt.Color(210, 174, 109));
        jButton24.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton24.setForeground(new java.awt.Color(0, 0, 0));
        jButton24.setText("Get Report");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        FjDateChooser4.setDateFormatString("yyyy-MM-dd");
        FjDateChooser4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel52.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("Select Date (Next Date)");

        jButton35.setBackground(new java.awt.Color(210, 174, 109));
        jButton35.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton35.setForeground(new java.awt.Color(0, 0, 0));
        jButton35.setText("Clear All");
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });

        jButton36.setBackground(new java.awt.Color(153, 153, 153));
        jButton36.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton36.setForeground(new java.awt.Color(0, 0, 0));
        jButton36.setText("Cases include Production (Ended)");
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton36ActionPerformed(evt);
            }
        });

        jLabel54.setBackground(new java.awt.Color(210, 174, 109));
        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(0, 0, 0));
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setText("Cases WithOut Case Notes");
        jLabel54.setOpaque(true);

        jButton37.setBackground(new java.awt.Color(210, 174, 109));
        jButton37.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton37.setForeground(new java.awt.Color(0, 0, 0));
        jButton37.setText("Find");
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });

        jButton38.setBackground(new java.awt.Color(153, 153, 153));
        jButton38.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton38.setForeground(new java.awt.Color(0, 0, 0));
        jButton38.setText("Case By Type");
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(177, 177, 177)
                .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(175, 175, 175)
                .addComponent(jButton38, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(208, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(FjDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane7)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(99, 99, 99))))
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addGap(31, 31, 31)
                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 1006, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(93, Short.MAX_VALUE)))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50))
                            .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel52)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FjDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)))
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton38, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(56, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addGap(35, 35, 35)
                    .addComponent(jLabel54)
                    .addContainerGap(668, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("tab7", jPanel8);

        jPanel6.setBackground(new java.awt.Color(53, 27, 2));

        jLabel45.setBackground(new java.awt.Color(210, 174, 109));
        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(0, 0, 0));
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("Rol Book");
        jLabel45.setOpaque(true);

        jTable8.setBackground(new java.awt.Color(204, 204, 204));
        jTable8.setForeground(new java.awt.Color(0, 0, 0));
        jTable8.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Case No", "RD", "PD", "ND", "Complainant", "Case Type", "Category", "Court Type", "Transfer To", "Case Note"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable8.setShowGrid(true);
        jTable8.getTableHeader().setReorderingAllowed(false);
        jTable8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable8MouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(jTable8);
        if (jTable8.getColumnModel().getColumnCount() > 0) {
            jTable8.getColumnModel().getColumn(5).setHeaderValue("Case Type");
            jTable8.getColumnModel().getColumn(6).setHeaderValue("Category");
            jTable8.getColumnModel().getColumn(7).setHeaderValue("Court Type");
            jTable8.getColumnModel().getColumn(8).setHeaderValue("Transfer To");
            jTable8.getColumnModel().getColumn(9).setHeaderValue("Case Note");
        }

        jDateChooser1.setDateFormatString("yyyy-MM-dd");
        jDateChooser1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Select Date   (ND)");

        jButton28.setBackground(new java.awt.Color(210, 174, 109));
        jButton28.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton28.setForeground(new java.awt.Color(0, 0, 0));
        jButton28.setText("Clear All");
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });

        jButton31.setBackground(new java.awt.Color(210, 174, 109));
        jButton31.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton31.setForeground(new java.awt.Color(0, 0, 0));
        jButton31.setText("Find");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });

        jLabel47.setBackground(new java.awt.Color(210, 174, 109));
        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(0, 0, 0));
        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel47.setText("RD - Registered Date");
        jLabel47.setOpaque(true);

        jLabel48.setBackground(new java.awt.Color(210, 174, 109));
        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(0, 0, 0));
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("PD - Previous Date");
        jLabel48.setOpaque(true);

        jLabel49.setBackground(new java.awt.Color(210, 174, 109));
        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(0, 0, 0));
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("ND - Next Date");
        jLabel49.setOpaque(true);

        jButton32.setBackground(new java.awt.Color(210, 174, 109));
        jButton32.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton32.setForeground(new java.awt.Color(0, 0, 0));
        jButton32.setText("Get Roll Book Report");
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(68, 68, 68)
                        .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(150, 150, 150))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 993, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 1020, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(96, 96, 96))))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130)
                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(142, 142, 142)
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel45)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(62, 62, 62))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGap(40, 40, 40)
                                        .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGap(50, 50, 50)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(60, 60, 60)))
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab5", jPanel6);

        jPanel5.setBackground(new java.awt.Color(53, 27, 2));

        jLabel51.setBackground(new java.awt.Color(210, 174, 109));
        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(0, 0, 0));
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setText("Backup");
        jLabel51.setOpaque(true);

        jButton34.setBackground(new java.awt.Color(102, 102, 102));
        jButton34.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton34.setForeground(new java.awt.Color(255, 255, 255));
        jButton34.setText("Backup");
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 972, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(418, 418, 418)
                        .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(533, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab4", jPanel5);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, -40, 1130, 760));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed

        String email = jTextField2.getText();
        String mobile = jTextField1.getText();
        String firstName = jTextField4.getText();
        String lastName = jTextField3.getText();
        String address = jTextField5.getText();
        ButtonModel gender = buttonGroup1.getSelection();
        String username = jTextField6.getText();
        String password = String.valueOf(jPasswordField1.getPassword());

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter your Email", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!email.matches("^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@gmail\\.com$")) {
            JOptionPane.showMessageDialog(this, "Please Enter Valid Email", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (mobile.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Mobile Number", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!mobile.matches("^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$")) {
            JOptionPane.showMessageDialog(this, "Please Enter Valid Mobile Number", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (firstName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter First Name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Last Name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Address", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (gender == null) {
            JOptionPane.showMessageDialog(this, "Please Select Gender", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Username", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (username.length() > 20) {
            JOptionPane.showMessageDialog(this, "Username is Too Long , Characters Should be less than 20", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Password", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (password.length() > 20 || password.length() < 8) {
            JOptionPane.showMessageDialog(this, "Password Should be between 8 - 20 Characters", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!password.matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=-{};:'<>,./?]).{8,}$")) {
            JOptionPane.showMessageDialog(this, "Password Should Only Contain Letters , Numbers and Special Characters", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {

                ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `admin` WHERE `email` = '" + email + "' OR `mobile` = '" + mobile + "' ");
                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Admin Email or Password Already Exists, Please Use a Different Email or Password", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {

                    String genderId = gender.getActionCommand();
                    MySQL.executeIUD("INSERT INTO `admin` (`email`  ,`mobile` , `firstname` , `lastname` ,`address` , `gender_id` , `username` , `password` ) VALUES"
                            + " ('" + email + "' , '" + mobile + "' , '" + firstName + "' , '" + lastName + "' , '" + address + "' , '" + genderId + "' , '" + username + "' , '" + password + "') ");
                    JOptionPane.showMessageDialog(this, "Admin Account Successfully Created", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadAdmins();
                    reset();

                }

            } catch (Exception e) {
                logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
            }

        }


    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        reset();

    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MousePressed
        jPasswordField1.setEchoChar('\u0000');

    }//GEN-LAST:event_jButton2MousePressed

    private void jButton2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseReleased
        jPasswordField1.setEchoChar('\u2022');
    }//GEN-LAST:event_jButton2MouseReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        jButton12.setEnabled(false);
        int row = jTable1.getSelectedRow();
        String mobile = String.valueOf(jTable1.getValueAt(row, 1));
        try {
            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `admin` WHERE `mobile` = '" + mobile + "'  ");

            if (resultSet.next()) {
                jTextField2.setEditable(false);
                jTextField2.setText(resultSet.getString("email"));
                jTextField1.setText(resultSet.getString("mobile"));
                jTextField4.setText(resultSet.getString("firstname"));
                jTextField3.setText(resultSet.getString("lastname"));
                jTextField5.setText(resultSet.getString("address"));
                if (resultSet.getString("gender_id").equals("3")) {
                    jRadioButton1.setSelected(true);
                } else {
                    jRadioButton2.setSelected(true);
                }

                jTextField6.setText(resultSet.getString("username"));
                jPasswordField1.setText(resultSet.getString("password"));

            }
        } catch (Exception e) {
            logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton13MouseClicked


    }//GEN-LAST:event_jButton13MouseClicked

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed

        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please Select a Row", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            String email = jTextField2.getText();
            String mobile = jTextField1.getText();
            String firstName = jTextField4.getText();
            String lastName = jTextField3.getText();
            String address = jTextField5.getText();
            ButtonModel gender = buttonGroup1.getSelection();
            String username = jTextField6.getText();
            String password = String.valueOf(jPasswordField1.getPassword());

            String selectedMobile = String.valueOf(jTable1.getValueAt(row, 1));
            String selectedUsername = String.valueOf(jTable1.getValueAt(row, 2));
            String selectedPassword = String.valueOf(jTable1.getValueAt(row, 3));

            if (mobile.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Enter Mobile Number", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (!mobile.matches("^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$")) {
                JOptionPane.showMessageDialog(this, "Please Enter Valid Mobile Number", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (firstName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Enter First Name", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (lastName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Enter Last Name", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (address.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Enter Address", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (gender == null) {
                JOptionPane.showMessageDialog(this, "Please Select Gender", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Enter Username", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (username.length() > 20) {
                JOptionPane.showMessageDialog(this, "Username is Too Long , Characters Should be less than 20", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Enter Password", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (password.length() > 20 || password.length() < 8) {
                JOptionPane.showMessageDialog(this, "Password Should be between 8 - 20 Characters", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (!password.matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=-{};:'<>,./?]).{8,}$")) {
                JOptionPane.showMessageDialog(this, "Password Should Only Contain Letters , Numbers and Special Characters", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (mobile.equals(selectedMobile) && username.equals(selectedUsername) && password.equals(selectedPassword)) {
                JOptionPane.showMessageDialog(this, "Please Change Mobile , Username or Password", "Warning", JOptionPane.WARNING_MESSAGE);

            } else {

                try {

                    ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `admin` WHERE `username` = '" + username + "' OR `mobile` = '" + mobile + "' OR `password` = '" + password + "' ");
                    boolean canUpdate = false;

                    if (resultSet.next()) {

                        if ((resultSet.getString("email").equals(email))) {
                            canUpdate = true;

                        } else {
                            JOptionPane.showMessageDialog(this, "Username , Password or Mobile already used", "Warning", JOptionPane.WARNING_MESSAGE);

                        }
                    } else {
                        canUpdate = true;
                    }

                    if (canUpdate) {
                        String genderId = gender.getActionCommand();
                        MySQL.executeIUD(" UPDATE `admin` SET `mobile` = '" + mobile + "' , `firstname` = '" + firstName + "' , `lastname` = '" + lastName + "' "
                                + ", `address` = '" + address + "' , `gender_id` = '" + genderId + "' , `username` = '" + username + "' , `password` = '" + password + "' WHERE `email` = '" + email + "' ");
                        JOptionPane.showMessageDialog(this, "Admin Account Successfully Updated", "Success", JOptionPane.INFORMATION_MESSAGE);
                        loadAdmins();
                        reset();
                    }

                } catch (Exception e) {
                    logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
                }

            }

        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jTextField7KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField7KeyReleased
        loadToDaybookTable(jTextField7.getText());


    }//GEN-LAST:event_jTextField7KeyReleased

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        resetDayBooks();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked

//        jTextField7.setEditable(false);
        jComboBox1.setEnabled(true);
        int row = jTable2.getSelectedRow();

        jTextField7.setText("");
        String caseNo = String.valueOf(jTable2.getValueAt(row, 0));
        jTextField23.setText(caseNo);

        String court = String.valueOf(jTable2.getValueAt(row, 1));
        jComboBox2.setSelectedItem(court);

        String caseType = String.valueOf(jTable2.getValueAt(row, 2));
        jComboBox3.setSelectedItem(caseType);

        String casestatus = String.valueOf(jTable2.getValueAt(row, 3));
        jComboBox1.setSelectedItem(casestatus);

        String preDate = String.valueOf(jTable2.getValueAt(row, 4));
        jTextField8.setText(preDate);

        String nextDate = String.valueOf(jTable2.getValueAt(row, 5));
        jTextField9.setText(nextDate);


    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed

        if (jTable2.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to update", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            String caseNo = jTextField23.getText();
            String courtType = String.valueOf(jComboBox2.getSelectedItem());
            String caseType = String.valueOf(jComboBox3.getSelectedItem());
            String caseStatus = String.valueOf(jComboBox1.getSelectedItem());
            String preDate = String.valueOf(jTextField8.getText());
            String nextDate = String.valueOf(jTextField9.getText());

            if (courtType.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please select court type", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (caseType.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please select case type", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (caseStatus.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please select case status", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (preDate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter previous date", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (nextDate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter next date", "Warning", JOptionPane.WARNING_MESSAGE);

            } else {
                int row = jTable2.getSelectedRow();
                String getCaseNo = String.valueOf(jTable2.getValueAt(row, 0));
                String getcourtType = String.valueOf(jTable2.getValueAt(row, 1));
                String getCasetType = String.valueOf(jTable2.getValueAt(row, 2));
                String getcaseStatus = String.valueOf(jTable2.getValueAt(row, 3));
                String getPreDate = String.valueOf(jTable2.getValueAt(row, 4));
                String getNextDate = String.valueOf(jTable2.getValueAt(row, 5));

                if (getCaseNo.equals(caseNo) && getcourtType.equals(courtType) && getCasetType.equals(caseType) && getcaseStatus.equals(caseStatus) && getPreDate.equals(preDate) && getNextDate.equals(nextDate)) {
                    JOptionPane.showMessageDialog(this, "Details already exists", "Warning", JOptionPane.WARNING_MESSAGE);
                    resetDayBooks();

                } else {

                    if (caseStatus.equals("Ended") || caseType.equals("Ended Case")) {

                        nextDate = "Ended Case";
                        caseStatus = "Ended";
                    }

                    try {
                        MySQL.executeIUD("UPDATE `case` SET `case_no` = '" + caseNo + "', `courtType_id` = '" + courtTypeMap.get(courtType) + "',`caseType_id` = '" + CaseTypeMap.get(caseType) + "' , `caseStatus_id` = '" + CaseStatusMap.get(caseStatus) + "' ,`previousdate` = '" + preDate + "' , `nextdate` = '" + nextDate + "' WHERE `case_no` = '" + getCaseNo + "' ");
                        JOptionPane.showMessageDialog(this, "Day Books updated Success", "Success", JOptionPane.INFORMATION_MESSAGE);
                        resetDayBooks();
                        loadToDaybookTable("");
                        loadToDayBookCombobox();


                    } catch (Exception e) {
                        logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
                    }

                }

            }

        }

    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        CategoryManagement categoryManagement = new CategoryManagement(this, true);
        categoryManagement.setVisible(true);
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        ComplainantManagement comMananage = new ComplainantManagement(this, true);
        comMananage.setVisible(true);
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        OtherUpdates otherUpdates = new OtherUpdates(this, true);
        otherUpdates.setVisible(true);
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        jTabbedPane1.setSelectedIndex(7);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        jTabbedPane1.setSelectedIndex(6);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jTabbedPane1.setSelectedIndex(5);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

        jTabbedPane1.setSelectedIndex(4);
        generateFineId();
        jTextField20.setEditable(false);
        generateInvoiceID();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        jTabbedPane1.setSelectedIndex(3);
        setMotionUpdate();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        jTabbedPane1.setSelectedIndex(2);
     
        setEndedStstatus();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        NewPlaintsRegisteration newPainReg = new NewPlaintsRegisteration(this, true);
        newPainReg.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTabbedPane1.setSelectedIndex(1);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        jTabbedPane1.setSelectedIndex(0);

    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        CaseTransferBook caseTansferBook = new CaseTransferBook(this, true);
        caseTansferBook.setVisible(true);
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked

        int row = jTable3.getSelectedRow();
        String caseNo = String.valueOf(jTable3.getValueAt(row, 0));
        String motionDate = String.valueOf(jTable3.getValueAt(row, 1));
        jTextField10.setText(motionDate);
        jTextField11.setText(caseNo);
    }//GEN-LAST:event_jTable3MouseClicked

    private void jCheckBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox2ItemStateChanged


    }//GEN-LAST:event_jCheckBox2ItemStateChanged

    private void jTextField11KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField11KeyReleased
        loadMotion(jTextField11.getText());
    }//GEN-LAST:event_jTextField11KeyReleased

    private void jCheckBox2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCheckBox2MouseClicked
        checkBox();
    }//GEN-LAST:event_jCheckBox2MouseClicked

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        motionReset();

    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        int row = jTable3.getSelectedRow();

        String caseNo = String.valueOf(jTextField11.getText());
        String motionDate = String.valueOf(jTextField10.getText());

        if (caseNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter case number", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (motionDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter motion date", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `case` WHERE `case_no` = '" + caseNo + "' ");

                if (resultSet.next()) {

                    String getCaseNo = String.valueOf(jTable3.getValueAt(row, 0));
                    String getMotionDate = String.valueOf(jTable3.getValueAt(row, 1));

                    if (caseNo.equals(getCaseNo) && motionDate.equals(getMotionDate)) {
                        JOptionPane.showMessageDialog(this, "Motion details already exists", "Warning", JOptionPane.WARNING_MESSAGE);
                        motionReset();
                        loadMotion("");
                    } else {

                        MySQL.executeIUD(" UPDATE `case` SET `motiondate`  = '" + motionDate + "' WHERE `case_no` = '" + caseNo + "' ");
                        motionReset();
                        loadMotion("");
                        JOptionPane.showMessageDialog(this, "Motion date updated successfull", "Succss", JOptionPane.INFORMATION_MESSAGE);

                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Case not registered", "Warning", JOptionPane.WARNING_MESSAGE);
                    loadMotion("");
                    motionReset();
                }

            } catch (Exception e) {
                logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
            }

        }
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
        int row = jTable4.getSelectedRow();
        String caseNo = String.valueOf(jTable4.getValueAt(row, 0));
        String nextDate = String.valueOf(jTable4.getValueAt(row, 2));
        jTextField13.setText(nextDate);
        jTextField12.setText(caseNo);
    }//GEN-LAST:event_jTable4MouseClicked

    private void jTextField12KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField12KeyReleased
        loadMotionPass(jTextField12.getText());
    }//GEN-LAST:event_jTextField12KeyReleased

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed

        int row = jTable4.getSelectedRow();

        String caseNo = String.valueOf(jTextField12.getText());
        String nextDate = String.valueOf(jTextField13.getText());

        if (caseNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter case number", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (nextDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter motion date", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `case` WHERE `case_no` = '" + caseNo + "' ");

                if (resultSet.next()) {

                    String getCaseNo = String.valueOf(jTable3.getValueAt(row, 0));
                    String getMotionDate = String.valueOf(jTable3.getValueAt(row, 2));

                    if (caseNo.equals(getCaseNo) && nextDate.equals(getMotionDate)) {
                        JOptionPane.showMessageDialog(this, "Motion details already exists", "Warning", JOptionPane.WARNING_MESSAGE);
                        motionPassReset();
                        loadMotionPass("");
                    } else {

                        MySQL.executeIUD(" UPDATE `case` SET `nextdate`  = '" + nextDate + "' WHERE `case_no` = '" + caseNo + "' ");
                        motionPassReset();
                        loadMotionPass("");
                        JOptionPane.showMessageDialog(this, "Next date updated successfull", "Succss", JOptionPane.INFORMATION_MESSAGE);

                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Case not registered", "Warning", JOptionPane.WARNING_MESSAGE);
                    loadMotionPass("");
                    motionPassReset();
                }

            } catch (Exception e) {
                logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
            }

        }
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        motionPassReset();
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jTextField13KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField13KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField13KeyReleased

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        String fineNum = String.valueOf(jTextField17.getText());
        String caseNo = String.valueOf(jTextField15.getText());
        String recievedFrom = String.valueOf(jTextField16.getText());
        String date = String.valueOf(jTextField14.getText());

        if (fineNum.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter fine number", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (caseNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter case number", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (recievedFrom.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter reciever name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (date.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter date", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (jTextField18.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please enter amount  amount", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!jTextField18.getText().matches("^[0-9]+(?:\\.[0-9]+)?$")) {
            JOptionPane.showMessageDialog(this, "Please enter valid  amount", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            int row = jTable5.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a row", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {

                String getFineNo = String.valueOf(jTable5.getValueAt(row, 0));
                String getCaseNo = String.valueOf(jTable5.getValueAt(row, 1));
                String getReceivedFrom = String.valueOf(jTable5.getValueAt(row, 2));
                String getAmount = String.valueOf(jTable5.getValueAt(row, 3));
                String getDate = String.valueOf(jTable5.getValueAt(row, 4));

                if (getFineNo.equals(fineNum) && getCaseNo.equals(caseNo) && getReceivedFrom.equals(recievedFrom) && getAmount.equals(jTextField18.getText()) && getDate.equals(date)) {
                    JOptionPane.showMessageDialog(this, "Fine details same please change", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {

                    try {
                        ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `fine` WHERE `fine_no` = '" + jTextField17.getText() + "' ");
                        if (resultSet.next()) {
                            ResultSet rs = MySQL.executeSearch("SELECT * FROM `case` WHERE `case_no` = '" + caseNo + "' ");
                            if (rs.next()) {
                                MySQL.executeIUD("UPDATE `fine` SET  `caseNo` = '" + caseNo + "'  ,`receivedFrom` = '" + recievedFrom + "' , `amount` = '" + jTextField18.getText() + "' , `date` = '" + date + "' "
                                        + "WHERE `fine_no` = '" + fineNum + "'   ");
                                JOptionPane.showMessageDialog(this, "Fine update successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                                resetFines();
                                loadFinesToTable();
                            } else {
                                JOptionPane.showMessageDialog(this, "Case not registered", "Warning", JOptionPane.WARNING_MESSAGE);

                            }

                        } else {

                            JOptionPane.showMessageDialog(this, "Fine does not exists", "Warning", JOptionPane.WARNING_MESSAGE);

                        }
                    } catch (Exception e) {
                        logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
                    }

                }

            }

        }
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jTextField15KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField15KeyReleased

    }//GEN-LAST:event_jTextField15KeyReleased

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed

        String fineNum = String.valueOf(jTextField17.getText());
        String caseNo = String.valueOf(jTextField15.getText());
        String recievedFrom = String.valueOf(jTextField16.getText());
        String date = String.valueOf(jTextField14.getText());

        if (fineNum.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter fine number", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (caseNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter case number", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (recievedFrom.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter reciever name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (date.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter date", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (jTextField18.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please enter amount  amount", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!jTextField18.getText().matches("^[0-9]+(?:\\.[0-9]+)?$")) {
            JOptionPane.showMessageDialog(this, "Please enter valid  amount", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `fine` WHERE `fine_no` = '" + jTextField17.getText() + "' ");
                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Fine already Exists", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {

                    ResultSet rs = MySQL.executeSearch("SELECT * FROM `case` WHERE `case_no` = '" + caseNo + "' ");
                    if (rs.next()) {
                        MySQL.executeIUD("INSERT INTO `fine` (`fine_no` , `caseNo` , `receivedFrom` , `amount` , `date`) "
                                + "VALUES ('" + fineNum + "' , '" + caseNo + "' , '" + recievedFrom + "' ,  '" + jTextField18.getText() + "', '" + date + "' ) ");
                        JOptionPane.showMessageDialog(this, "Fine registered successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                        resetFines();
                        loadFinesToTable();
                    } else {
                        JOptionPane.showMessageDialog(this, "Case not registered", "Warning", JOptionPane.WARNING_MESSAGE);

                    }

                }
            } catch (Exception e) {
                logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
            }

        }
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jTable5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable5MouseClicked

        int row = jTable5.getSelectedRow();
        String fineNo = String.valueOf(jTable5.getValueAt(row, 0));
        String caseNo = String.valueOf(jTable5.getValueAt(row, 1));
        String receivedFrom = String.valueOf(jTable5.getValueAt(row, 2));
        String amount = String.valueOf(jTable5.getValueAt(row, 3));
        String date = String.valueOf(jTable5.getValueAt(row, 4));

        jTextField17.setText(fineNo);
        jTextField15.setText(caseNo);
        jTextField16.setText(receivedFrom);
        jTextField18.setText(amount);
        jTextField14.setText(date);

        if (evt.getClickCount() == 2) {

            int rowCount = jTable6.getRowCount();

            if (rowCount != 0) {

                JOptionPane.showMessageDialog(this, "Only one fine can be added", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {

                jTextField19.setText(fineNo);
                jTextField19.setEditable(false);

                FinesToInvoice fineDetails = new FinesToInvoice();
                fineDetails.setFineNum(fineNo);
                fineDetails.setCaseNo(caseNo);
                fineDetails.setRecievedFrom(receivedFrom);
                fineDetails.setAmount(Double.parseDouble(amount));
                fineDetails.setDate(date);

                if (fineDetailMap.get(caseNo) == null) {
                    fineDetailMap.put(caseNo, fineDetails);
                    loadFinesToInvoice();
                } else {
                    JOptionPane.showMessageDialog(this, "Fine already have added", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }

        }


    }//GEN-LAST:event_jTable5MouseClicked

    private void jTable5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable5KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable5KeyReleased

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        resetFines();
        loadFinesToTable();
        generateFineId();
        setDateFine();
        jTextField19.setText("");
    }//GEN-LAST:event_jButton26ActionPerformed

    private void jTable6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable6KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable6KeyReleased

    private void jTable6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable6MouseClicked

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        generateInvoiceID();

        if (fineDetailMap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No fines to clear", "Warning", JOptionPane.WARNING_MESSAGE);
            jFormattedTextField1.setText("");
            jFormattedTextField2.setText("");
            jFormattedTextField3.setText("");
            jFormattedTextField2.setEditable(false);
        } else {

            int option = JOptionPane.showConfirmDialog(this, "Do you want to clear fines in invoice table ", "Messege", JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (option == JOptionPane.YES_OPTION) {
                fineDetailMap.values().clear();
                jTextField19.setText("");
                jFormattedTextField1.setText("");
                jFormattedTextField2.setText("");
                jFormattedTextField3.setText("");
                loadFinesToInvoice();
                jFormattedTextField2.setEditable(false);

            }
        }


    }//GEN-LAST:event_jButton29ActionPerformed

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        if (fineDetailMap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No fines in invoice table to print", "Warning", JOptionPane.WARNING_MESSAGE);

        } else {

            try {
                String invoiceNo = String.valueOf(jTextField21.getText());

                for (FinesToInvoice fine : fineDetailMap.values()) {

                    MySQL.executeIUD("INSERT INTO `invoice` (`invoiceNo` , `date` , `amount` , `fine_fine_no` , `caseNo`) "
                            + "VALUES ('" + invoiceNo + "' , '" + fine.getDate() + "' , '" + fine.getAmount() + "' , '" + fine.getFineNum() + "' , '" + fine.getCaseNo() + "' )  ");

                    String path = "src/reports/SAD1.jasper";

                    HashMap<String, Object> p = new HashMap<>();

                    try {
                        JRTableModelDataSource mds = new JRTableModelDataSource(jTable6.getModel());
                        JasperPrint jasperPrint = JasperFillManager.fillReport(path, p, mds);
//                        JasperPrint jp = JasperFillManager.fillReport(path,mds);
                        JasperViewer jasperViwer = new JasperViewer(jasperPrint, false);
                        jasperViwer.setVisible(true);
                    } catch (Exception e) {
                        logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
                    }

                    fineDetailMap.values().clear();
                    jTextField19.setText("");
                    jFormattedTextField1.setText("");
                    jFormattedTextField2.setText("");
                    jFormattedTextField3.setText("");
                    jButton30.setEnabled(false);
                    loadFinesToInvoice();
                    jFormattedTextField2.setEditable(false);

                }
            } catch (Exception e) {
                logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
            }

        }
    }//GEN-LAST:event_jButton30ActionPerformed

    private void jFormattedTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField2KeyReleased

        calculate();
    }//GEN-LAST:event_jFormattedTextField2KeyReleased

    private void jFormattedTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField2KeyTyped

    private void jFormattedTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField1ActionPerformed

    private void jTable8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable8MouseClicked

    }//GEN-LAST:event_jTable8MouseClicked

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        loadCaseToRolBook();
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        resetRolBook();
        loadCasesWhenFind();
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jFormattedTextField3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFormattedTextField3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField3MouseClicked

    private void jFormattedTextField2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFormattedTextField2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField2MouseClicked

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed

        String path = "src/reports/SAD26.jasper";
        HashMap<String, Object> parameters = new HashMap<>();

        try {

            JRTableModelDataSource dataSource = new JRTableModelDataSource(jTable8.getModel());

            JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters, dataSource);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (Exception e) {
            logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
        }
    }//GEN-LAST:event_jButton32ActionPerformed

    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String fomatDate = dateFormat.format(currentDate);

        try {
            String command = "\"" + MYSQLDUMP_PATH + "\\mysqldump\" -uroot -pKavindu@dilshan --add-drop-database courtcase -r \"" + BACKUP_DIRECTORY + "\\" + fomatDate + "_courtcase.sql\"";
            Process process = Runtime.getRuntime().exec(command);
            int waitFor = process.waitFor();
            if (waitFor == 0) {
//                System.out.println("Backup Successful");
                JOptionPane.showMessageDialog(this, "Backup Successful", "Success", JOptionPane.INFORMATION_MESSAGE);

            } else {
                InputStream errorStream = process.getErrorStream();
                String error = new String(errorStream.readAllBytes());
                JOptionPane.showMessageDialog(this, "Backup Failed", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }//GEN-LAST:event_jButton34ActionPerformed

    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton35ActionPerformed

        FjDateChooser4.setDate(null);
//        TjDateChooser3.setDate(null);
        loadFindCasesWithOutNotesWhenFind();


    }//GEN-LAST:event_jButton35ActionPerformed

    private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton36ActionPerformed
        CasesIncludeProductionNotEnded CIP = new CasesIncludeProductionNotEnded();
        CIP.setVisible(true);
    }//GEN-LAST:event_jButton36ActionPerformed

    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton37ActionPerformed
        loadFindCasesWithOutNotes();
    }//GEN-LAST:event_jButton37ActionPerformed

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        CasesByType CBT = new CasesByType();
        CBT.setVisible(true);
    }//GEN-LAST:event_jButton38ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed

//        DefaultTableModel jTable7Model = (DefaultTableModel) jTable7.getModel();
//        String[] columnNames = new String[jTable7Model.getColumnCount()];
//        for (int i = 0; i < jTable7Model.getColumnCount(); i++) {
//            columnNames[i] = jTable7Model.getColumnName(i);
//        }
//        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
//
//        for (int i = 0; i < jTable7Model.getRowCount(); i++) {
//            Object[] row = new Object[jTable7Model.getColumnCount()];
//            for (int j = 0; j < jTable7Model.getColumnCount(); j++) {
//                row[j] = jTable7Model.getValueAt(i, j);
//            }
//            tableModel.addRow(row);
//        }
        String path = "src/reports/SAD23.jasper";
        HashMap<String, Object> parameters = new HashMap<>();

        try {

            JRTableModelDataSource dataSource = new JRTableModelDataSource(jTable7.getModel());

//                dataSource.setFieldMapper(new FieldMapper() {
//                    public String getFieldName(int columnIndex) {
//                        switch (columnIndex) {
//                            case 0:
//                                return "COLUMN_0";
//                            case 1:
//                                return "COLUMN_1";
//                            case 2:
//                                return "COLUMN_2";
//                            default:
//                                return null;
//                        }
//                    }
//                });
            JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters, dataSource);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (Exception e) {
            logger.log(java.util.logging.Level.WARNING, "Exception Occured", e);
        }


    }//GEN-LAST:event_jButton24ActionPerformed

    private void jTextField22KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField22KeyReleased
        loadCaseToDashoard(jTextField22.getText());
    }//GEN-LAST:event_jTextField22KeyReleased

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
       
       

//            if (jComboBox1.getSelectedIndex() == 2) {
//                jTextField9.setText("Ended Case");
//            } else if (jComboBox1.getSelectedIndex() == 1 || jComboBox1.getSelectedIndex() == 0) {
//                int row = jTable2.getSelectedRow();
//                String nextDate = String.valueOf(jTable2.getValueAt(row, 5));
//                jTextField9.setText(nextDate);
//            }

        

    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jButton10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton10KeyPressed
       if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            jButton1.grabFocus();
        }else if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
            jTextField22.grabFocus();
        }
    }//GEN-LAST:event_jButton10KeyPressed

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
       if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            jButton4.grabFocus();
        }else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            jButton10.grabFocus();
        }
    }//GEN-LAST:event_jButton1KeyPressed

    private void jButton4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton4KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            jButton5.grabFocus();
        }else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            jButton1.grabFocus();
        }
    }//GEN-LAST:event_jButton4KeyPressed

    private void jButton5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton5KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            jButton6.grabFocus();
        }else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            jButton4.grabFocus();
        }else if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
            jTextField7.grabFocus();
        }
    }//GEN-LAST:event_jButton5KeyPressed

    private void jButton6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton6KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            jButton3.grabFocus();
        }else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            jButton5.grabFocus();
        }
    }//GEN-LAST:event_jButton6KeyPressed

    private void jButton3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton3KeyPressed
         if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            jButton8.grabFocus();
        }else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            jButton6.grabFocus();
        }
    }//GEN-LAST:event_jButton3KeyPressed

    private void jButton8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton8KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            jButton9.grabFocus();
        }else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            jButton3.grabFocus();
        }
    }//GEN-LAST:event_jButton8KeyPressed

    private void jButton9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton9KeyPressed
       if (evt.getKeyCode() == KeyEvent.VK_UP) {
            jButton8.grabFocus();
        }
    }//GEN-LAST:event_jButton9KeyPressed

    private void jTextField22KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField22KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
            jButton10.grabFocus();
        }
    }//GEN-LAST:event_jTextField22KeyPressed

    private void jTextField7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField7KeyPressed
       if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
            jButton5.grabFocus();
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN) {
            jComboBox2.grabFocus();
        }
    }//GEN-LAST:event_jTextField7KeyPressed

    private void jComboBox2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jComboBox3.grabFocus();
        }else if(evt.getKeyCode() == KeyEvent.VK_LEFT) {
            jTextField7.grabFocus();
        }
    }//GEN-LAST:event_jComboBox2KeyPressed

    private void jComboBox3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox3KeyPressed
       if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jComboBox1.grabFocus();
        }else if(evt.getKeyCode() == KeyEvent.VK_LEFT) {
            jComboBox2.grabFocus();
        }
    }//GEN-LAST:event_jComboBox3KeyPressed

    private void jComboBox1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox1KeyPressed
      
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jTextField8.grabFocus();
        }else if(evt.getKeyCode() == KeyEvent.VK_LEFT) {
            jComboBox3.grabFocus();
        }
    }//GEN-LAST:event_jComboBox1KeyPressed

    private void jTextField8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField8KeyPressed
         if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jTextField9.grabFocus();
        }else if(evt.getKeyCode() == KeyEvent.VK_LEFT) {
            jComboBox1.grabFocus();
        }
    }//GEN-LAST:event_jTextField8KeyPressed

    private void jTextField9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField9KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jButton15.grabFocus();
        }else if(evt.getKeyCode() == KeyEvent.VK_LEFT) {
            jTextField8.grabFocus();
        }
    }//GEN-LAST:event_jTextField9KeyPressed

    private void jButton15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton15KeyPressed
      if (evt.getKeyCode() == KeyEvent.VK_UP) {
            jButton11.grabFocus();
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN) {
            jButton17.grabFocus();
        }
    }//GEN-LAST:event_jButton15KeyPressed

    private void jButton17KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton17KeyPressed
        
       if (evt.getKeyCode() == KeyEvent.VK_UP) {
            jButton15.grabFocus();
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN) {
            jButton21.grabFocus();
        } 
    }//GEN-LAST:event_jButton17KeyPressed

    private void jButton21KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton21KeyPressed
         if (evt.getKeyCode() == KeyEvent.VK_UP) {
            jButton17.grabFocus();
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN) {
            jButton18.grabFocus();
        } 
    }//GEN-LAST:event_jButton21KeyPressed

    private void jButton18KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton18KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            jButton21.grabFocus();
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN) {
            jButton20.grabFocus();
        }
    }//GEN-LAST:event_jButton18KeyPressed

    private void jButton20KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton20KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            jButton18.grabFocus();
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN) {
            jTextField7.grabFocus();
        }
    }//GEN-LAST:event_jButton20KeyPressed

    private void jButton11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton11KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
            jTextField7.grabFocus();
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN) {
            jButton15.grabFocus();
        }
    }//GEN-LAST:event_jButton11KeyPressed

  
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser FjDateChooser4;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private javax.swing.JTable jTable8;
    private javax.swing.JTable jTable9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables

    private void reset() {
        jTextField2.setText("");
        jTextField1.setText("");
        jTextField4.setText("");
        jTextField3.setText("");
        jTextField5.setText("");
        buttonGroup1.clearSelection();
        jTextField6.setText("");
        jPasswordField1.setText("");
        jTable1.clearSelection();
        jTextField2.grabFocus();
        jButton12.setEnabled(true);
        jTextField2.setEditable(true);

    }

    private void loadAdmins() {
        try {
            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `admin` ");
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("firstname") + " " + resultSet.getString("lastname"));
                vector.add(resultSet.getString("mobile"));
                vector.add(resultSet.getString("username"));
                vector.add(resultSet.getString("password"));
                model.addRow(vector);
            }
            jTable1.setModel(model);

        } catch (Exception e) {
        }
    }



    private void resetDayBooks() {
//        jTextField7.setEditable(true);
        jTextField7.setText("");
        jTable2.clearSelection();
        jComboBox2.setSelectedIndex(0);
        jComboBox3.setSelectedIndex(0);
        jComboBox1.setSelectedIndex(0);
        jTextField8.setText("");
        jTextField9.setText("");
   
        loadToDayBookCombobox();
        loadToDaybookTable("");
        jTextField7.grabFocus();
        jTextField23.setText("");
        setEndedStstatus();
        

    }

    private void motionReset() {
        jTable3.clearSelection();
        jTextField11.setText("");
        jTextField10.setText("");
        jTextField10.setEditable(true);
        jCheckBox2.setSelected(false);
        loadMotion("");
        jTextField11.grabFocus();

    }

    private void motionPassReset() {
        jTable4.clearSelection();
        jTextField12.setText("");
        jTextField13.setText("");
        loadMotionPass("");
        jTextField12.grabFocus();

    }

    private void resetFines() {
        jTable5.clearSelection();
        jTextField17.setText("");
        jTextField15.setText("");
        jTextField16.setText("");
        jTextField14.setText("");
        jTextField18.setText("");
        loadFinesToTable();
    }

    private void resetRolBook() {
        jDateChooser1.setDate(null);

    }

}
