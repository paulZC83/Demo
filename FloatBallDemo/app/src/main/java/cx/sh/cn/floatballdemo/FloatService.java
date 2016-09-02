package cx.sh.cn.floatballdemo;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

public class FloatService extends Service
{

    //定义浮动窗口布局
    LinearLayout mFloatLayout;
    WindowManager.LayoutParams wmParams;
    //创建浮动窗口设置布局参数的对象
    WindowManager mWindowManager;

    ImageView mFloatView;
    private  LinearLayout mSencondFloatView;

    private View popupView;
    private PopupWindow popupWindow;
    private int postDelayedTime = 20;

    private int screenWidth;
    private float x;
    private float y;
    //保存悬浮球在左边还是右边
    private boolean  isRight = false;

    //是否触摸悬浮窗
    private boolean isTouch = false;
    //手指是否离开悬浮球
    private boolean isUp;
    private float xStart;

    // popwindow是否显示
    private  boolean popShow = false;

    private static final int MOVE_SLOWLY = 2;
    private int j ;
    private int count;
    private boolean canClick = true;
    //松开手后悬浮球移动的步数
    private int step;
    private float distance;
    //松开手悬浮球移动的频率
    private static final int FREQUENCY = 16;
    private float mTouchX;
    private float mTouchY;
    //保存当前是否为移动模式
    private boolean  isMove = false;
    private float mX;

    private static final String TAG = "FxService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "----onStartCommand---");
        createFloatView();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
        Log.d(TAG, "----oncreat----");

        //Toast.makeText(FxService.this, "create FxService", Toast.LENGTH_LONG);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        Log.d(TAG, "----onBind---");
        // TODO Auto-generated method stub
        return null;
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:

