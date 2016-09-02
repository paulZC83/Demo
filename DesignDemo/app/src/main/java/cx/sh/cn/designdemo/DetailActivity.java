package cx.sh.cn.designdemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by ZengChao on 2016/9/1.
 */
public class DetailActivity extends AppCompatActivity implements View.OnClickListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
//        recyclerView.setAdapter(new MyAdapter(this, getData()));
//        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams();
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        actionBar.setCustomView(R.layout.actionbar);
        //自定义一个布局，并居中
        actionBar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.actionbar, null);
        actionBar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
        Button back = (Button)v.findViewById(R.id.back_bt);
        Button option = (Button) v.findViewById(R.id.option_bt);
        back.setOnClickListener(this);
        option.setOnClickListener(this);


        RecyclerView detaiRecyclerview = (RecyclerView) findViewById(R.id.detai_recyclerview);
        detaiRecyclerview.setAdapter(new MyAdapter(this, getData()));

    }

    private List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int j = 0; j < 100; j++) {
            list.add(String.format(Locale.CHINA,"第%03d条数据", j));
        }
        return list;
    }


    private List<String> getViewPagerData() {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            list.add(String.format(Locale.CHINA, "第%02d页", i));
        }
        return list;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_bt:
                Toast.makeText(this, "点击了返回按钮", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.option_bt:
                Toast.makeText(this, "点击了功能按钮", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
