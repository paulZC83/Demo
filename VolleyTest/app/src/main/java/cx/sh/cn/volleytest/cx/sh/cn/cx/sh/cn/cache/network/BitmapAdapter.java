package cx.sh.cn.volleytest.cx.sh.cn.cx.sh.cn.cache.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;
import java.util.zip.Inflater;

import cx.sh.cn.volleytest.MyApplication;
import cx.sh.cn.volleytest.R;

/**
 * Created by ZengChao on 2016/9/22.
 */
public class BitmapAdapter extends BaseAdapter {

    private final ImageLoader loader;
    private final List<String> list;
    private final Context mContext;


    public BitmapAdapter(Context context, List<String> urlList){
        loader = new ImageLoader(MyApplication.getHttpQueue(),new BitmapCache());
        list = urlList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v;
        if (view == null){
            v = LayoutInflater.from(mContext).inflate(R.layout.cache_image_item,viewGroup , false);
        } else {
            v = view;
        }
        ImageView img = ((ImageView) v.findViewById(R.id.item_image));
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(img,R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        loader.get(list.get(i), imageListener);
        return v;
    }
}
