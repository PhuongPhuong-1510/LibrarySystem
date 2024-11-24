package LMSNotification.controller;

import LMSNotification.view.NotificationView;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class NotificationController implements ActionListener, MouseListener {
    private NotificationView notificationView;

    public NotificationController(NotificationView notificationView) {
        this.notificationView = notificationView;
        initializeListeners();
    }

    private void initializeListeners() {
        notificationView.getBackgroundPanel1().addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        notificationView.toggleNotificationPanel();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        notificationView.getBackgroundPanel1().setBackground(new Color(60, 90, 130, 255));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        notificationView.getBackgroundPanel1().setBackground(new Color(42, 71, 106, 255));
    }
}
