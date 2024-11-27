package HomePage.view;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

/**
 * CustomScrollBarUI là một lớp tùy chỉnh cho giao diện của thanh cuộn (scrollbar),
 * kế thừa từ BasicScrollBarUI của Swing.
 * Lớp này hỗ trợ tùy chỉnh màu sắc, độ rộng và kiểu hiển thị của thanh cuộn.
 */
public class CustomScrollBarUI extends BasicScrollBarUI {
    // Độ rộng cố định của thanh cuộn
    private static final int SCROLL_BAR_WIDTH = 10; // Độ rộng của thanh cuộn

    // Màu của thanh trượt (thumb)
    private Color thumbColor = new Color(100, 149, 237);

    // Màu của nền thanh cuộn (track)
    private Color trackColor = new Color(245, 222, 179);

    /**
     * Phương thức thiết lập màu sắc cho thanh trượt và nền của thanh cuộn.
     *
     * @param thumbColor Màu của thanh trượt
     * @param trackColor Màu của nền thanh cuộn
     */
    public void setColors(Color thumbColor, Color trackColor) {
        this.thumbColor = thumbColor;
        this.trackColor = trackColor;
    }

    /**
     * Cấu hình màu sắc của các thành phần thanh cuộn.
     * Được gọi tự động bởi hệ thống khi vẽ giao diện.
     */
    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = thumbColor;
        this.trackColor = trackColor;
    }

    /**
     * Tạo nút giảm (decrease button) với kích thước bằng 0 để ẩn nút này.
     *
     * @param orientation Hướng của nút giảm
     * @return JButton có kích thước bằng 0
     */
    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    /**
     * Tạo nút tăng (increase button) với kích thước bằng 0 để ẩn nút này.
     *
     * @param orientation Hướng của nút tăng
     * @return JButton có kích thước bằng 0
     */
    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    /**
     * Tạo một JButton với kích thước bằng 0 để ẩn đi.
     *
     * @return JButton có kích thước bằng 0
     */
    private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
    }

    /**
     * Vẽ thanh trượt (thumb) của thanh cuộn với góc bo tròn.
     *
     * @param g           Đối tượng đồ họa dùng để vẽ
     * @param c           Thành phần (component) được vẽ
     * @param thumbBounds Kích thước và vị trí của thanh trượt
     */
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        g.setColor(thumbColor);
        g.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10); // Bo tròn góc cho thanh trượt
    }

    /**
     * Vẽ nền của thanh cuộn (track) với góc bo tròn.
     *
     * @param g           Đối tượng đồ họa dùng để vẽ
     * @param c           Thành phần (component) được vẽ
     * @param trackBounds Kích thước và vị trí của nền thanh cuộn
     */
    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        g.setColor(trackColor);
        g.fillRoundRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height, 10, 10); // Bo tròn góc cho track
    }

    /**
     * Cung cấp kích thước ưa thích (preferred size) cho thanh cuộn.
     *
     * @param c Thành phần thanh cuộn
     * @return Kích thước ưa thích của thanh cuộn
     */
    @Override
    public Dimension getPreferredSize(JComponent c) {
        if (((JScrollBar) c).getOrientation() == JScrollBar.VERTICAL) {
            return new Dimension(SCROLL_BAR_WIDTH, super.getPreferredSize(c).height);
        } else {
            return new Dimension(super.getPreferredSize(c).width, SCROLL_BAR_WIDTH);
        }
    }
}