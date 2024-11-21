package API;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApiController implements ActionListener {
    private ApiView apiView;

    public ApiController(ApiView apiView) {
        this.apiView = apiView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String src = e.getActionCommand();
        if (src.equals("Search")) {
            String query = apiView.getSearchQuery();
            apiView.searchBooks(query);
        }
    }
}
