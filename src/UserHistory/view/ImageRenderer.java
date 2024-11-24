package UserHistory.view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

class ImageRenderer extends JLabel implements TableCellRenderer {
    public ImageRenderer() {
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value != null && value instanceof String) {
            String imagePath = (String) value;
            ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
            setIcon(icon);
        } else {
            setIcon(null);
        }
        return this;
    }
}

