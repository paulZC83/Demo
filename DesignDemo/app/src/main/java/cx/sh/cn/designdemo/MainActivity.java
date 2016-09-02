package cx.sh.cn.designdemo;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabLayout tab = (TabLayout) findViewById(R.id.tab);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView toolbarImag = (ImageView) findViewById(R.id.toolbar_img);
        toolbarImag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "点击自定义toolbar按钮", Toast.LENGTH_SHORT).show();
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), getData()));
        tab.setupWithViewPager(viewPager);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(this);

        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sigle_1:

                break;
            case R.id.item_4:
                finish();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        Snackbar.make(view, "撤销", Snackbar.LENGTH_LONG)
                .setAction("点我撤销", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, CoordinatorActivity.class));
                    }
                }).show();
    }

    private List<ArrayList<String>> getData() {
        List<ArrayList<String>> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ArrayList<String> ll = new ArrayList<>();
            for (int j = 0; j < 100; j++) {
                ll.add(String.format(Locale.CHINA,"第%02d页%03d条数据", i, j));
            }
            list.add(ll);
        }
        return list;
    }
}
