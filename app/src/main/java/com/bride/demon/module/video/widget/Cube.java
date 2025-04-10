package com.bride.demon.module.video.widget;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.view.View;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Cube extends Shape {

    private FloatBuffer mVertexBuffer, mColorBuffer;
    private ShortBuffer mIdxBuffer;

    // 顶点着色器
    private final String mVertexShaderCode =
            "attribute vec4 vPosition;" +
                    "uniform mat4 vMatrix;"+
                    "varying  vec4 vColor;"+
                    "attribute vec4 aColor;"+
                    "void main() {" +
                    "  gl_Position = vMatrix*vPosition;" +
                    "  vColor=aColor;"+
                    "}";

    // 片元着色器
    private final String mFragmentShaderCode =
            "precision mediump float;" +
                    "varying vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private int mProgram;

    final int COORDS_PER_VERTEX = 3;

    // 8个顶点位置(x,y,z)
    final float[] mCubePositions = {
            -1.0f, 1.0f, 1.0f,    // 正面左上0
            -1.0f, -1.0f, 1.0f,   // 正面左下1
            1.0f, -1.0f, 1.0f,    // 正面右下2
            1.0f, 1.0f, 1.0f,     // 正面右上3
            -1.0f, 1.0f, -1.0f,    // 反面左上4
            -1.0f, -1.0f, -1.0f,   // 反面左下5
            1.0f, -1.0f, -1.0f,    // 反面右下6
            1.0f, 1.0f, -1.0f,     // 反面右上7
    };

    // 1个面俩三角形，1个三角形3个顶点
    final short mIndexes[] = {
            6, 7, 4, 6, 4, 5,    // 后面
            6, 3, 7, 6, 2, 3,    // 右面
            6, 5, 1, 6, 1, 2,    // 下面
            0, 3, 2, 0, 2, 1,    // 正面
            0, 1, 5, 0, 5, 4,    // 左面
            0, 7, 3, 0, 4, 7,    // 上面
    };

    // 8个顶点颜色(RGBA)
    float mColors[] = {
            0f, 1f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 1f, 0f, 1f,
            1f, 0f, 0f, 1f,
            1f, 0f, 0f, 1f,
            1f, 0f, 0f, 1f,
            1f, 0f, 0f, 1f,
    };

    private int mMatrixHandle;
    private int mPositionHandle;
    private int mColorHandle;

    private float[] mViewMatrix = new float[16];
    private float[] mProjectMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];

    // 顶点个数
    private final int vertexCount = mCubePositions.length / COORDS_PER_VERTEX;
    // 顶点之间的偏移量
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 每个顶点四个字节

    public Cube(View view) {
        super(view);

        ByteBuffer bb = ByteBuffer.allocateDirect(mCubePositions.length * 4);
        bb.order(ByteOrder.nativeOrder());
        mVertexBuffer = bb.asFloatBuffer();
        mVertexBuffer.put(mCubePositions);
        mVertexBuffer.position(0);

        ByteBuffer dd = ByteBuffer.allocateDirect(mColors.length * 4);
        dd.order(ByteOrder.nativeOrder());
        mColorBuffer = dd.asFloatBuffer();
        mColorBuffer.put(mColors);
        mColorBuffer.position(0);

        ByteBuffer cc = ByteBuffer.allocateDirect(mIndexes.length * 2);
        cc.order(ByteOrder.nativeOrder());
        mIdxBuffer = cc.asShortBuffer();
        mIdxBuffer.put(mIndexes);
        mIdxBuffer.position(0);

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, mVertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, mFragmentShaderCode);
        // 创建一个空的OpenGLES程序
        mProgram = GLES20.glCreateProgram();
        // 将顶点着色器加入到程序
        GLES20.glAttachShader(mProgram, vertexShader);
        // 将片元着色器加入到程序中
        GLES20.glAttachShader(mProgram, fragmentShader);
        // 连接到着色器程序
        GLES20.glLinkProgram(mProgram);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 开启深度测试
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // 计算宽高比
        float ratio = (float) width / height;

        // 设置透视投影
        Matrix.frustumM(mProjectMatrix, 0, -ratio, ratio, -1, 1, 3, 20);

        // 设置相机位置
        Matrix.setLookAtM(mViewMatrix, 0, 5.0f, 5.0f, 10.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // 计算变换矩阵
        Matrix.multiplyMM(mMVPMatrix,0, mProjectMatrix,0, mViewMatrix,0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // 将程序加入到OpenGLES2.0环境
        GLES20.glUseProgram(mProgram);

        // 获取变换矩阵vMatrix成员句柄
        mMatrixHandle = GLES20.glGetUniformLocation(mProgram,"vMatrix");
        // 指定vMatrix的值
        GLES20.glUniformMatrix4fv(mMatrixHandle,1,false, mMVPMatrix,0);

        // 获取顶点着色器的vPosition成员句柄
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        // 启用三角形顶点的句柄
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        // 准备三角形的坐标数据
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, 0, mVertexBuffer);

        // 获取片元着色器的vColor成员的句柄
        mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");
        // 设置绘制三角形的颜色
//        GLES20.glUniform4fv(mColorHandle, 2, color, 0);
        GLES20.glEnableVertexAttribArray(mColorHandle);
        GLES20.glVertexAttribPointer(mColorHandle,4, GLES20.GL_FLOAT, false, 0, mColorBuffer);

        // 索引法绘制正方体
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, mIndexes.length, GLES20.GL_UNSIGNED_SHORT, mIdxBuffer);

        // 禁止顶点数组的句柄
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}