                    break;
                case MOVE_SLOWLY:
                    if(j==count+1){
                        canClick = true;
                    }
                    //根据悬浮球离最近的屏幕边缘的距离来定移动的步数
                    count = (int) (2 * step * Math.abs(distance) / screenWidth); //count/step = distance/(screenWidth/2)
                    if(j <= count){
                        wmParams.x = (int) (xStart-j * distance / count);
                        mWindowManager.updateViewLayout(mFloatLayout, wmParams); // 刷新显示
                        Log.d(TAG,"//////handler------刷新显示--j:"+j+"---count:"+count);
                        j++;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                handler.sendEmptyMessage(MOVE_SLOWLY);
                            }
                        }, FREQUENCY);
                    }
                    break;
            }
        };
    };


    private void updateViewPosition() {
        if(isUp){
            Log.d(TAG,"updateViewPosition...使靠边这个过程更平滑");
            canClick = false;
            //松开手后，悬浮球靠边速度太快，做了个延时，使靠边这个过程更平滑
            distance = xStart- x;
            j = 0;
            wmParams.y = (int) (y - mTouchY);
//			windowManagerParams.windowAnimations = android.R.anim.slide_in_left;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(MOVE_SLOWLY);
                }
            }, FREQUENCY);

        }else{
            Log.d(TAG,"updateViewPosition...刷新显示");
            wmParams.x = (int) (x- mTouchX);
            wmParams.y = (int) (y - mTouchY);
            mWindowManager.updateViewLayout(mFloatLayout, wmParams); // 刷新显示
        }
    }

    private void createFloatView()
    {
        wmParams = new WindowManager.LayoutParams();
        //获取WindowManagerImpl.CompatModeWrapper
        mWindowManager = (WindowManager)getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        //设置window type
        wmParams.type = LayoutParams.TYPE_PHONE;
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags =
//          LayoutParams.FLAG_NOT_TOUCH_MODAL |
                LayoutParams.FLAG_NOT_FOCUSABLE
//          LayoutParams.FLAG_NOT_TOUCHABLE
        ;

        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;

        // 以屏幕左上角为原点，设置x、y初始值
        wmParams.x = 0;
        wmParams.y = 0;

        /*// 设置悬浮窗口长宽数据
        wmParams.width = 200;
        wmParams.height = 80;*/

        if(getApplication().getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE){
            //横屏，规定移动的步数最多为20步
            step = 20;
        }
        else if(getApplication().getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
            //竖屏，规定移动的步数最多为12步
            step = 12;
        }

        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        //获取浮动窗口视图所在布局
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_layout, null);
        //添加mFloatLayout
        mWindowManager.addView(mFloatLayout, wmParams);

        Log.i(TAG, "mFloatLayout-->left" + mFloatLayout.getLeft());
        Log.i(TAG, "mFloatLayout-->right" + mFloatLayout.getRight());
        Log.i(TAG, "mFloatLayout-->top" + mFloatLayout.getTop());
        Log.i(TAG, "mFloatLayout-->bottom" + mFloatLayout.getBottom());

        //浮动窗口按钮
        mFloatView = (ImageView)mFloatLayout.findViewById(R.id.float_id);

        mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        Log.i(TAG, "Width/2--->" + mFloatView.getMeasuredWidth()/2);
        Log.i(TAG, "Height/2--->" + mFloatView.getMeasuredHeight()/2);
        screenWidth = getApplication().getResources().getDisplayMetrics().widthPixels;
        //设置监听浮动窗口的触摸移动
        mFloatView.setOnTouchListener(new OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                isTouch = true;
                isUp = false;
                xStart = 0;
                //System.out.println("statusBarHeight:"+statusBarHeight);
                // 获取相对屏幕的坐标，即以屏幕左上角为原点
                x = event.getRawX();
                y = event.getRawY() ; // statusBarHeight是系统状态栏的高度
                Log.i("tag", "currX" + x + "====currY" + y);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // 捕获手指触摸按下动作
                        // 获取相对View的坐标，即以此View左上角为原点
                        isMove = false;
                        mTouchX = event.getX();
                        mTouchY = event.getY();
                        Log.i("tag", "startX" + mTouchX + "====startY"+ mTouchY);
                        Log.d(TAG,"ACTION_DOWN...");
                        break;
                    case MotionEvent.ACTION_MOVE: // 捕获手指触摸移动动作
                        int xMove = Math.abs((int) (event.getX() - mTouchX));
                        int yMove = Math.abs((int) (event.getY() - mTouchY));
                        Log.d(TAG,"ACTION_MOVE...xMove:"+xMove+"----yMove:"+yMove);
                        if(xMove > 5 || yMove > 5) {
                            //x轴或y轴方向的移动距离大于5个像素，视为拖动，否则视为点击
                            isMove = true;
//                            image.setImageResource(defaultResource);
                            updateViewPosition();
                        }
                        break;
                    case MotionEvent.ACTION_UP: // 捕获手指触摸离开动作
                        Log.d(TAG,"ACTION_UP...");
                        isTouch = false;
                        float halfScreen = screenWidth/2;
                        Log.d(TAG,"ACTION_UP时是否为移动："+isMove);
                        if(isMove) {
                            isUp = true;
                            mX = mTouchX;
                            isMove = false;
                            if (x <= halfScreen) {
                                xStart = x - mTouchX;
                                x = 0;
                                isRight = false;
                            } else {
                                xStart = x;
                                x = screenWidth + mTouchX + mFloatView.getWidth();//为了保证悬浮球靠边隐藏，而且在极限情况下（横屏）不会从屏幕右边突然跳到屏幕左边
                                isRight = true;
                            }
                            Log.d(TAG,"靠右显示吗？"+isRight);
                            updateViewPosition();
                        } else {
                            // 点击事件的处理
                            doOnClick();
                        }
                        mTouchX = mTouchY = 0;
                        Log.d(TAG,"//////popShow:"+popShow);
                        if (popShow) {
                            popShow = false;
                            dismissPopupWindow();
                        }
                        break;
                }

