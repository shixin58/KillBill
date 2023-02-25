package com.bride.demon.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bride.baselib.KotlinUtilsKt;
import com.bride.ui_lib.BaseActivity;
import com.bride.baselib.net.Urls;
import com.bride.demon.DemonApplication;
import com.bride.demon.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.io.IOException;
import java.io.InputStream;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Glide为双检查单例模式。
 * <p>磁盘缓存目录为Context#getCacheDir()(/data/data/package/cache/image_manager_disk_cache), InternalCacheDiskCacheFactory, 250MB。
 * <p>或者Context#getExternalCacheDir()(/storage/emulated/0/Android/data/package/cache), ExternalPreferredCacheDiskCacheFactory, 250MB。
 * <p>内存缓存LruArrayPool, BitmapPool, LruResourceCache。
 * <p>Created by shixin on 2019/3/5.
 */
@Route(path = "/demon/glide")
public class GlideActivity extends BaseActivity {
    private static final String TAG = GlideActivity.class.getSimpleName();

    private Bitmap mCurBitmap;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, GlideActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 切横屏或内存不足销毁，均会保存状态创建新实例。正常退出，不保存状态。
        Log.i(TAG, "onCreate "+this.hashCode()+" - "+savedInstanceState);
        setContentView(R.layout.activity_glide);
        initView();
    }

    private void initView() {
        testGlide1();
        testGlide2();

        testXfermode();

        testVector();

        ImageView imageView = findViewById(R.id.iv_decoder);
        setBitmapByRegionDecoder(this, R.mipmap.actress, imageView);

        setImgAlpha();

        setImgColorFilter();
        testColorFilter();

        setBlendedTxt();

        setMulticolor();
    }

    private void testGlide1() {
        Glide.with(this)
                .load(R.mipmap.actress)
//                .apply(RequestOptions.circleCropTransform())
//                .apply(RequestOptions.bitmapTransform(new BlurTransformation()))
                .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(
                        new CropCircleTransformation(), new BlurTransformation(25, 1),
                        new ColorFilterTransformation(0x7FFF0000))))
                .into((ImageView) findViewById(R.id.iv_transform));
    }

    private void testGlide2() {
        ImageView imageView = findViewById(R.id.iv_logo);
        CustomViewTarget<ImageView, Bitmap> target = new CustomViewTarget<ImageView, Bitmap>(imageView) {
            @Override
            protected void onResourceCleared(Drawable placeholder) {
                Log.i(TAG, "CustomViewTarget#onResourceCleared - "+placeholder);
                view.setImageDrawable(placeholder);
            }

            @Override
            public void onLoadFailed(Drawable errorDrawable) {
                Log.i(TAG, "CustomViewTarget#onLoadFailed - "+errorDrawable);
                view.setImageDrawable(errorDrawable);
            }

            @Override
            public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                Log.i(TAG, "CustomViewTarget#onResourceReady");
                Log.i(TAG, "bitmap info: "+resource.getConfig()+" - "+resource.getWidth()
                        +" - "+resource.getHeight()+" - "+resource.getAllocationByteCount());
                view.setImageBitmap(resource);
            }

            @Override
            public void onStart() {
                super.onStart();
                Log.i(TAG, "CustomViewTarget#onStart");
            }

            @Override
            public void onStop() {
                super.onStop();
                Log.i(TAG, "CustomViewTarget#onStop");
            }

            @Override
            public void onDestroy() {
                super.onDestroy();
                Log.i(TAG, "CustomViewTarget#onDestroy");
            }
        };
        RequestListener<Bitmap> requestListener = new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                Log.i(TAG, "RequestListener#onLoadFailed");
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                Log.i(TAG, "RequestListener#onResourceReady");
                return false;
            }
        };
        Glide.with(this)
                .asBitmap()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .load(Urls.Images.LOGO)
                .listener(requestListener)
                .into(target);
    }

    private void testXfermode() {
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(50, 50, 50, paint);
        Paint paint1 = new Paint();
        // 图像组合
        paint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // 颜色滤镜
        paint1.setColorFilter(new PorterDuffColorFilter(0x7F000000, PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.actress), null, new Rect(0, 0, 100, 100), paint1);
        canvas.setBitmap(null);
        ImageView ivCircle = findViewById(R.id.iv_circle);
        ivCircle.setImageBitmap(bitmap);
    }

    private void testVector() {
        // 测试矢量图(res/mipmap/*.xml)
        ImageView imageView = findViewById(R.id.iv_resource);
//        setImage(this, imageView, R.mipmap.ic_launcher_round);
        Bitmap bitmap = getBitmapByFactory(this, R.drawable.city);
        mCurBitmap = bitmap;
        imageView.setImageBitmap(bitmap);
        testInBitmap();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_clear_memory_cache:
                Glide.get(this).clearMemory();
                KotlinUtilsKt.toast("清空Glide内存缓存成功");
                break;
            case R.id.tv_clear_disk_cache:
                DemonApplication.Companion.getInstance().getExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(GlideActivity.this).clearDiskCache();
                        GlideActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                KotlinUtilsKt.toast("清空Glide磁盘缓存成功");
                            }
                        });
                    }
                });
                break;
        }
    }

    public static void setImage(Context context, ImageView imageView, int id) {
        Bitmap bitmap;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            bitmap = getBitmapFromVector(context, id);
        } else {
            bitmap = getBitmapByFactory(context, id);
        }
        imageView.setImageBitmap(bitmap);
        // 4byte*1920*1200
        Log.i(TAG, "bitmap info: "+bitmap.getConfig()+" - "+bitmap.getWidth()
                +" - "+bitmap.getHeight()+" - "+bitmap.getAllocationByteCount());
        Log.i(TAG, "bitmap.isRecycled() "+bitmap.isRecycled());
        // BUGFIX: BitmapDrawable: Canvas: trying to use a recycled bitmap
