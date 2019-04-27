package com.ubook.ubookapp.notify;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ubook.ubookapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotifyFragment extends Fragment {

    @BindView(R.id.rvNotify)
    RecyclerView rvNotify;
    @BindView(R.id.tbNotify)
    Toolbar tbNotify;

    //
    NotifyAdapter notifyAdapter;
    private List<NotifyData> notifyDatas;


    public NotifyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notify, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        notifyDatas = new ArrayList<>();
        notifyDatas.add(new NotifyData(R.drawable.icon_notify, "Notify 1", "Body of notify 1"));
        notifyDatas.add(new NotifyData(R.drawable.icon_notify, "Notify 2", "Body of notify 2"));
        notifyDatas.add(new NotifyData(R.drawable.icon_notify, "Notify 3", "Body of notify 3"));
        notifyDatas.add(new NotifyData(R.drawable.icon_notify, "Notify 4", "Body of notify 4"));
        notifyDatas.add(new NotifyData(R.drawable.icon_notify, "Notify 5", "Body of notify 5"));
        notifyDatas.add(new NotifyData(R.drawable.icon_notify, "Notify 6", "Body of notify 6"));
        notifyDatas.add(new NotifyData(R.drawable.icon_notify, "Notify 7", "Body of notify 7"));
        notifyDatas.add(new NotifyData(R.drawable.icon_notify, "Notify 8", "Body of notify 8"));
        notifyDatas.add(new NotifyData(R.drawable.icon_notify, "Notify 9", "Body of notify 9"));
        notifyDatas.add(new NotifyData(R.drawable.icon_notify, "Notify 10", "Body of notify 10"));
        //
        initView();
    }

    private void initView() {
        rvNotify.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvNotify.setLayoutManager(linearLayoutManager);
        notifyAdapter = new NotifyAdapter(notifyDatas, getActivity());
        rvNotify.setAdapter(notifyAdapter);
        notifyAdapter.setListener((notifyData, position) -> {
            Toast.makeText(getActivity(), notifyData.getBody(), Toast.LENGTH_SHORT).show();
        });
    }
}
