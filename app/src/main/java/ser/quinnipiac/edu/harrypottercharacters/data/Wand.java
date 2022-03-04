package ser.quinnipiac.edu.harrypottercharacters.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Wand implements Serializable {
//    {"wood":"holly","core":"phoenix feather","length":11}

    private final String wood;
    private final String core;
    private final double length;

    public Wand(JSONObject jsonObject) throws JSONException {
        wood = jsonObject.getString("wood");
        core = jsonObject.getString("core");
        length = jsonObject.getDouble("length");
    }

    public String getWood() {
        return wood;
    }

    public String getCore() {
        return core;
    }

    public double getLength() {
        return length;
    }
}
