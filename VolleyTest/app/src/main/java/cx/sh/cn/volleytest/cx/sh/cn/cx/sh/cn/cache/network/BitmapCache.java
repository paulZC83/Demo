package cx.sh.cn.volleytest.cx.sh.cn.cx.sh.cn.cache.network;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

import static com.android.volley.toolbox.ImageLoader.*;


/**
 * Created by ZengChao on 2016/9/22.
 */
public class BitmapCache implements ImageCache {
    private LruCache<String, Bitmap> lruCache;
    private int maxSize = (int) (Runtime.getRuntime().maxMemory() / 8);
    public BitmapCache() {
        lruCache = new LruCache<String, Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };

    }
    @Override
    public Bitmap getBitmap(String key) {
        return lruCache.get(key);
    }

    @Override
    public void putBitmap(String key, Bitmap bitmap) {
        lruCache.put(key, bitmap);
    }
}
