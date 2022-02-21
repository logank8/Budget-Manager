package persistence;

import model.Budget;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    //  throws IOException if an error occurs reading data from file
    public Budget read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBudget(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses budget from JSON object and returns it
    private Budget parseBudget(JSONObject jsonObject) {
        Budget b = new Budget();
        int income = jsonObject.getInt("income");
        addRanges(b, jsonObject);
        addAmounts(b, jsonObject);
        return b;
    }

    // MODIFIES: b
    // EFFECTS: parses ranges from JSON object and adds them to budget
    private void addRanges(Budget b, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("ranges");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addRange(b, jsonObject);
        }
    }

    // MODIFIES: b
    // EFFECTS: parses range from JSON object and adds to budget
    private void addRange(Budget b, JSONObject jsonObject) {
        int low = jsonObject.getInt("low");
        int high = jsonObject.getInt("high");
    }

    private void addAmounts(Budget b, JSONObject jsonObject) {

    }
}
