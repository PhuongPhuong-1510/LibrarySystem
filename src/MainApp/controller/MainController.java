package MainApp.controller;

import MainApp.view.MainView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainController implements ActionListener {
    private final MainView mainView;

    public MainController(MainView mainView) {
        this.mainView = mainView;
        initializeButtonListeners();
        initializeMouseListeners();
    }

    // Phương thức khởi tạo các ActionListener cho nút
    private void initializeButtonListeners() {
        mainView.getCloseButton().addActionListener(this);
        mainView.getMinimizeButton().addActionListener(this);
    }

    // Phương thức khởi tạo các MouseListener cho nút
    private void initializeMouseListeners() {
        addMouseHoverEffect(mainView.getCloseButton());
        addMouseHoverEffect(mainView.getMinimizeButton());
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
                button.setBackground(new Color(150, 180, 255)); // Trả về màu nền gốc
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == mainView.getCloseButton()) {
            handleClose();
        } else if (source == mainView.getMinimizeButton()) {
            handleMinimize();
        }
    }

    // Phương thức xử lý sự kiện đóng ứng dụng
    private void handleClose() {
        System.exit(0); // Đóng ứng dụng
    }

    // Phương thức xử lý sự kiện thu nhỏ cửa sổ
    private void handleMinimize() {
        mainView.setState(JFrame.ICONIFIED); // Thu nhỏ cửa sổ
    }
}
