package API;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class BookParser {
    public static List<String> getBookTitles(String jsonResponse) {
        List<String> titles = new ArrayList<>();

        if (jsonResponse == null || jsonResponse.isEmpty()) {
            return titles;
        }

        JSONObject jsonObject = new JSONObject(jsonResponse);
        if (jsonObject.has("items")) {
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject volumeInfo = itemsArray.getJSONObject(i).getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                titles.add(title);
            }
        }

        return titles;
    }
}

