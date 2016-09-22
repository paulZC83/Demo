package cx.sh.cn.handledemo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MyRunnable mMyRunnable = new MyRunnable();
    int index = -1;
    private ImageView imageView;

    int[] imageSource = {R.drawable.demo1, R.drawable.demo2, R.drawable.demo3, R.drawable.demo6, R.drawable.demo9};
    private EditText edit;
    private Button bt;
    private Button bt2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.image);
        edit = (EditText) findViewById(R.id.edit);
        bt = (Button) findViewById(R.id.bt);
        bt.setOnClickListener(this);
        bt2 = (Button) findViewById(R.id.bt2);
        bt2.setOnClickListener(this);

    }


    private Handler mHandler = new Handler(new Handler.Callback() {
        // Handler中new了一个Callback,当Callback中重写的handleMessage返回true时，Handler自己的重写方法（第二个handleMessage方法）不会被执行，反之返回false，则第二个handleMessage方法会继续被回调
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                mHandler.removeCallbacks(mMyRunnable);
                return true;
            }
            return false;
        }
    }) {
        @Override
        public void handleMessage(Message msg) {
            mHandler.postDelayed(mMyRunnable, 1000);
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt:
                // 输入1和1以外的数值区别发送消息
                if ("1".equals(edit.getText().toString())) {
                    mHandler.sendEmptyMessage(1);
                } else {
                    mHandler.sendEmptyMessage(0);
                }

                break;
            case R.id.bt2:
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
                break;
        }


    }

    private class MyRunnable implements Runnable {

        @Override
        public void run() {
            index++;
            index = index % 5;
            // postDelayed中传入的Runnable为本Runnable自己，以实现递归调用
            mHandler.postDelayed(mMyRunnable, 1000);
            imageView.setImageResource(imageSource[index]);
        }
    }

}
