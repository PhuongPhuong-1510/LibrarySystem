package LoginPage.view;

import javax.swing.*;
import java.awt.*;

public class OvalButton extends JButton {
    public OvalButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(new Font("Tahoma", Font.BOLD, 16));
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Vẽ nền với các góc tròn
        super.paintComponent(g); // Gọi phương thức cha để vẽ nội dung nút
    }



    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 30); // Kích thước nhỏ hơn cho nút
    }
}

