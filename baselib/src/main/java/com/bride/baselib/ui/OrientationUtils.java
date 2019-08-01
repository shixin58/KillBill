package com.bride.baselib.ui;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.provider.Settings;
import android.view.OrientationEventListener;

/**
 * 重力感应使用方法：
 * 1、onCreate() initialize
 * 2、onResume() start
 * 3、onPause()  end
 * 4、Activity#onConfigurationChanged(Configuration) onConfigurationChanged
 */
public class OrientationUtils {

    private Activity mActivity;
    private boolean mOrientationEventListenerSwitch = true;
    private boolean mIsLandscape;
    private OrientationEventListener mOrientationEventListener;

    public OrientationUtils(Activity activity) {
        init(activity, null);
    }

    public OrientationUtils(Activity activity, ChangePageOrientationCallback callback){
        init(activity, callback);
    }

    public void onConfigurationChanged(int orientation) {
        if((orientation == Configuration.ORIENTATION_LANDSCAPE) != mIsLandscape){
            mOrientationEventListenerSwitch = false;
        }
        mIsLandscape = orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public void start() {
        mOrientationEventListener.enable();
    }

    public void end() {
        mOrientationEventListener.disable();
    }

    private void init(final Activity activity, final ChangePageOrientationCallback callback) {
        mActivity = activity;
        mOrientationEventListener = new OrientationEventListener(activity) {
            @Override
            public void onOrientationChanged(int orientation) {
                try {
                    boolean autoRotateOn = (Settings.System.getInt(activity.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0) == 1);
                    if (!autoRotateOn) {
                        return;
                    }
                    if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                        return;
                    }
                    if ((orientation < 30)
                            || (orientation > 150 && orientation < 210)
                            || (orientation > 330)) {
                        //竖屏
                        if(!mIsLandscape){
                            mOrientationEventListenerSwitch = true;
                        }else if(mOrientationEventListenerSwitch) {
                            if(callback != null) {
                                callback.setPortrait();
                            }else {
                                setPortraitDefault();
                            }
                        }
                    } else if ((orientation > 60 && orientation < 120)
                            || (orientation > 240 && orientation < 300)) {
                        //横屏
                        if(mIsLandscape){
                            mOrientationEventListenerSwitch = true;
                        }else if(mOrientationEventListenerSwitch) {
                            if(callback != null) {
                                callback.setLandscape();
                            }else {
                                setLandscapeDefault();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
    }

    public interface ChangePageOrientationCallback {

        void setPortrait();

        void setLandscape();
    }

    public void setPortraitDefault() {
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void setLandscapeDefault() {
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
}
