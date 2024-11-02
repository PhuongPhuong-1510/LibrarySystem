package HomePage.view;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class CustomScrollBarUI extends BasicScrollBarUI {
    private static final int SCROLL_BAR_WIDTH = 10; // Độ rộng của thanh cuộn

    private Color thumbColor = new Color(100, 149, 237); // Màu thanh trượt
    private Color trackColor = new Color(245, 222, 179); // Màu nền của thanh cuộn

    public void setColors(Color thumbColor, Color trackColor) {
        this.thumbColor = thumbColor;
        this.trackColor = trackColor;
    }

    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = thumbColor;
        this.trackColor = trackColor;
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        g.setColor(thumbColor);
        g.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10); // Bo tròn góc cho thanh trượt
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        g.setColor(trackColor);
        g.fillRoundRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height, 10, 10); // Bo tròn góc cho track
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        if (((JScrollBar) c).getOrientation() == JScrollBar.VERTICAL) {
            return new Dimension(SCROLL_BAR_WIDTH, super.getPreferredSize(c).height);
        } else {
            return new Dimension(super.getPreferredSize(c).width, SCROLL_BAR_WIDTH);
        }
    }
}

