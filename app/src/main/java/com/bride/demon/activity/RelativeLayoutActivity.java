package com.bride.demon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bride.ui_lib.BaseActivity;
import com.bride.demon.R;
import com.bride.demon.adapter.RelativeLayoutAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <p>Created by shixin on 2019/4/14.
 */
public class RelativeLayoutActivity extends BaseActivity {
    private RecyclerView mRecyclerView;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, RelativeLayoutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relativelayout);
        initView();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        RelativeLayoutAdapter adapter = new RelativeLayoutAdapter();
        mRecyclerView.setAdapter(adapter);

        List<String> list = new ArrayList<>();
        list.add(getResources().getString(R.string.tv_1));
        list.add(getResources().getString(R.string.tv_2));
        list.add(getResources().getString(R.string.tv_3));
        adapter.setList(list);
    }
}