//        bitmap.recycle();
    }

    /**
     * 通过id加载Drawable，再转化为Bitmap。
     * @param context
     * @param id 兼容矢量图资源
     * @return Bitmap
     */
    public static Bitmap getBitmap(Context context, int id) {
        Drawable drawable = ContextCompat.getDrawable(context, id);
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            return bitmapDrawable.getBitmap();
        } else if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && (drawable instanceof VectorDrawable))
                || (drawable instanceof VectorDrawableCompat)) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }
        return null;
    }

    public static Bitmap getBitmapFromVector(Context context, int vectorDrawableId) {
        final VectorDrawableCompat drawable = VectorDrawableCompat.create(context.getResources(), vectorDrawableId, null);
        if (drawable == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 用BitmapFactory加载图片资源。用drawable/mipmap目录下的jpg
     * @param context
     * @param id
     * @return
     */
    public static Bitmap getBitmapByFactory(Context context, int id) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 不加载图片进内存，获得图片宽高。辅助获得合适的宽高比
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), id, options);
        int rawWidth = options.outWidth;
        Log.i(TAG, "only bitmap info: "+options.outMimeType+", "+", "+rawWidth+" * "+options.outHeight);

        // 1）尺寸压缩，>=2降低图片采样率，减少图片内存占用
        options.inSampleSize = rawWidth / 200;// 压缩到宽200像素

        // 2）质量压缩，即解码率压缩，降低1个像素占用内存的大小。用RGB565替代ARGB_8888可降低图片内存占用。
        // 对JPG管用，对PNG无用
        options.inPreferredConfig = Bitmap.Config.RGB_565;// 1个像素占用两字节

        options.inMutable = true;// true表示该Bitmap缓存区可以被修改

        // Bitmap实现Parcelable接口，可以通过Bundle跨页面、Parcel跨进程传输。
        // BUGFIX: BitmapFactory#decodeResource解析vector图片失败
        // 解码文件用BitmapFactory#decodeFile(pathName, opts)，解码输入流用BitmapFactory.decodeStream(InputStream, Rect, opts)
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(context.getResources(), id, options);
    }

    public void testInBitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.city, options);
        int rawWidth = options.outWidth;

        // Android3.0之前，像素数据保存在本地内存，Bitmap本身保存在Dalvik堆。释放像素数据需调用Bitmap#recycle()
        // Android3.0开始，像素数据和Bitmap本身均保存在Dalvik堆，GC自动回收。
        // 3）内存重用。Android3.0开始支持inBitmap属性，重用该Bitmap缓存区。
        options.inBitmap = mCurBitmap;
        options.inSampleSize = rawWidth / 200;// 600/200
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        // IllegalArgumentException: Problem decoding into existing bitmap
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.city, options);
        ImageView imageView = findViewById(R.id.iv_in_bitmap);
        imageView.setImageBitmap(bitmap);
    }

    public static void setBitmapByRegionDecoder(Context context, int id, ImageView imageView) {
        try {
            // 通过InputStream创建BitmapRegionDecoder
            InputStream inputStream = context.getResources().openRawResource(id);
            BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(inputStream, false);

            // 获得图片原始大小
            int rawWidth = decoder.getWidth();
            int rawHeight = decoder.getHeight();

            // 获取ImageView大小
            ViewGroup.LayoutParams lp = imageView.getLayoutParams();
            int needWidth = lp.width, needHeight = lp.height;// 80dp*80dp

            // 根据ScaleType计算解码区域
            ImageView.ScaleType scaleType = imageView.getScaleType();
            Rect rect = scaleType == ImageView.ScaleType.CENTER_CROP ? new Rect(360, 0, 1560, 1200)
                    :new Rect(0, 0, rawWidth, rawHeight);

            // 设置尺寸压缩
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = rawHeight/needHeight;

            // 生成Bitmap。先裁剪，后等比缩放
            Bitmap bitmap = decoder.decodeRegion(rect, options);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setImgAlpha() {
        ImageView ivAlpha = findViewById(R.id.iv_alpha);
        int alphaNormal = 255;
        int alphaPressed = 100;// 0x64
        ivAlpha.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    ivAlpha.setImageAlpha(alphaPressed);
                    return true;
                case MotionEvent.ACTION_MOVE:
                    if (event.getX() >0 && event.getX() < ivAlpha.getWidth() && event.getY() > 0 && event.getY() < ivAlpha.getHeight()) {
                        ivAlpha.setImageAlpha(alphaPressed);
                    } else {
                        ivAlpha.setImageAlpha(alphaNormal);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    ivAlpha.setImageAlpha(alphaNormal);
//                    Toast toast = Toast.makeText(this, "setImgAlpha", Toast.LENGTH_SHORT);
                    Toast toast = new Toast(this);
                    toast.setDuration(Toast.LENGTH_LONG);
                    View view = LayoutInflater.from(this).inflate(R.layout.toast_custom, null);
                    toast.setView(view);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    break;
            }
            return false;
        });
    }

    private void setImgColorFilter() {
        ImageView ivColorFilter = findViewById(R.id.iv_color_filter);
        ivColorFilter.setColorFilter(0x26000000, PorterDuff.Mode.SRC_OVER);
//        ivColorFilter.setColorFilter(0x64FFFFFF, PorterDuff.Mode.MULTIPLY);
    }

    private void testColorFilter() {
        ColorFilter colorFilter = new ColorMatrixColorFilter(new float[]{0, 0, 0, 0, 0,
                0, 0, 0, 0, 255,
                0, 0, 0, 0, 0,
                0, 0, 0, 1, 0});
        ImageView ivFilter = findViewById(R.id.iv_filter);
        ivFilter.setColorFilter(colorFilter);
        ivFilter.setImageResource(android.R.drawable.stat_notify_more);
    }

    private void setBlendedTxt() {
        TextView textView = findViewById(R.id.tv_blended);
        ImageSpan imageSpan1 = new ImageSpan(this, R.drawable.brave, ImageSpan.ALIGN_BOTTOM);
        ImageSpan imageSpan2 = new ImageSpan(this, R.drawable.brave, ImageSpan.ALIGN_BOTTOM);
        String source = "3+{p1}OR{p2}ON A \nWINLINE WILL AWARD A WIN!";
        SpannableString spannableString = new SpannableString(source);
        spannableString.setSpan(imageSpan1, source.indexOf("{p1}"), source.indexOf("{p1}")+"{p1}".length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(imageSpan2, source.indexOf("{p2}"), source.indexOf("{p2}")+"{p2}".length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
//        textView.append("ABCD");
    }

    private void setMulticolor() {
        // 方案1
        TextView textView = findViewById(R.id.tv_multicolor);
        String num = "x3";
        String separator = " - ";
        String value = "50K";
        SpannableStringBuilder style = new SpannableStringBuilder()
                .append(num).append(separator).append(value);
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#ff0000")), 0, num.length() + separator.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(style);

        // 方案2
        TextView textView2 = findViewById(R.id.tv_multicolor2);
        String info = String.format("<font color=\"#ff0000\">%1$s - </font>%2$s", num, value);
        textView2.setText(Html.fromHtml(info));
    }
}
