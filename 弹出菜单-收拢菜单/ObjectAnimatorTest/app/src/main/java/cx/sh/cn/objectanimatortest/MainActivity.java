package cx.sh.cn.objectanimatortest;

import android.animation.ObjectAnimator;
import android.support.annotation.InterpolatorRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    int[] images = {R.id.h,R.id.g,R.id.f,R.id.e
            ,R.id.d,R.id.c,R.id.b,R.id.a};
    List<ImageView> imageViewList= new ArrayList<ImageView>();

    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < images.length; i++) {
            ImageView imageView = (ImageView) findViewById(images[i]);
            imageView.setOnClickListener(this);
            imageViewList.add(imageView);
        }

    }

    private void startAnimator() {
        for (int i = 1; i < images.length; i++) {
            ObjectAnimator ob = ObjectAnimator.ofFloat(imageViewList.get(i), "translationY",0F,i*220);
            ob.setStartDelay(i*300);
            ob.setDuration(500);
            ob.setInterpolator(new BounceInterpolator());
            ob.start();

        }
        flag = false;
    }

    private  void closeAnimator() {
        for (int i = 1; i < images.length; i++) {
            ObjectAnimator ob = ObjectAnimator.ofFloat(imageViewList.get(i), "translationY",i*220,0F);
            ob.setStartDelay(i*300);
            ob.setDuration(500);
            new BounceInterpolator();
            ob.start();

        }
        flag = true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.h:
                if (flag) {
                    startAnimator();
                } else {
                    closeAnimator();
                }

                break;
            default:
                Toast.makeText(MainActivity.this,"Id::"+view.getId(),Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
