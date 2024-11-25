package API;

import MainApp.view.MainView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChiTietController implements ActionListener {
    private final ChiTiet chiTiet;

    public ChiTietController(ChiTiet chiTiet) {
        this.chiTiet = chiTiet;
        initializeButtonListeners();
        initializeMouseListeners();
    }


    private void initializeButtonListeners() {
        chiTiet.getCloseButton().addActionListener(this);
    }

    // Phương thức khởi tạo các MouseListener cho nút
    private void initializeMouseListeners() {
        addMouseHoverEffect(chiTiet.getCloseButton());
    }

    // Phương thức thêm hiệu ứng màu khi di chuột
    private void addMouseHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(255, 70, 70));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(236,212,236,255)); // Trả về màu nền gốc
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == chiTiet.getCloseButton()) {
            handleClose();
        }
    }

    // Phương thức xử lý sự kiện đóng ứng dụng
    private void handleClose() {
        chiTiet.setVisible(false);
        chiTiet.getParentFrame().dispose();
    }


}
