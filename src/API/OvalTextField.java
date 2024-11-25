package API;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class OvalTextField extends JTextField {
    private String placeholder;

    public OvalTextField(int columns) {
        super(columns);
        setOpaque(false);
        setFont(new Font("Arial", Font.PLAIN, 12));

        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                repaint();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                repaint();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                repaint();
            }
        });
    }

    // Đặt placeholder
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Vẽ nền oval cho JTextField
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 60, 60);

        // Nếu không có nội dung, vẽ placeholder
        if (getText().isEmpty() && placeholder != null) {
            g.setColor(Color.GRAY);
            g.setFont(new Font("Arial", Font.ITALIC, 15));
            Insets insets = getInsets();
            g.drawString(placeholder, getInsets().left, getHeight() / 2 + getFont().getSize() / 2 - 2);
        }

        // Vẽ nội dung của JTextField
        super.paintComponent(g);
    }


    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(Color.BLACK);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 60, 60);
    }

    @Override
    public Insets getInsets() {

        return new Insets(10, 40, 10, 10);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(150, 40);
    }
}
