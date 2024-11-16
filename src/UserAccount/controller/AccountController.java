package UserAccount.controller;

import UserAccount.view.AccountView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AccountController implements ActionListener, MouseListener {
    private AccountView accountView;

    public AccountController(AccountView accountView) {
        this.accountView = accountView;
        initializeListeners();

    }
    private void initializeListeners() {
        accountView.getPassButton().addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }


    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == accountView.getPassButton()) {
            styleButtonHover(accountView.getPassButton(), Font.PLAIN, 13);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() ==accountView.getPassButton()) {
            styleButtonDefault(accountView.getPassButton());
        }
    }

    private void styleButtonHover(JButton button, int fontStyle, int fontSize) {
        button.setBackground(new Color(255,106,106));
        button.setFont(new Font("Tahoma", fontStyle, fontSize));
        button.revalidate();
        button.repaint();
    }

    private void styleButtonDefault(JButton button) {
        button.setBackground(new Color(132,173,185));
        button.setFont(new Font("Tahoma", Font.BOLD, 13));
        button.revalidate();
        button.repaint();
    }
}

