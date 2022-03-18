package ser.quinnipiac.edu.harrypottercharacters.async;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ser.quinnipiac.edu.harrypottercharacters.data.Character;

/**
 * @author Thomas Kwashnak
 */
public class FetchCharactersTask extends AsyncTask<String,Void, Collection<Character>> {

    //Base url
    private static final String BASE_URL = "https://hp-api.herokuapp.com/api";

    private final FetchCharactersListener listener;

    public FetchCharactersTask(FetchCharactersListener listener) {
        this.listener = listener;
    }

    @Override
    protected Collection<Character> doInBackground(String... strings) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        StringBuffer jsonString = new StringBuffer();

        try {
            //Loads the data from the api to the jsonString
            URL url = new URL(BASE_URL + "/" + String.join("/",strings));
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            InputStream stream = urlConnection.getInputStream();

            if(stream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(stream));

            String line;

            while((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            reader.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
        //Returns the Character Collection from the jsonString
        return convertJson(jsonString.toString());
    }

    @Override
    protected void onPostExecute(Collection<Character> people) {
        super.onPostExecute(people);

        //If there is a list of people, and there is a listener
        if(people != null && listener != null) {
            //Tell the listener that we have people!
            listener.onFetchCharacters(people);
        }
    }

    /**
     * Attempts to convert a json string to a collection of characters
     * @param json String json
     * @return Collection of Characters, null if an exception was reached
     */
    private Collection<Character> convertJson(String json) {
        try {
            Collection<Character> characters = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                characters.add(new Character(object));
            }

            return characters;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @author Thomas Kwashnak
     * Allows the ability to be used as a listener for a FetchCharacterTask
     */
    public interface FetchCharactersListener {
        void onFetchCharacters(Collection<Character> list);
    }
}
