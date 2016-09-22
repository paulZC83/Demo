package cx.sh.cn.volleytest;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by ZengChao on 2016/9/20.
 */
public class MyApplication extends Application {
    public static RequestQueue queues;
    @Override
    public void onCreate() {
        super.onCreate();
        queues = Volley.newRequestQueue(getApplicationContext());
    }
    public static RequestQueue getHttpQueue(){
        return queues;
    }
}
