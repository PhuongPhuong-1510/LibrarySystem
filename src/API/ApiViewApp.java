package API;

import javax.swing.JFrame;

public class ApiViewApp {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Api View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 800);
        frame.setLocationRelativeTo(null);

        ApiView apiView = new ApiView();
        frame.getContentPane().add(apiView);
        frame.setVisible(true);
    }
}

