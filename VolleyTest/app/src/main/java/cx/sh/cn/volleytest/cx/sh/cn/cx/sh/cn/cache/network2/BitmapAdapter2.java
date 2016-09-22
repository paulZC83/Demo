package cx.sh.cn.volleytest.cx.sh.cn.cx.sh.cn.cache.network2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import cx.sh.cn.volleytest.MyApplication;
import cx.sh.cn.volleytest.R;
import cx.sh.cn.volleytest.cx.sh.cn.cx.sh.cn.cache.network.BitmapCache;

/**
 * Created by ZengChao on 2016/9/22.
 */
public class BitmapAdapter2 extends BaseAdapter {

    private final ImageLoader loader;
    private final List<String> list;
    private final Context mContext;


    public BitmapAdapter2(Context context, List<String> urlList){
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
            v = LayoutInflater.from(mContext).inflate(R.layout.cache_image_item2,viewGroup , false);
        } else {
            v = view;
        }
        NetworkImageView img = ((NetworkImageView) v.findViewById(R.id.item_image2));
        img.setDefaultImageResId(R.mipmap.ic_launcher);
        img.setErrorImageResId(R.mipmap.ic_launcher);
        img.setImageUrl(list.get(i), loader);
        return v;
    }
}
