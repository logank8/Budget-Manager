package persistence;


import org.json.JSONObject;

// object able to write to json
public interface Writable {

    // EFFECTS: returns this as a JSON object
    JSONObject toJson();
}
