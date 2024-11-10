package IssueBook.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;

public class DatePickerDemo extends JFrame {
    private JTextField dateField;
    private JButton dateButton;

    public DatePickerDemo() {
        setTitle("Date Picker Demo");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        dateField = new JTextField(10);
        dateField.setEditable(false);

        dateButton = new JButton("Chọn ngày");
        dateButton.addActionListener(e -> showDatePicker());

        JPanel panel = new JPanel();
        panel.add(new JLabel("Issue Date:"));
        panel.add(dateField);
        panel.add(dateButton);

        add(panel);
    }

    public void showDatePicker() {
        JDialog dateDialog = new JDialog(this, "Chọn ngày", true);
        dateDialog.setSize(250, 200);
        dateDialog.setLayout(new BorderLayout());

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        JComboBox<String> monthCombo = new JComboBox<>(new String[]{
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"});
        monthCombo.setSelectedIndex(month);

        JComboBox<Integer> yearCombo = new JComboBox<>();
        for (int i = year - 100; i <= year + 100; i++) {
            yearCombo.addItem(i);
        }
        yearCombo.setSelectedItem(year);

        JPanel headerPanel = new JPanel();
        headerPanel.add(monthCombo);
        headerPanel.add(yearCombo);

        JPanel daysPanel = new JPanel(new GridLayout(7, 7));
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : days) {
            daysPanel.add(new JLabel(day, SwingConstants.CENTER));
        }

        JButton[] dayButtons = new JButton[42];
        for (int i = 0; i < 42; i++) {
            dayButtons[i] = new JButton();
            daysPanel.add(dayButtons[i]);
            dayButtons[i].addActionListener(e -> {
                JButton source = (JButton) e.getSource();
                dateField.setText(source.getText() + "/" + (monthCombo.getSelectedIndex() + 1) + "/" + yearCombo.getSelectedItem());
                dateDialog.dispose();
            });
        }

        monthCombo.addActionListener(e -> updateDays(daysPanel, dayButtons, monthCombo, yearCombo));
        yearCombo.addActionListener(e -> updateDays(daysPanel, dayButtons, monthCombo, yearCombo));

        updateDays(daysPanel, dayButtons, monthCombo, yearCombo);

        dateDialog.add(headerPanel, BorderLayout.NORTH);
        dateDialog.add(daysPanel, BorderLayout.CENTER);
        dateDialog.setLocationRelativeTo(this);
        dateDialog.setVisible(true);
    }

    private void updateDays(JPanel daysPanel, JButton[] dayButtons, JComboBox<String> monthCombo, JComboBox<Integer> yearCombo) {
        Calendar calendar = Calendar.getInstance();
        int month = monthCombo.getSelectedIndex();
        int year = (int) yearCombo.getSelectedItem();

        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int startDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < 42; i++) {
            if (i >= startDay && i < startDay + daysInMonth) {
                dayButtons[i].setText(Integer.toString(i - startDay + 1));
                dayButtons[i].setEnabled(true);
            } else {
                dayButtons[i].setText("");
                dayButtons[i].setEnabled(false);
            }
        }
        daysPanel.revalidate();
        daysPanel.repaint();
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            DatePickerDemo demo = new DatePickerDemo();
//            demo.setVisible(true);
//        });
//    }
}

