package cx.sh.cn.volleytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cx.sh.cn.volleytest.cx.sh.cn.cx.sh.cn.cache.network2.Main5Activity;
import cx.sh.cn.volleytest.cx.sh.cn.load.network.Main3Activity;
import cx.sh.cn.volleytest.cx.sh.cn.cx.sh.cn.cache.network.Main4Activity;
import cx.sh.cn.volleytest.cx.sh.cn.volley.custom.made.Main2Activity;

public class MainActivity extends AppCompatActivity {

    private EditText phoneNm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 主界面中根据电话号码输入长度启动Volley网络请求
        phoneNm = (EditText) findViewById(R.id.phone_ed);
        phoneNm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (phoneNm.getText().toString().length() == 11) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // GET方式请求数据
//                            getPhoneNumAdd(phoneNm.getText().toString());
                            // POST方式请求数据
                            postPhoneNumAdd();
                        }
                    });
                    thread.start();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        // 启动新界面实现自定义Volley架构
        Button customMadBt = (Button) findViewById(R.id.custom_made_volley);
        customMadBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });

        // 启动新界面用ImageRequest实现Volley加载网络图片
        Button loadNetBt = (Button) findViewById(R.id.load_network_pic_volley);
        loadNetBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Main3Activity.class));
            }
        });

        // 启动新界面实现Volley缓存机制加载网络图片---方式一
        Button cacheNetBt1 = (Button) findViewById(R.id.cache_network_pic_volley_1);
        cacheNetBt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Main4Activity.class));
            }
        });

        // 启动新界面实现Volley缓存机制加载网络图片---方式二
        Button cacheNetBt2 = (Button) findViewById(R.id.cache_network_pic_volley_2);
        cacheNetBt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Main5Activity.class));
            }
        });

    }

    // 使用GET请求方式
    private void getPhoneNumAdd(String nm) {
        String url ="http://apis.juhe.cn/mobile/get?phone="+nm+"&key=15c0642a1e6ed69b481d551f6400bc93";

        Log.d("ZC_DEBUG","得到的URL：------" + url);
        // StringRequest方式请求
//        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
//                System.out.println("-----------------onResponse----------------");
//                System.out.println(s);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
//                System.out.println("--------------onErrorResponse-------------------");
//                System.out.println(volleyError.getMessage());
//            }
//        });

        // JsonObjectRequest
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {

                    JSONObject jo = jsonObject.getJSONObject("result");
                   String city = jo.getString("city");
                    Toast.makeText(MainActivity.this, ("该手机号码属于："+city), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("-----------------onResponse----------------");
                System.out.println(jsonObject.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                System.out.println("--------------onErrorResponse-------------------");
                System.out.println(volleyError.getMessage());
            }
        });
        request.setTag("getPhoneNumAdd");
        MyApplication.getHttpQueue().add(request);
    }

    // Post方式请求数据
    private void postPhoneNumAdd(){
        // post方式请求数据时的URL和GET方式相比是没有请求参数的
        String url = "http://apis.juhe.cn/mobile/get?";


        // StringRequest的POST方式请求数据
//        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
//            }
//        }){
//            // Post方式请求数据时需要在getParams方法中设置请求参数的键值对
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<String, String>();
//                map.put("phone", phoneNm.getText().toString());
//                map.put("key", "15c0642a1e6ed69b481d551f6400bc93");
//                return map;
//            }
//        };
//        request.setTag("postPhoneNumAdd");
//        MyApplication.getHttpQueue().add(request);

        // JSONObjectRequest的POST方式请求数据
        Map<String, String> map = new HashMap<String, String>();
                map.put("phone", phoneNm.getText().toString());
                map.put("key", "15c0642a1e6ed69b481d551f6400bc93");
        JSONObject jsonObject = new JSONObject(map);

        CustomRequest  objectRequest = new CustomRequest (Request.Method.POST, url, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Toast.makeText(MainActivity.this, jsonObject.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                System.out.println(volleyError.toString());
            }
        });


        objectRequest.setTag("postPhoneNumAdd");
        MyApplication.getHttpQueue().add(objectRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        MyApplication.getHttpQueue().cancelAll("getPhoneNumAdd");
        MyApplication.getHttpQueue().cancelAll("postPhoneNumAdd");
    }

}
