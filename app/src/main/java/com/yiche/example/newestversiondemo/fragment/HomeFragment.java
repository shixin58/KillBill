package com.yiche.example.newestversiondemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yiche.example.newestversiondemo.R;
import com.yiche.example.newestversiondemo.activity.AnimationActivity;
import com.yiche.example.newestversiondemo.activity.RecyclerViewActivity;
import com.yiche.example.newestversiondemo.activity.TestPlatformActivity;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>Created by shixin on 2018/4/15.
 */
public class HomeFragment extends Fragment {

    @BindView(R.id.input)
    EditText mTextInput;
    @BindView(R.id.chronometer)
    Chronometer mChronometer;
    @BindString(R.string.app_name)
    String name;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String input = getActivity().getPreferences(Context.MODE_PRIVATE)
                .getString("input", "");
        mTextInput.setText(input, TextView.BufferType.NORMAL);
    }

    @Override
    public void onDestroyView() {
        String input = mTextInput.getText().toString();
        getActivity().getPreferences(Context.MODE_PRIVATE)
                .edit()
                .putString("input", input)
                .apply();
        mChronometer.stop();
        super.onDestroyView();
    }

    @OnClick(R.id.chronometer)
    void onChronometerClick(View view){
        ((Chronometer)view).start();
    }

    @OnClick(R.id.button)
    void onCreateBugClick(){
        //Log.i("Victor", ""+name);
        //Toast.makeText(this, ""+name, Toast.LENGTH_LONG).show();
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.view_serial_follow_popup_window, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.showAtLocation(mChronometer, Gravity.RIGHT|Gravity.BOTTOM, 200, 200);
        popupView.findViewById(R.id.ivHide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
//        popupWindow.setTouchable(true);
//        popupWindow.setOutsideTouchable(false);
        popupWindow.showAsDropDown(mChronometer, 10, 10);
//        if(Build.VERSION.SDK_INT>=21) {
//            popupWindow.setElevation(5f);
//        }
    }

    @OnClick(R.id.button_animation)
    void onAnimationClick() {
        AnimationActivity.openActivity(getActivity());
    }

    @OnClick(R.id.button_recycler_view)
    void onRecyclerViewClick() {
        RecyclerViewActivity.openActivity(getActivity());
    }

    @OnClick(R.id.button_fragment)
    void onFragmentClick(){
        TestPlatformActivity.Companion.openActivity(getActivity());
    }
}
