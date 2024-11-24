package LoginPage.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PlaceholderPasswordField extends JTextField implements FocusListener {
    private String placeholder;
    private Color placeholderColor = new Color(100, 100, 100);

    public PlaceholderPasswordField(String placeholder) {
        this.placeholder = placeholder;
        this.addFocusListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        String text = getText();
        if (text.isEmpty() && !isFocusOwner()) {
            // Vẽ placeholder nếu trống và không được focus
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(placeholderColor);
            g2.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12)); // Font to tròn, cỡ chữ 12
            g2.drawString(placeholder, getInsets().left + 5, getHeight() / 2 + g.getFontMetrics().getAscent() / 2 - 2);
            g2.dispose();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        repaint(); // Vẽ lại khi focus
    }

    @Override
    public void focusLost(FocusEvent e) {
        repaint(); // Vẽ lại khi mất focus
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        repaint();
    }

    @Override
    public String getText() {
        return super.getText();
    }
}