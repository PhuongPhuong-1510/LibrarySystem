package IssueBook.view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Calendar;


/**
 * Lớp DatePickerDemo hiển thị một cửa sổ chọn ngày cho người dùng.
 * Cửa sổ này cho phép người dùng chọn một ngày từ một lịch.
 */
public class DatePickerDemo extends JDialog {
    // Biến lưu trữ ngày được chọn
    private LocalDate selectedDate;

    /**
     * Phương thức trả về ngày được chọn.
     *
     * @return Ngày đã chọn
     */
    public LocalDate getSelectedDate() {
        return selectedDate;
    }

    /**
     * Constructor khởi tạo cửa sổ chọn ngày.
     * Cửa sổ này bao gồm các thành phần như combo box cho tháng và năm,
     * cùng với các nút để chọn ngày từ lịch.
     */
    public DatePickerDemo() {
        setSize(400, 250);
        this.setUndecorated(true);

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        // Tạo combo box cho tháng
        JComboBox<String> monthCombo = new JComboBox<>(new String[] {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"});
        monthCombo.setSelectedIndex(month);
        monthCombo.setBackground(new Color(248, 248, 255));

        // Tạo combo box cho năm
        JComboBox<Integer> yearCombo = new JComboBox<>();
        for (int i = year - 100; i <= year + 100; i++) {
            yearCombo.addItem(i);
        }
        yearCombo.setSelectedItem(year);
        yearCombo.setBackground(new Color(248, 248, 255));

        // Panel chứa các combo box cho tháng và năm
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 192, 203));
        headerPanel.add(monthCombo);
        headerPanel.add(yearCombo);

        // Panel chứa các nút ngày
        JPanel daysPanel = new JPanel(new GridLayout(7, 7));
        daysPanel.setBackground(new Color(255, 192, 203));

        // Thêm các nhãn cho các ngày trong tuần
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : days) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(new Font("Arial", Font.BOLD, 14));
            daysPanel.add(dayLabel);
        }

        // Mảng các nút để người dùng chọn ngày
        JButton[] dayButtons = new JButton[42];
        for (int i = 0; i < 42; i++) {
            dayButtons[i] = new JButton();
            dayButtons[i].setBackground(new Color(255, 228, 225));
            daysPanel.add(dayButtons[i]);
            dayButtons[i].addActionListener(e -> {
                JButton source = (JButton) e.getSource();
                String dayText = source.getText();
                if (!dayText.isEmpty()) {
                    int day = Integer.parseInt(dayText);
                    int selectedYear = (int) yearCombo.getSelectedItem(); // Lấy năm từ JComboBox
                    int selectedMonth = monthCombo.getSelectedIndex() + 1; // Lấy tháng từ JComboBox
                    selectedDate = LocalDate.of(selectedYear, selectedMonth, day); // Cập nhật selectedDate
                    System.out.println("Ngày chọn: " + day + "/" + selectedMonth + "/" + selectedYear);
                    this.dispose();

                }
            });
        }

        // Cập nhật lại các ngày khi tháng hoặc năm thay đổi
        monthCombo.addActionListener(e -> updateDays(daysPanel, dayButtons, monthCombo, yearCombo));
        yearCombo.addActionListener(e -> updateDays(daysPanel, dayButtons, monthCombo, yearCombo));

        // Cập nhật ngày khi lần đầu tiên hiển thị lịch
        updateDays(daysPanel, dayButtons, monthCombo, yearCombo);

        // Đặt layout cho cửa sổ
        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(daysPanel, BorderLayout.CENTER);
    }

    /**
     * Cập nhật các ngày trên lịch khi người dùng thay đổi tháng hoặc năm.
     *
     * @param daysPanel Panel chứa các nút ngày.
     * @param dayButtons Mảng các nút ngày.
     * @param monthCombo JComboBox chứa tháng.
     * @param yearCombo JComboBox chứa năm.
     */
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
                dayButtons[i].setBackground(new Color(255, 228, 225));
                dayButtons[i].setEnabled(true);
            } else {
                dayButtons[i].setText("");
                dayButtons[i].setEnabled(false);
                dayButtons[i].setBackground(Color.WHITE);
            }
        }
        daysPanel.revalidate();
        daysPanel.repaint();
    }
}