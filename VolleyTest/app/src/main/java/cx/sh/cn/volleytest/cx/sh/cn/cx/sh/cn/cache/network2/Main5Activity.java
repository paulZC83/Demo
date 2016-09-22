package cx.sh.cn.volleytest.cx.sh.cn.cx.sh.cn.cache.network2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cx.sh.cn.volleytest.R;
import cx.sh.cn.volleytest.cx.sh.cn.cx.sh.cn.cache.network.BitmapAdapter;

public class Main5Activity extends AppCompatActivity {

    List<String> urlList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        initData();

        BitmapAdapter2 adapter2 = new BitmapAdapter2(this, urlList);

        ListView listView = (ListView) findViewById(R.id.cache_list2);
        listView.setAdapter(adapter2);
    }

    private void initData(){
        urlList.add("http://www.taopic.com/uploads/allimg/120421/107063-12042114025737.jpg");
        urlList.add("http://pic2016.5442.com:82/2016/0810/7/2.jpg%21960.jpg");
        urlList.add("http://img5.imgtn.bdimg.com/it/u=1204628810,147075003&fm=11&gp=0.jpg");
        urlList.add("http://img3.imgtn.bdimg.com/it/u=3150980150,3281059115&fm=11&gp=0.jpg");
        urlList.add("http://img5.imgtn.bdimg.com/it/u=1205727766,1693092955&fm=11&gp=0.jpg");
        urlList.add("http://img0.imgtn.bdimg.com/it/u=3050595843,265067487&fm=11&gp=0.jpg");
        urlList.add("http://img3.imgtn.bdimg.com/it/u=2571492775,828873446&fm=21&gp=0.jpg");
        urlList.add("http://img3.imgtn.bdimg.com/it/u=1039146727,1495652378&fm=11&gp=0.jpg");
        urlList.add("http://img1.imgtn.bdimg.com/it/u=1804935075,3932884946&fm=11&gp=0.jpg");
        urlList.add("http://img3.imgtn.bdimg.com/it/u=2913287666,2010318971&fm=11&gp=0.jpg");
        urlList.add("http://img2.imgtn.bdimg.com/it/u=3906848609,1140344167&fm=21&gp=0.jpg");
        urlList.add("http://img3.imgtn.bdimg.com/it/u=2387156975,3934216838&fm=21&gp=0.jpg");
        urlList.add("http://img1.imgtn.bdimg.com/it/u=821091578,2871093089&fm=21&gp=0.jpg");
        urlList.add("http://img1.imgtn.bdimg.com/it/u=3129620859,2050677752&fm=21&gp=0.jpg");
        urlList.add("http://img2.imgtn.bdimg.com/it/u=1263921936,2765627702&fm=21&gp=0.jpg");
        urlList.add("http://img3.imgtn.bdimg.com/it/u=1507352961,110419122&fm=21&gp=0.jpg");
        urlList.add("http://img2.imgtn.bdimg.com/it/u=191706040,3122799634&fm=21&gp=0.jpg");
        urlList.add("http://img4.imgtn.bdimg.com/it/u=1642209990,2784453482&fm=21&gp=0.jpg");
        urlList.add("http://img2.imgtn.bdimg.com/it/u=634391529,3602404565&fm=21&gp=0.jpg");
        urlList.add("http://ww2.sinaimg.cn/orj480/02e9e654gw1f80mlj8naqj21z418gu0y.jpg");
    }
}
