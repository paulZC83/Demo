package cx.sh.cn.volleytest.cx.sh.cn.load.network;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import cx.sh.cn.volleytest.MyApplication;
import cx.sh.cn.volleytest.R;

public class Main3Activity extends AppCompatActivity {

    private ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        icon = (ImageView) findViewById(R.id.image_icon);

        String url = "http://www.taopic.com/uploads/allimg/120421/107063-12042114025737.jpg";
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                icon.setImageBitmap(bitmap);
            }
        }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                icon.setBackgroundResource(R.mipmap.ic_launcher);
            }
        });
        imageRequest.setTag("imageRequest");
        MyApplication.getHttpQueue().add(imageRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getHttpQueue().cancelAll("imageRequest");
    }
}
