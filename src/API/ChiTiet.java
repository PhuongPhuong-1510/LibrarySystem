package API;

import HomePage.view.CustomScrollBarUI;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class ChiTiet extends JPanel {
    private static final long serialVersionUID = 1L;
    private final JButton closeButton;
    private JFrame parentFrame; // Tham chiếu tới JFrame chứa ChiTiet panel

    public ChiTiet(JFrame parentFrame, String title, String author, String language, String category, String thumbnailUrl, String infoLink, String description) {
        this.parentFrame = parentFrame;
     setLayout(new BorderLayout());
        this.setBorder(new LineBorder(new Color(232,244,252), 3, true));



        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);


        JPanel backgroundPanel = createBackgroundPanel("/API/icon/background.png");
        configurePanel(backgroundPanel, 0, 0, 600, 500);
        backgroundPanel.setBackground(new Color(35,69,109,255));
        layeredPane.add(backgroundPanel, Integer.valueOf(0));



        JPanel imagePanel = new JPanel();
        imagePanel.setBounds(20, 80, 128, 192);

        try {
            URL url = new URL(thumbnailUrl);
            ImageIcon imageIcon = new ImageIcon(url);
            Image image = imageIcon.getImage().getScaledInstance(128, 192, Image.SCALE_SMOOTH);
            JLabel lblImage = new JLabel(new ImageIcon(image));
            imagePanel.add(lblImage);
        } catch (Exception e) {
            JLabel lblImage = new JLabel("Không tìm thấy ảnh");
            lblImage.setHorizontalAlignment(SwingConstants.CENTER);
            lblImage.setVerticalAlignment(SwingConstants.CENTER);
            imagePanel.add(lblImage);
            System.out.println("Không tải được ảnh từ URL: " + thumbnailUrl);
        }

        layeredPane.add(imagePanel,Integer.valueOf(1));

        JPanel titlePanel = new JPanel(new BorderLayout());

        JLabel lblTitle = new JLabel();
        lblTitle.setFont(new Font("Arial", Font.BOLD, 25)); // Đặt font
        String formattedTitle = "<html>" + wrapTextToWidth(title, 300, lblTitle.getFontMetrics(lblTitle.getFont())) + "</html>";
        lblTitle.setText(formattedTitle);
        titlePanel.setBackground(new Color(236,212,236,255));


        titlePanel.add(lblTitle, BorderLayout.CENTER);

        JScrollPane titleScrollPane = createScrollPane(titlePanel);
        titleScrollPane.setBounds(200, 10, 345, 100); // Đặt vị trí và kích thước cho JScrollPane
        titleScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Hiển thị thanh cuộn dọc khi cần
        titleScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Không hiển thị thanh cuộn ngang

// Thêm JScrollPane vào giao diện chính
        add(titleScrollPane);


        JLabel lblAuthor = new JLabel();
        lblAuthor.setFont(new Font("Arial", Font.BOLD, 15)); // Đặt font trước khi gọi getFontMetrics
        lblAuthor.setBounds(180, 150, 250, 30);
        String formattedAuthor = "<html>" + wrapTextToWidth("Author: " + author, 507, lblAuthor.getFontMetrics(lblAuthor.getFont())) + "</html>";
        lblAuthor.setText(formattedAuthor);
        add(lblAuthor);

        JLabel lblLanguage = new JLabel();
        lblLanguage.setFont(new Font("Arial", Font.BOLD, 15)); // Đặt font trước khi gọi getFontMetrics
        lblLanguage.setBounds(180, 200, 250, 30);
        String formattedLanguage = "<html>" + wrapTextToWidth("Language: " + language, 507, lblLanguage.getFontMetrics(lblLanguage.getFont())) + "</html>";
        lblLanguage.setText(formattedLanguage);
        add(lblLanguage);

        JLabel lblCategory = new JLabel();
        lblCategory.setFont(new Font("Arial", Font.BOLD, 15)); // Đặt font trước khi gọi getFontMetrics
        lblCategory.setBounds(180, 250, 250, 30);
        String formattedCategory = "<html>" + wrapTextToWidth("Category: " + category, 507, lblCategory.getFontMetrics(lblCategory.getFont())) + "</html>";
        lblCategory.setText(formattedCategory);
        add(lblCategory);

        JPanel qrCodePanel = new JPanel();
        qrCodePanel.setBackground(new Color(0,0,0,0));
        qrCodePanel.setBounds(450, 120, 100, 100);
        add(qrCodePanel);

        try {
            String bookLink = infoLink; // Link trực tiếp
            BufferedImage qrCodeImage = generateQRCodeImage(bookLink, 100, 100); // Loại bỏ viền
            JLabel lblQRCode = new JLabel(new ImageIcon(qrCodeImage));
            qrCodePanel.add(lblQRCode);
        } catch (Exception e) {
            JLabel lblQRCode = new JLabel("QR Code Error");
            qrCodePanel.add(lblQRCode);
            e.printStackTrace();
        }
        JPanel despanel=new JPanel(new BorderLayout());

        JLabel deslbl = new JLabel();
        deslbl.setFont(new Font("Arial", Font.BOLD, 15)); // Đặt font trước khi gọi getFontMetrics
        despanel.add(deslbl,BorderLayout.CENTER);
        String formattedDes = "<html>" + wrapTextToWidth("Description : " + description, 507, lblCategory.getFontMetrics(lblCategory.getFont())) + "</html>";
        deslbl.setText(formattedDes);
        despanel.setBackground(new Color(204,220,251));

        JScrollPane scrollPane = createScrollPane(despanel);
        scrollPane.setBounds(10, 290, 550, 190); // Đặt kích thước và vị trí cho JScrollPane
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Luôn hiển thị thanh cuộn dọc
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Không hiển thị thanh cuộn ngang

        add(scrollPane);

        closeButton = createButton("X", 547, 5);
        new ChiTietController(this);
        add(closeButton);








        this.add(layeredPane,BorderLayout.CENTER);




    }
    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(236,212,236,255));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText("CLOSE");
        button.setBounds(x, y, 50, 50); // Size and position of the button
        return button;
    }
    public JScrollPane createScrollPane(JPanel table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(238, 210, 238)); // Màu nền cho vùng hiển thị của JScrollPane


        CustomScrollBarUI verticalScrollBarUI = new CustomScrollBarUI();
        verticalScrollBarUI.setColors(new Color(205, 201, 201), new Color(232, 232, 232));
        scrollPane.getVerticalScrollBar().setUI(verticalScrollBarUI); // Ghi đè UI cho thanh cuộn dọc

        CustomScrollBarUI horizontalScrollBarUI = new CustomScrollBarUI();
        horizontalScrollBarUI.setColors(new Color(205, 201, 201), new Color(232, 232, 232));
        scrollPane.getHorizontalScrollBar().setUI(horizontalScrollBarUI); // Ghi đè UI cho thanh cuộn ngang

        scrollPane.setPreferredSize(new Dimension(1200, getHeight()));
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    public BufferedImage generateQRCodeImage(String text, int width, int height) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        // Tìm giới hạn của vùng không phải khoảng trắng
        int[] enclosingRectangle = findEnclosingRectangle(bitMatrix);

        // Cắt BitMatrix để loại bỏ khoảng trắng
        int x = enclosingRectangle[0];
        int y = enclosingRectangle[1];
        int qrWidth = enclosingRectangle[2];
        int qrHeight = enclosingRectangle[3];

        BitMatrix croppedMatrix = cropBitMatrix(bitMatrix, x, y, qrWidth, qrHeight);

        // Chuyển đổi BitMatrix thành BufferedImage
        return MatrixToImageWriter.toBufferedImage(croppedMatrix);
    }

    // Hàm tìm vùng bao quanh nội dung QR Code
    private int[] findEnclosingRectangle(BitMatrix bitMatrix) {
        int top = Integer.MAX_VALUE;
        int left = Integer.MAX_VALUE;
        int bottom = Integer.MIN_VALUE;
        int right = Integer.MIN_VALUE;

        for (int y = 0; y < bitMatrix.getHeight(); y++) {
            for (int x = 0; x < bitMatrix.getWidth(); x++) {
                if (bitMatrix.get(x, y)) {
                    if (x < left) left = x;
                    if (y < top) top = y;
                    if (x > right) right = x;
                    if (y > bottom) bottom = y;
                }
            }
        }

        return new int[]{left, top, right - left + 1, bottom - top + 1};
    }

    // Hàm cắt BitMatrix
    private BitMatrix cropBitMatrix(BitMatrix bitMatrix, int x, int y, int width, int height) {
        BitMatrix croppedMatrix = new BitMatrix(width, height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (bitMatrix.get(x + i, y + j)) {
                    croppedMatrix.set(i, j);
                }
            }
        }
        return croppedMatrix;
    }


    public static String wrapTextToWidth(String text, int width, FontMetrics fm) {
        StringBuilder wrappedText = new StringBuilder();
        String[] words = text.split(" ");
        int lineLength = 0;

        for (String word : words) {
            int wordLength = fm.stringWidth(word + " ");

            // Nếu chiều dài dòng cộng với chiều dài từ hiện tại vượt quá giới hạn width
            if (lineLength + wordLength > width) {
                wrappedText.append("<br>"); // Chuyển sang dòng mới
                lineLength = 0; // Reset chiều dài dòng
            }

            // Thêm từ vào dòng và cập nhật chiều dài dòng
            wrappedText.append(word).append(" ");
            lineLength += wordLength;
        }

        return wrappedText.toString().trim(); // Trả về chuỗi với thẻ HTML cho phép xuống dòng
    }

    private JPanel createBackgroundPanel(String path) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundIcon = new ImageIcon(getClass().getResource(path));
                g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

    }
    private void configurePanel(JPanel panel, int x, int y, int width, int height) {
        panel.setBounds(x, y, width, height);
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    public JFrame getParentFrame() {
        return parentFrame;
    }
}
