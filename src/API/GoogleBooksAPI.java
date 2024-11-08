package API;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GoogleBooksAPI {
    public static String searchBooks(String query) {
        try {
            String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=" + query.replace(" ", "+");
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
