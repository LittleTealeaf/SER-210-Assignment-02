package ser.quinnipiac.edu.harrypottercharacters.util;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Cache Implementation adapted from https://developer.android.com/topic/performance/graphics/cache-bitmap
 */
public class BitmapCache extends LruCache<String, Bitmap> {

    public BitmapCache() {
        super((int) (Runtime.getRuntime().maxMemory() / 1024) / 8);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getByteCount() / 1024;
    }
}
