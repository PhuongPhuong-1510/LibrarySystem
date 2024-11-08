package API;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookParser {

    public static List<String[]> getBookDetails(String jsonResponse) {
        List<String[]> bookDetails = new ArrayList<>();

        if (jsonResponse == null || jsonResponse.isEmpty()) {
            return bookDetails;
        }

        JSONObject jsonObject = new JSONObject(jsonResponse);
        if (jsonObject.has("items")) {
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject volumeInfo = itemsArray.getJSONObject(i).getJSONObject("volumeInfo");

                String title = volumeInfo.optString("title", "No Title Available");
                String authors = volumeInfo.has("authors") ?
                        volumeInfo.getJSONArray("authors").toList().stream()
                                .map(Object::toString)
                                .collect(Collectors.joining(", ")) :
                        "Unknown Author";
                String language = volumeInfo.optString("language", "N/A");
                String categories = volumeInfo.has("categories") ?
                        volumeInfo.getJSONArray("categories").toList().stream()
                                .map(Object::toString)
                                .collect(Collectors.joining(", ")) :
                        "Unknown Genre";

                // Extract thumbnail URL
                String thumbnail = volumeInfo.has("imageLinks") ?
                        volumeInfo.getJSONObject("imageLinks").optString("thumbnail", "") :
                        "";

                // Store book details as an array of strings
                bookDetails.add(new String[]{title, authors, language, categories, thumbnail});
            }
        }

        return bookDetails;
    }
}
