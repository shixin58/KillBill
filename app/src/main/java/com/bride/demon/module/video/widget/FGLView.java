package com.bride.demon.module.video.widget;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Description:
 */
public class FGLView extends GLSurfaceView {

    private FGLRender mRenderer;

    public FGLView(Context context) {
        this(context,null);
    }

    public FGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        setEGLContextClientVersion(2);
        setRenderer(mRenderer = new FGLRender(this));
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public void setShape(Class<? extends Shape> clazz){
        try {
            mRenderer.setShape(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
