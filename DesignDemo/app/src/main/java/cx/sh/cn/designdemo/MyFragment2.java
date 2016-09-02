package cx.sh.cn.designdemo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment2 extends Fragment {

    public MyFragment2() {
        // Required empty public constructor
    }

    public static MyFragment2 newInstance(ArrayList<String> list) {

        Bundle args = new Bundle();
        args.putStringArrayList("data_list", list);
        MyFragment2 fragment = new MyFragment2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerview2);
        recyclerView.setAdapter(new MyAdapter(getActivity(), getArguments().getStringArrayList("data_list")));

    }
}
