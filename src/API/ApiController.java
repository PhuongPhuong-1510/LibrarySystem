package API;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApiController implements ActionListener {
    private ApiView apiView;

    public ApiController(ApiView apiView) {
        this.apiView = apiView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

            String query = apiView.getSearchQuery();
        // Kiểm tra nếu `query` trống
             if (query == null || query.trim().isEmpty()) {
                 JOptionPane.showMessageDialog(null, "Please enter a keyword to search.",
                    "Empty Search Query", JOptionPane.WARNING_MESSAGE);
                  return;
            }


        apiView.searchBooks(query);
//        }
    }
}
