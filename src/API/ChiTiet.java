package API;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;

public class ChiTiet extends JPanel {

    private static final long serialVersionUID = 1L;

    public ChiTiet(String title, String author, String language, String category, String thumbnailUrl) {
        setLayout(null);

        JPanel image_panel = new JPanel();
        image_panel.setBounds(31, 67, 319, 438);
        add(image_panel);
        try {
            URL url = new URL(thumbnailUrl);
            ImageIcon imageIcon = new ImageIcon(url);
            Image image = imageIcon.getImage().getScaledInstance(371, 515, Image.SCALE_SMOOTH);
            JLabel lblImage = new JLabel(new ImageIcon(image));
            image_panel.add(lblImage);
        } catch (Exception e) {
            JLabel lblImage = new JLabel("No Image Available");
            image_panel.add(lblImage);
            e.printStackTrace();
        }

        JLabel lblTitle = new JLabel(title+"");
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

    }
}
