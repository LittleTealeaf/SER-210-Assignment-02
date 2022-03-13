package ser.quinnipiac.edu.harrypottercharacters.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.util.HashMap;

public class LoadImageTask extends AsyncTask<String,Void, Bitmap> {


    private final LoadImageListener listener;

    public LoadImageTask(LoadImageListener listener) {
        this.listener = listener;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(strings[0]).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if(result != null) {
            listener.onLoadImage(result);
        }
    }

    public interface LoadImageListener {
        void onLoadImage(Bitmap bitmap);
    }
}
