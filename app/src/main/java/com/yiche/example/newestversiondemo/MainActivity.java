package com.yiche.example.newestversiondemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

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

        mTextMessage = findViewById(R.id.message);
        mTextInput = findViewById(R.id.input);
        String input = getPreferences(Context.MODE_PRIVATE)
                .getString("input", "");
        mTextInput.setText(input, TextView.BufferType.NORMAL);
        mChronometer = findViewById(R.id.chronometer);

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
        int i = 1/0;
    }
}
