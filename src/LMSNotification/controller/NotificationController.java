package LMSNotification.controller;

import LMSNotification.view.NotificationView;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 * Lớp điều khiển thông báo, quản lý các sự kiện liên quan đến giao diện NotificationView.
 */
public class NotificationController implements ActionListener, MouseListener {
    private NotificationView notificationView;

    /**
     * Khởi tạo NotificationController với một NotificationView.
     *
     * @param notificationView giao diện thông báo cần điều khiển.
     */
    public NotificationController(NotificationView notificationView) {
        this.notificationView = notificationView;
        initializeListeners();
    }

    /**
     * Thêm các bộ lắng nghe sự kiện cho các thành phần trong giao diện.
     */
    private void initializeListeners() {
        notificationView.getBackgroundPanel1().addMouseListener(this);
    }

    /**
     * Xử lý các hành động từ các sự kiện ActionEvent.
     *
     * @param e sự kiện được kích hoạt.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
    }

    /**
     * Xử lý sự kiện khi người dùng nhấn chuột vào panel.
     *
     * @param e sự kiện chuột được kích hoạt.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        notificationView.toggleNotificationPanel();
    } // Thay đổi trạng thái hiển thị của bảng thông báo.

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Xử lý sự kiện khi con trỏ chuột đi vào khu vực của panel.
     *
     * @param e sự kiện chuột được kích hoạt.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        notificationView.getBackgroundPanel1().setBackground(new Color(60, 90, 130, 255));
    }

    /**
     * Xử lý sự kiện khi con trỏ chuột rời khỏi khu vực của panel.
     *
     * @param e sự kiện chuột được kích hoạt.
     */
    @Override
    public void mouseExited(MouseEvent e) {
        notificationView.getBackgroundPanel1().setBackground(new Color(42, 71, 106, 255));
    }
}
