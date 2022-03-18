package ser.quinnipiac.edu.harrypottercharacters.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.util.HashMap;

/**
 * @author Thomas Kwashnak
 */
public class LoadImageTask extends AsyncTask<String,Void, Bitmap> {


    private final LoadImageListener listener;

    public LoadImageTask(LoadImageListener listener) {
        this.listener = listener;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        //Attempts to load a bitmap from a url stream
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
            //If there was a result, send the bitmap to the listener
            listener.onLoadImage(result);
        }
    }

    /**
     * @author Thomas Kwashnak
     * Allows the ability to be used as a listener for a LoadImageTask
     */
    public interface LoadImageListener {
        void onLoadImage(Bitmap bitmap);
    }
}
