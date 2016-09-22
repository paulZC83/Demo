package cx.sh.cn.volleytest.cx.sh.cn.volley.custom.made;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

import cx.sh.cn.volleytest.MyApplication;

/**
 * Created by ZengChao on 2016/9/21.
 */
public class VolleyRequest {
    public Context mContext;
    public static StringRequest mRequest;

    // GET请求
    public static void requestGet(String url, String tag,VolleyListener vif) {
        MyApplication.getHttpQueue().cancelAll(tag);

        mRequest = new StringRequest(Request.Method.GET,url,vif.loadListener(),vif.errorListener());
        mRequest.setTag(tag);
        MyApplication.getHttpQueue().add(mRequest);
//        MyApplication.getHttpQueue().start();
    }

    // POST请求
    public static void requestPost(String url, String tag, final Map<String,String> map, VolleyListener vif) {
        MyApplication.getHttpQueue().cancelAll(tag);
        mRequest = new StringRequest(Request.Method.POST,url,vif.loadListener(),vif.errorListener())
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        mRequest.setTag(tag);
        MyApplication.getHttpQueue().add(mRequest);

    }


}
