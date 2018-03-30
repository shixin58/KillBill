package com.yiche.example.newestversiondemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yiche.example.newestversiondemo.utils.SerialFollowAnimationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimationActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.root_inventory)
    View rootInventory;
    @BindView(R.id.button_inventory)
    TextView buttonInventory;
    @BindView(R.id.iv_point)
    ImageView ivPoint;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, AnimationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        AnimationAdapter animationAdapter = new AnimationAdapter(this);
        listView.setAdapter(animationAdapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_TOUCH_SCROLL:
                        break;
                    case SCROLL_STATE_FLING:
                        break;
                    case SCROLL_STATE_IDLE:
                        SerialFollowAnimationUtils.collapseInventory(rootInventory, 0);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        rootInventory.setOnClickListener(this);
        SerialFollowAnimationUtils.collapseInventory(rootInventory, 1200);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.flFollow:
                // 圆角矩形收缩成圆0.1s -> 小蓝球掉入清单0.2s
                // 清单入口展开 -> 0.3s
                SerialFollowAnimationUtils.shrinkRoundRectangle(view);
                SerialFollowAnimationUtils.throwBall(view, rootInventory, ivPoint);
                SerialFollowAnimationUtils.expandInventory(rootInventory);
                break;
            case R.id.root_inventory:
                if(buttonInventory.getVisibility()==View.GONE) {
                    SerialFollowAnimationUtils.expandInventory(rootInventory);
                }else {
                    Toast.makeText(this, "打开清单页", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
