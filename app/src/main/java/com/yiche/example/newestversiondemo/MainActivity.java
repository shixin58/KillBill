package com.yiche.example.newestversiondemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.yiche.example.newestversiondemo.fragment.DashboardFragment;
import com.yiche.example.newestversiondemo.fragment.HomeFragment;
import com.yiche.example.newestversiondemo.fragment.NotificationsFragment;

public class MainActivity extends AppCompatActivity {

    private Fragment[] fragments = new Fragment[3];
    private int mIndex;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction = getSupportFragmentManager().beginTransaction();
                    if(fragments[0]==null) {
                        fragments[0] = HomeFragment.newInstance();
                        transaction.add(R.id.container_fragment, fragments[0]);
                    }
                    transaction.show(fragments[0]);
                    if(mIndex!=0) {
                        transaction.hide(fragments[mIndex]);
                        mIndex=0;
                    }
                    transaction.commitAllowingStateLoss();
                    return true;
                case R.id.navigation_dashboard:
                    transaction = getSupportFragmentManager().beginTransaction();
                    if(fragments[1]==null) {
                        fragments[1] = DashboardFragment.newInstance();
                        transaction.add(R.id.container_fragment, fragments[1]);
                    }
                    transaction.show(fragments[1]);
                    if(mIndex!=1) {
                        transaction.hide(fragments[mIndex]);
                        mIndex=1;
                    }
                    transaction.commitAllowingStateLoss();
                    return true;
                case R.id.navigation_notifications:
                    transaction = getSupportFragmentManager().beginTransaction();
                    if(fragments[2]==null) {
                        fragments[2] = NotificationsFragment.newInstance();
                        transaction.add(R.id.container_fragment, fragments[2]);
                    }
                    transaction.show(fragments[2]);
                    if(mIndex!=2) {
                        transaction.hide(fragments[mIndex]);
                        mIndex=2;
                    }
                    transaction.commitAllowingStateLoss();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
    }
}
