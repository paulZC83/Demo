package cx.sh.cn.newsasynctaskdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private final String url = "http://www.imooc.com/api/teacher?type=4&num=30";

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = ((ListView) findViewById(R.id.listview));
        // 根据URL执行AsyncTask获取JSON数据
        NewsAsyncTask task = new NewsAsyncTask(this, mListView);
        task.execute(url);
    }
}