//                // TODO Auto-generated method stub
//                //getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
//                wmParams.x = (int) event.getRawX() - mFloatView.getMeasuredWidth()/2;
//                //Log.i(TAG, "Width/2--->" + mFloatView.getMeasuredWidth()/2);
//                Log.i(TAG, "RawX" + event.getRawX());
//                Log.i(TAG, "X" + event.getX());
//                //25为状态栏的高度
//                wmParams.y = (int) event.getRawY() - mFloatView.getMeasuredHeight()/2 - 25;
//                // Log.i(TAG, "Width/2--->" + mFloatView.getMeasuredHeight()/2);
//                Log.i(TAG, "RawY" + event.getRawY());
//                Log.i(TAG, "Y" + event.getY());
//                //刷新
//                mWindowManager.updateViewLayout(mFloatLayout, wmParams);
                return true;
            }
        });

    }

    private void doOnClick() {
        {

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSencondFloatView = (LinearLayout) View.inflate(getApplication(),R.layout.float_layout, null);
                    mSencondFloatView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dismissPopupWindow();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (mSencondFloatView != null) {
                                        mWindowManager.removeViewImmediate(mSencondFloatView);
                                    }
                                    mSencondFloatView = null;
                                }
                            }, 200);

                        }
                    });
                    mWindowManager.addView(mSencondFloatView, wmParams);

                    if (isRight) {
                        popupView = View.inflate(getApplication(),R.layout.float_popup_window_right, null);
                    } else {
                        popupView = View.inflate(getApplication(),R.layout.float_popup_window_left, null);
                    }

                    initView();
                    popupWindow = new PopupWindow(popupView,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
                    popupWindow.setTouchable(true);
                    // 点击popwindow以外区域，关闭popwindow
                    popupWindow.setFocusable(true);
//                    popupWindow.setBackgroundDrawable(new BitmapDrawable());
//                    popupWindow.setOutsideTouchable(true);
                    if (isRight) {
                        popupWindow.setAnimationStyle(R.style.popupWindowRightAnimation);
                        popupWindow.showAtLocation(mFloatView, Gravity.RIGHT, 0,0);
                    } else {
                        popupWindow.setAnimationStyle(R.style.popupWindowAnimation);
                        popupWindow.showAtLocation(mFloatView, Gravity.LEFT, 0,0);
                    }

                    Log.d(TAG,"点击悬浮球，弹出菜单...");
                    popShow = true;

//                    popupWindow.setTouchInterceptor(new OnTouchListener() {
//                        @Override
//                        public boolean onTouch(View view, MotionEvent motionEvent) {
//                            Log.d(TAG, "popwindow以外点击事件发生...action:"+motionEvent.getAction());
//                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN ||
//                                    motionEvent.getAction() == MotionEvent.ACTION_OUTSIDE) {
//                                dismissPopupWindow();
//                                handler.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        if (mSencondFloatView != null) {
//                                            mWindowManager.removeViewImmediate(mSencondFloatView);
//                                        }
//                                        mSencondFloatView = null;
//                                    }
//                                }, 200);
//                                return true;
//                            }
//                            return false;
//                        }
//                    });

                }
            },postDelayedTime);

        }
    }


    private void dismissPopupWindow() {
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    // popupWindow的布局及点击事件
    private void initView() {
        LinearLayout ll_custom_service = (LinearLayout) popupView
                .findViewById(R.id.ll_custom_service);
        LinearLayout ll_logout = (LinearLayout) popupView
                .findViewById(R.id.ll_logout);
        LinearLayout ll_protect = (LinearLayout) popupView
                .findViewById(R.id.ll_protect);
        ll_custom_service.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "您点击了客服中心按钮", Toast.LENGTH_SHORT).show();
            }
        });
        ll_logout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "您点击了注销按钮", Toast.LENGTH_SHORT).show();
            }
        });
        ll_protect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "您点击了账号保护按钮", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
        if(mFloatLayout != null)
        {
            mWindowManager.removeView(mFloatLayout);
        }
    }

}
