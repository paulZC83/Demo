package cx.sh.cn.designdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZengChao on 2016/8/29.
 */
public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private List<ArrayList<String>> list;

    public MyViewPagerAdapter(FragmentManager fm, List<ArrayList<String>> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return MyFragment1.newInstance(list.get(position));
        } else if (position == 1) {
            return MyFragment2.newInstance(list.get(position));
        } else {
            return MyFragment3.newInstance(list.get(position));
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "新闻";
        } else if(position == 1) {
            return "综合";
        } else {
            return "频道";
        }
    }
}
