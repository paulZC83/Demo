package cx.sh.cn.newsasynctaskdemo;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZengChao on 2016/9/26.
 */
public class NewsAsyncTask extends AsyncTask<String, Void, List<NewsBean>> {
    private Context mContext;
    private ListView mListView;
    public  NewsAsyncTask(Context context, ListView listview){
        this.mContext = context;
        this.mListView = listview;
    }
    @Override
    protected List<NewsBean> doInBackground(String... strings) {
        return getNewsBeanList(strings[0]);
    }

    @Override
    protected void onPostExecute(List<NewsBean> newsBeen) {
        // 任务执行完毕更新UI时，刷新列表
        NewsAdapter adapter = new NewsAdapter(mContext, newsBeen, mListView);
        mListView.setAdapter(adapter);
    }

    /*
     * 把JSON字符串数据转化为Javabean
     */
    private List<NewsBean> getNewsBeanList(String strUrl) {
        List<NewsBean> list = new ArrayList<>();
        String strJson = getStringJsonFromUrl(strUrl);
        if (!"".equals(strJson)) {
            try {
                JSONObject jb = new JSONObject(strJson);
                JSONArray ja = jb.getJSONArray("data");
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject itemJb = ja.getJSONObject(i);
                    NewsBean nb = new NewsBean();
                    nb.setContent(itemJb.getString("description"));
                    nb.setPicUrl(itemJb.getString("picSmall"));
                    nb.setTitle(itemJb.getString("name"));
                    list.add(nb);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return  list;
    }

    /*
     * 根据URL获取JSON字符串数据
     */
    private String getStringJsonFromUrl(String strUrl) {

        HttpURLConnection connection = null;
        String strJson = "";
        try {
            URL url = new URL(strUrl);
            connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null){
                strJson += line;
            }
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        return strJson;
    }
}
