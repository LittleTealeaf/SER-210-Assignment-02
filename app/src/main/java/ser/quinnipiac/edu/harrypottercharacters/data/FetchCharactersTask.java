package ser.quinnipiac.edu.harrypottercharacters.data;

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

public class FetchCharactersTask extends AsyncTask<String,Void, Collection<Character>> {

    private static final String BASE_URL = "https://hp-api.herokuapp.com/api";

    private final FetchCharactersListener listener;

    public FetchCharactersTask(FetchCharactersListener listener) {
        this.listener = listener;
    }

    protected String buildURL(String... endpoint) {
        StringBuilder builder = new StringBuilder(BASE_URL);
        for(String string : endpoint) {
            builder.append('/').append(string);
        }
        return builder.toString();
    }

    @Override
    protected Collection<Character> doInBackground(String... strings) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        StringBuffer jsonString = new StringBuffer();

        try {
            URL url = new URL(buildURL(strings));
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
        return convertJson(jsonString.toString());
    }

    @Override
    protected void onPostExecute(Collection<Character> people) {
        super.onPostExecute(people);

        if(people != null) {
            listener.onFetchCharacters(people);
        } else {
            System.out.println("NULL");
        }
    }

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

    public interface FetchCharactersListener {
        void onFetchCharacters(Collection<Character> list);
    }
}
