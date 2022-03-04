package ser.quinnipiac.edu.harrypottercharacters.data;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class FetchPersonList extends AsyncTask<String,Void, List<Person>> {

    public FetchPersonList() {

    }

    @Override
    protected List<Person> doInBackground(String... strings) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String json = null;

        try {
            URL url = new URL("http://hp-api.herokuapp.com/api/characters/" + strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            InputStream stream = urlConnection.getInputStream();
            if(stream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(stream));
            json = getStringFromBuffer(reader);
        } catch(Exception e) {

        } finally {
            try {
                if(reader != null) {
                    reader.close();
                }
                if(urlConnection != null) {
                    urlConnection.disconnect();
                }
            } catch(Exception e) {

            }
        }

//
//        return null;
        try {
            return convertJson(json);
        } catch(Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Person> people) {
        super.onPostExecute(people);
    }

    private String getStringFromBuffer(BufferedReader reader) throws JSONException {
        StringBuffer buffer = new StringBuffer();
        String line;

        try {
            while((line = reader.readLine()) != null) {
                buffer.append(line + '\n');
            }
            reader.close();
            return buffer.toString();
        } catch(Exception e) {

        } finally {

        }
        return null;
    }

    private List<Person> convertJson(String json) {
        return null;
    }
}
