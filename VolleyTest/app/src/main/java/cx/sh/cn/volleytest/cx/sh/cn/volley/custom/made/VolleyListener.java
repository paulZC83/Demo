package cx.sh.cn.volleytest.cx.sh.cn.volley.custom.made;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by ZengChao on 2016/9/21.
 */
public abstract class VolleyListener {

    public Response.Listener<String> mSuccessListener;
    public Response.ErrorListener mErrorListener;

    public abstract void mySuccess(String result);
    public abstract void myError(VolleyError volleyError);


    public Response.Listener loadListener() {
        mSuccessListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                mySuccess(s);
            }
        };
        return mSuccessListener;
    }

    public Response.ErrorListener errorListener(){
        mErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                myError(volleyError);
            }
        };
        return mErrorListener;
    }

}
