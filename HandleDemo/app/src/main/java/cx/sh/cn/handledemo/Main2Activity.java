package cx.sh.cn.handledemo;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private Handler MainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(Main2Activity.this,"收到来自子线程发送过来的消息", Toast.LENGTH_LONG).show();
            System.out.println("收到来自子线程发送过来的消息");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        MyThread t1 = new MyThread();
        t1.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.newThreadHandler.sendEmptyMessage(1);

    }

    private class MyThread extends Thread {
        public Handler newThreadHandler;

        @Override
        public void run() {
            Looper.prepare();
            newThreadHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    Toast.makeText(Main2Activity.this,"在子线程中收到来自主线程的消息", Toast.LENGTH_LONG).show();

                    MainHandler.sendEmptyMessage(2);
                    System.out.println("在子线程中收到来自主线程的消息,然后给主线程发送消息");

                }
            };
            Looper.loop();
        }
    }

}
