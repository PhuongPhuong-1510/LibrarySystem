package LoginPage.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PlaceholderPasswordField extends JPasswordField implements FocusListener {
    private String placeholder; // Placeholder text
    private Color placeholderColor = new Color(100, 100, 100); // Màu đen nhạt cho placeholder
    private int check;

    public PlaceholderPasswordField(String placeholder, int check) {
        this.placeholder = placeholder;
        this.check = check;
        this.addFocusListener(this);
        setEchoChar(check == 1 ? '•' : (char) 0);
    }

    public PlaceholderPasswordField(String placeholder) {
    }

    // Vẽ placeholder nếu password field rỗng và không có focus
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getPassword().length == 0 && !isFocusOwner()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(placeholderColor); // Đặt màu cho placeholder
            g2.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12)); // Font to tròn, cỡ chữ 12
            g2.drawString(placeholder, getInsets().left + 5, getHeight() / 2 + getFont().getSize() / 2 - 2);
            g2.dispose();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        // Vẽ lại khi được focus
        repaint();
    }

    @Override
    public void focusLost(FocusEvent e) {
        // Vẽ lại khi mất focus
        repaint();
    }
}
