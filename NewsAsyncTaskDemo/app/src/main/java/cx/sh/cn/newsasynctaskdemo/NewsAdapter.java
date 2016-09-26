package cx.sh.cn.newsasynctaskdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ZengChao on 2016/9/26.
 */
public class NewsAdapter extends BaseAdapter implements AbsListView.OnScrollListener {
    private Context mContext;
    private List<NewsBean> mList;
    private ImageLoader mLoader;
    private ListView mListView;
    // listview可见项的开始位置
    private int mStart;
    // listview可见项的终了位置
    private int mEnd;
    public static String[] URLS;
    // 第一次加载标志位
    private boolean mFirstIn;
    public NewsAdapter(Context context, List<NewsBean> list, ListView listView) {
        this.mFirstIn = true;
        this.mContext = context;
        this.mList = list;
        this.mListView = listView;
        URLS = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            URLS[i] = list.get(i).getPicUrl();
        }
        mLoader = new ImageLoader(mListView);
        mListView.setOnScrollListener(this);
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null) {
            vh = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_news, viewGroup, false);
            vh.iv_icon = ((ImageView) view.findViewById(R.id.image));
            vh.tv_content = ((TextView) view.findViewById(R.id.content));
            vh.tv_title = ((TextView) view.findViewById(R.id.title));
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
//        vh.iv_icon.setImageResource(R.mipmap.ic_launcher);
        vh.tv_title.setText(mList.get(i).getTitle());
        vh.tv_content.setText(mList.get(i).getContent());
        vh.iv_icon.setTag(mList.get(i).getPicUrl());
        mLoader.loadImage(vh.iv_icon, mList.get(i).getPicUrl());
        return view;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        if (i == SCROLL_STATE_IDLE) {
            // 没有滑动时加载图片
            mLoader.loadImageOnScroll(mStart, mEnd);
        } else {
            // 停止任务
            mLoader.cancelAllTasks();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        mStart = firstVisibleItem;
        mEnd = firstVisibleItem +visibleItemCount;
        if (mFirstIn && visibleItemCount > 0) {
            // 初始化时第一次加载
            mLoader.loadImageOnScroll(mStart, mEnd);
            mFirstIn = false;
        }
    }

    private class ViewHolder {
        private TextView tv_title,tv_content;
        private ImageView iv_icon;
    }
}
