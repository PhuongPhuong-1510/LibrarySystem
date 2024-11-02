package HomePage.view;

import javax.swing.*;
import java.awt.*;

public class PieChartExample extends JPanel {
    private double[] values = {};
    private Color[] colors = {};
    private String[] labels = {};

    public PieChartExample(double[] values, Color[] colors, String[] labels) {
        this.values = values;
        this.colors = colors;
        this.labels = labels;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        int width = 300;
        int height = 300;
        int x = 5;
        int y = 25;

        // Vẽ đổ bóng
        int shadowOffset = 5;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // Đặt độ trong suốt cho bóng
        g2d.setColor(Color.GRAY);
        double startAngleShadow = 0;
        for (int i = 0; i < values.length; i++) {
            double arcAngle = values[i] * 360 / 100;
            g2d.fillArc(x + shadowOffset, y + shadowOffset, width, height, (int) startAngleShadow, (int) arcAngle);
            startAngleShadow += arcAngle;
        }

        // Vẽ biểu đồ chính
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)); // Đặt lại độ trong suốt
        double startAngle = 0;
        for (int i = 0; i < values.length; i++) {
            double arcAngle = values[i] * 360 / 100;
            g2d.setColor(colors[i]);
            g2d.fillArc(x, y, width, height, (int) startAngle, (int) arcAngle);
            startAngle += arcAngle;
        }

        // Vẽ chú thích
        int legendX = x + width + 20;
        int legendY = y;
        int legendHeight = 20;
        for (int i = 0; i < values.length; i++) {
            g2d.setColor(colors[i]);
            g2d.fillRect(legendX, legendY + i * (legendHeight + 10), legendHeight, legendHeight);

            g2d.setColor(Color.BLACK);
            g2d.drawString(labels[i] + " - " + values[i] + "%", legendX + legendHeight + 10, legendY + i * (legendHeight + 10) + legendHeight - 5);
        }
    }


}
