package cx.sh.cn.newsasynctaskdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ZengChao on 2016/9/26.
 */
public class ImageLoader {
    LruCache<String, Bitmap> mCache;
    private ListView mListView;
    private Set<GetBitmapAsyncTask> mTasks;
    public ImageLoader(ListView listview) {
        this.mListView = listview;
        mTasks = new HashSet<>();
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int maxCache = maxMemory /4;
        mCache = new LruCache<String, Bitmap>(maxCache){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    /*
     * 默认加载图片
     */
    public void loadImage(ImageView iv, String url){
        if (getBitmapFromCache(url) != null) {
            iv.setImageBitmap(getBitmapFromCache(url));
        } else {
//            GetBitmapAsyncTask getBitmapAsyncTask = new GetBitmapAsyncTask();
//            getBitmapAsyncTask.execute(url);
            iv.setImageResource(R.mipmap.ic_launcher);
        }
    }

    /*
     * 滑动完成后需要加载的图片
     */
    public void loadImageOnScroll(int start , int end){
        for (int i = start; i < end; i++) {
            String url = NewsAdapter.URLS[i];
            // 缓存中存在时，从缓存中读取设置
            if (getBitmapFromCache(url) != null) {
                ImageView iv = (ImageView) mListView.findViewWithTag(url);
                iv.setImageBitmap(getBitmapFromCache(url));
            } else {
                // 缓存中没有时启动任务获取图片
                GetBitmapAsyncTask getBitmapAsyncTask = new GetBitmapAsyncTask();
                getBitmapAsyncTask.execute(url);
                mTasks.add(getBitmapAsyncTask);
            }
        }

    }

    /*
     * 取消所有任务
     */
    public void cancelAllTasks() {
        if (mTasks != null) {
            for (GetBitmapAsyncTask task : mTasks) {
                task.cancel(false);
            }
        }

    }

    /*
     * 从缓存中获取bitmap图片
     */
    private Bitmap getBitmapFromCache(String url) {
        return mCache.get(url);
    }

    /*
     * 把bitmap图片加入缓存
     */
    private void addBitmapToCache(Bitmap bitmap, String url) {
        if (getBitmapFromCache(url) == null) {
            mCache.put(url, bitmap);
        }
    }

    private class GetBitmapAsyncTask extends AsyncTask<String, Void, Bitmap>{

        String url;

        @Override
        protected Bitmap doInBackground(String... strings) {
            url = strings[0];
            return getBitmapFromUrl(strings[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ImageView iv = (ImageView) mListView.findViewWithTag(url);
            if (iv.getTag().equals(url)) {
                iv.setImageBitmap(bitmap);
                // 加入缓存
                addBitmapToCache(bitmap, url);
                mTasks.remove(this);
            }
        }
    }

    /*
     * 根据URL获取bitmap
     */
    private Bitmap getBitmapFromUrl(String strUrl){
        try {
            URL url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
