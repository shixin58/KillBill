package com.yiche.example.newestversiondemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.message)
    TextView mTextMessage;
    @BindView(R.id.input)
    EditText mTextInput;
    @BindView(R.id.chronometer)
    Chronometer mChronometer;
    @BindString(R.string.app_name)
    String name;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        String input = getPreferences(Context.MODE_PRIVATE)
                .getString("input", "");
        mTextInput.setText(input, TextView.BufferType.NORMAL);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String input = mTextInput.getText().toString();
        getPreferences(Context.MODE_PRIVATE)
                .edit()
                .putString("input", input)
                .apply();
        mChronometer.stop();
    }

    @OnClick(R.id.chronometer)
    void onChronometerClick(View view){
        ((Chronometer)view).start();
    }

    @OnClick(R.id.button)
    void onCreateBugClick(){
        //Log.i("Victor", ""+name);
        //Toast.makeText(this, ""+name, Toast.LENGTH_LONG).show();
        View popupView = LayoutInflater.from(this).inflate(R.layout.view_popup_window, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.showAtLocation(mChronometer, Gravity.RIGHT|Gravity.BOTTOM, 200, 200);
        popupView.findViewById(R.id.tv_welcome).setOnClickListener(new View.OnClickListener() {
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
}
