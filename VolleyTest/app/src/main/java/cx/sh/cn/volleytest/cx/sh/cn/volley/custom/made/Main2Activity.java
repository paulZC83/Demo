package cx.sh.cn.volleytest.cx.sh.cn.volley.custom.made;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

import cx.sh.cn.volleytest.R;

public class Main2Activity extends AppCompatActivity {

    private EditText ed2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ed2 = (EditText) findViewById(R.id.phone_ed2);
        ed2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(ed2.getText().toString().length() == 11) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            startGet(ed2.getText().toString());
                        }
                    }).start();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }

    private void startGet(String nm){
        String url ="http://apis.juhe.cn/mobile/get?phone="+nm+"&key=15c0642a1e6ed69b481d551f6400bc93";
        // GET请求
//        VolleyRequest.requestGet(url, "GetCustomMadeVolley", new VolleyListener() {
//            @Override
//            public void mySuccess(String result) {
//                Toast.makeText(Main2Activity.this, result, Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void myError(VolleyError volleyError) {
//                Toast.makeText(Main2Activity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
//                Log.d("zc---", volleyError.toString());
//            }
//        });

        // POST请求
        Map<String,String> map = new HashMap<>();
        map.put("phone", ed2.getText().toString());
        map.put("key", "15c0642a1e6ed69b481d551f6400bc93");
        VolleyRequest.requestPost(url, "GetCustomMadeVolley", map, new VolleyListener() {
            @Override
            public void mySuccess(String result) {
                Toast.makeText(Main2Activity.this, result, Toast.LENGTH_LONG).show();
            }

            @Override
            public void myError(VolleyError volleyError) {
                Toast.makeText(Main2Activity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                Log.d("zc---", volleyError.toString());
            }
        });
    }
}
