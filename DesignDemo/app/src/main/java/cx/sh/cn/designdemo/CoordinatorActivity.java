package cx.sh.cn.designdemo;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CoordinatorActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
        findViewById(R.id.dhh).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Snackbar.make(view, "邓哈哈拆解", Snackbar.LENGTH_LONG).show();
    }
}
