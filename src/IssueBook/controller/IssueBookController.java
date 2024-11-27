package IssueBook.controller;

import IssueBook.view.DatePickerDemo;
import IssueBook.view.IssueBookView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;

/**
 * Lớp điều khiển cho việc mượn sách.
 * Quản lý sự kiện nhấn nút và chọn ngày mượn và ngày trả của người dùng.
 */
public class IssueBookController implements ActionListener, MouseListener {
    private IssueBookView issueBookView;
    private LocalDate issueDate;
    private LocalDate dueDate;

    /**
     * Constructor cho lớp điều khiển.
     * Khởi tạo và gán các listener cho các sự kiện.
     *
     * @param issueBookView Giao diện người dùng cho việc mượn sách
     */
    public IssueBookController(IssueBookView issueBookView) {
        this.issueBookView = issueBookView;
        this.initializeListeners();
    }

    /**
     * Phương thức để khởi tạo các listener cho các sự kiện.
     * Gán các listener cho các nút "Ngày mượn", "Ngày trả", "Mượn sách" và "Xóa dữ liệu".
     */
    private void initializeListeners() {
        // Gán sự kiện cho các nút chọn ngày
        issueBookView.getIssueDateButton().addActionListener(this);
        issueBookView.getDueDateButton().addActionListener(this);

        // Gán sự kiện chuột cho các nút "Mượn sách" và "Xóa dữ liệu"
        issueBookView.getIssueButton().addMouseListener(this);
        issueBookView.getClearButton().addMouseListener(this);
    }

    /**
     * Phương thức xử lý sự kiện khi người dùng nhấn vào các nút.
     * Xử lý sự kiện khi chọn ngày mượn và ngày trả.
     *
     * @param e Sự kiện hành động nhấn nút
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Kiểm tra nếu nút "Ngày mượn" được nhấn
        if (e.getSource() == issueBookView.getIssueDateButton()) {
            DatePickerDemo jDialogIssue = new DatePickerDemo();
            jDialogIssue.setBounds(965, 524, 400, 200);
            jDialogIssue.setModal(true);
            jDialogIssue.setVisible(true);

            issueDate = jDialogIssue.getSelectedDate();  // Lưu vào biến thành viên

            if (issueDate != null) {
                issueBookView.getIssueDateField().setText(issueDate.toString()); // Cập nhật ngày mượn sách
            }
        }

        // Kiểm tra nếu nút "Ngày trả" được nhấn
        if (e.getSource() == issueBookView.getDueDateButton()) {
            DatePickerDemo jDialogDue = new DatePickerDemo();
            jDialogDue.setBounds(965, 565, 400, 200);
            jDialogDue.setModal(true);
            jDialogDue.setVisible(true);

            dueDate = jDialogDue.getSelectedDate();

            // Kiểm tra nếu ngày trả hợp lệ và không sớm hơn ngày mượn
            if (dueDate != null) {
                if (issueDate == null) {
                    JOptionPane.showMessageDialog(issueBookView,
                            "Please select the Issue Date first.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else if (isDueDateValid(issueDate, dueDate)) {

                    issueBookView.getDueDateField().setText(dueDate.toString());
                }
            }
        }

    }

    /**
     * Phương thức kiểm tra xem ngày trả sách có hợp lệ hay không.
     * Đảm bảo rằng ngày trả không sớm hơn ngày mượn.
     *
     * @param issueDate Ngày mượn sách
     * @param dueDate Ngày trả sách
     * @return true nếu ngày trả hợp lệ, ngược lại trả về false
     */
    private boolean isDueDateValid(LocalDate issueDate, LocalDate dueDate) {
        if (issueDate == null || dueDate == null) {
            JOptionPane.showMessageDialog(issueBookView,
                    "Both Issue Date and Due Date must be selected.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return !dueDate.isBefore(issueDate); // Trả về true nếu ngày trả không sớm hơn ngày mượn
    }

    /**
     * Phương thức xử lý sự kiện khi con trỏ chuột di chuyển vào các nút.
     * Thay đổi kiểu chữ và màu nền của nút khi chuột hover vào.
     *
     * @param e Sự kiện chuột di chuyển vào
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == issueBookView.getIssueButton()) {
            styleButtonHover(issueBookView.getIssueButton(), Font.PLAIN, 16);
        } else if (e.getSource() == issueBookView.getClearButton()) {
            styleButtonHover(issueBookView.getClearButton(), Font.PLAIN, 16);
        }
    }

    /**
     * Phương thức xử lý sự kiện khi con trỏ chuột di chuyển vào các nút.
     * Thay đổi kiểu chữ và màu nền của nút khi chuột hover vào.
     *
     * @param e Sự kiện chuột di chuyển vào
     */
    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() ==issueBookView.getIssueButton()) {
            styleButtonDefault(issueBookView.getIssueButton());
        } else if (e.getSource() == issueBookView.getClearButton()) {
            styleButtonDefault(issueBookView.getClearButton());
        }
    }

    /**
     * Phương thức thay đổi kiểu nút khi chuột hover vào.
     *
     * @param button Nút cần thay đổi
     * @param fontStyle Kiểu chữ
     * @param fontSize Kích thước chữ
     */
    private void styleButtonHover(JButton button, int fontStyle, int fontSize) {
        button.setBackground(new Color(255,69,0));
        button.setFont(new Font("Tahoma", fontStyle, fontSize));
        button.revalidate();
        button.repaint();
    }

    /**
     * Phương thức khôi phục kiểu nút mặc định.
     *
     * @param button Nút cần khôi phục
     */
    private void styleButtonDefault(JButton button) {
        button.setBackground(new Color(255,130,171));
        button.setFont(new Font("Tahoma", Font.BOLD, 16)); // Kiểu chữ mặc định
        button.revalidate();
        button.repaint();
    }

    /**
     * Phương thức xử lý sự kiện khi người dùng nhấn nút "Mượn sách" hoặc "Xóa dữ liệu".
     *
     * @param e Sự kiện chuột nhấn
     */
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == issueBookView.getIssueButton()) {
            issueBookView.issueBook(); // Thêm thông tin vào cơ sở dữ liệu
        } else if (e.getSource() == issueBookView.getClearButton()) {
            issueBookView.removeData();  // Xóa dữ liệu các trường nhập liệu
            JOptionPane.showMessageDialog(issueBookView, "All fields cleared.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

}


