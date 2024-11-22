package API;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class ChiTiet extends JPanel {
    private static final long serialVersionUID = 1L;

    public ChiTiet(String title, String author, String language, String category, String thumbnailUrl, String inforLink) {
        setLayout(null);

        JPanel imagePanel = new JPanel();
        imagePanel.setBounds(31, 67, 319, 438);
        add(imagePanel);

        try {
            URL url = new URL(thumbnailUrl);
            ImageIcon imageIcon = new ImageIcon(url);
            Image image = imageIcon.getImage().getScaledInstance(319, 438, Image.SCALE_SMOOTH);
            JLabel lblImage = new JLabel(new ImageIcon(image));
            imagePanel.add(lblImage);
        } catch (Exception e) {
            JLabel lblImage = new JLabel("No Image Available");
            imagePanel.add(lblImage);
            e.printStackTrace();
        }

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
        lblTitle.setBounds(379, 67, 611, 68);
        add(lblTitle);

        JLabel lblAuthor = new JLabel("Author: " + author);
        lblAuthor.setFont(new Font("Tahoma", Font.ITALIC, 25));
        lblAuthor.setBounds(379, 145, 507, 68);
        add(lblAuthor);

        JLabel lblLanguage = new JLabel("Language: " + language);
        lblLanguage.setFont(new Font("Tahoma", Font.ITALIC, 25));
        lblLanguage.setBounds(379, 234, 507, 68);
        add(lblLanguage);

        JLabel lblCategory = new JLabel("Category: " + category);
        lblCategory.setFont(new Font("Tahoma", Font.ITALIC, 25));
        lblCategory.setBounds(379, 330, 507, 68);
        add(lblCategory);

        // QR Code Panel
        JPanel qrCodePanel = new JPanel();
        qrCodePanel.setBounds(700, 300, 200, 200);
        add(qrCodePanel);

        try {
            String bookLink = inforLink; // Thay bằng link trực tiếp lấy từ Google Books API
            System.out.println(bookLink);
            BufferedImage qrCodeImage = generateQRCodeImage(bookLink, 200, 200);
            JLabel lblQRCode = new JLabel(new ImageIcon(qrCodeImage));
            qrCodePanel.add(lblQRCode);
        } catch (Exception e) {
            JLabel lblQRCode = new JLabel("QR Code Error");
            qrCodePanel.add(lblQRCode);
            e.printStackTrace();
        }
    }

    private BufferedImage generateQRCodeImage(String text, int width, int height) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
