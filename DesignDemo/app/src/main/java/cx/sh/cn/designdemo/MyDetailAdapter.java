package cx.sh.cn.designdemo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by ZengChao on 2016/8/31.
 */
public class MyDetailAdapter extends RecyclerView.Adapter{
    private Context context;
    private List<String> list;

    public MyDetailAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new RecyclerView.ViewHolder(view){};
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TextView) holder.itemView.findViewById(R.id.item_txt)).setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
