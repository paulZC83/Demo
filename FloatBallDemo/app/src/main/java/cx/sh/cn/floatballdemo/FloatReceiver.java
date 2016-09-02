package cx.sh.cn.floatballdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ZengChao on 2016/8/5.
 */
public class FloatReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"收到广播啦。。。",Toast.LENGTH_SHORT).show();
        Intent floatIntent = new Intent(context, FloatService.class);
        context.startService(floatIntent);
        Log.d("FloatReceiver", "收到开机广播，启动服务...");
    }
}
