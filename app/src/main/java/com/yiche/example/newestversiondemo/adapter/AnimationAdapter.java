package com.yiche.example.newestversiondemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.yiche.example.newestversiondemo.R;

/**
 * <p>Created by shixin on 2018/2/8.
 */
public class AnimationAdapter extends BaseAdapter {

    private String[] strings = new String[]{"dog", "cat", "tiger", "lion", "panda",
            "lobster", "crab", "shellfish", "rat", "dragon fruit"};
    private View.OnClickListener mOnClickListener;

    public AnimationAdapter(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @Override
    public int getCount() {
        return strings.length;
    }

    @Override
    public String getItem(int i) {
        return strings[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_animation, viewGroup, false);
        }
        TextView textView = view.findViewById(R.id.tvTitle);
        textView.setText(strings[i]);
        View flFollow = view.findViewById(R.id.flFollow);
        flFollow.setOnClickListener(mOnClickListener);
        return view;
    }
}
