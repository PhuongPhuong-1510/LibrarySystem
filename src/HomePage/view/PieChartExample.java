package HomePage.view;

import javax.swing.*;
import java.awt.*;

/**
 * Lớp vẽ biểu đồ tròn (Pie Chart) trong JPanel.
 * Biểu đồ tròn hiển thị các phần trăm của các giá trị khác nhau, với các màu sắc khác nhau cho mỗi phần.
 */
public class PieChartExample extends JPanel {
    // Các giá trị tỷ lệ phần trăm cho từng phần của biểu đồ
    private double[] values = {};
    // Các màu sắc ứng với từng phần trong biểu đồ
    private Color[] colors = {};
    // Các nhãn cho từng phần của biểu đồ
    private String[] labels = {};

    /**
     * Constructor tạo biểu đồ tròn với các giá trị, màu sắc và nhãn.
     *
     * @param values Các giá trị tỷ lệ phần trăm cho các phần trong biểu đồ
     * @param colors Mảng các màu sắc ứng với từng phần của biểu đồ
     * @param labels Mảng các nhãn cho từng phần của biểu đồ
     */
    public PieChartExample(double[] values, Color[] colors, String[] labels) {
        this.values = values;
        this.colors = colors;
        this.labels = labels;
    }

    /**
     * Phương thức vẽ biểu đồ tròn lên JPanel.
     * Sử dụng phương thức paintComponent của JPanel để vẽ biểu đồ.
     *
     * @param g Đối tượng Graphics được sử dụng để vẽ trên JPanel
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        int width = 300;
        int height = 300;
        int x = 5;
        int y = 25;

        // Vẽ đổ bóng cho biểu đồ tròn để tạo hiệu ứng nổi
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
            // Vẽ hình vuông nhỏ để đại diện cho màu sắc của mỗi phần trong biểu đồ
            g2d.setColor(colors[i]);
            g2d.fillRect(legendX, legendY + i * (legendHeight + 10), legendHeight, legendHeight);

            // Vẽ nhãn cho từng phần, bao gồm tên và tỷ lệ phần trăm
            g2d.setColor(Color.BLACK);
            g2d.drawString(labels[i] + " - " + values[i] + "%", legendX + legendHeight + 10, legendY + i * (legendHeight + 10) + legendHeight - 5);
        }
    }


}
