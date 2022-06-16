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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
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
 * 磁盘缓存目录为Context#getCacheDir()(/data/data/package/cache/image_manager_disk_cache), InternalCacheDiskCacheFactory, 250MB。
 * 或者Context#getExternalCacheDir()(/storage/emulated/0/Android/data/package/cache), ExternalPreferredCacheDiskCacheFactory, 250MB。
 * 内存缓存LruArrayPool, BitmapPool, LruResourceCache。
 * <p>Created by shixin on 2019/3/5.
 */
@Route(path = "/demon/glide")
public class GlideActivity extends BaseActivity {
    private static final String TAG = GlideActivity.class.getSimpleName();

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
        ImageView imageView = findViewById(R.id.iv_resource);
//        setImage(this, imageView, R.mipmap.ic_launcher_round);
        setBitmapByRegionDecoder(this, R.mipmap.actress, imageView, ImageView.ScaleType.CENTER_CROP);

        Glide.with(this)
                .load(R.mipmap.actress)
//                .apply(RequestOptions.circleCropTransform())
//                .apply(RequestOptions.bitmapTransform(new BlurTransformation()))
                .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(
                        new CropCircleTransformation(), new BlurTransformation(25, 1),
                        new ColorFilterTransformation(0x7FFF0000))))
                .into((ImageView) findViewById(R.id.iv_transform));

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

        ColorFilter colorFilter = new ColorMatrixColorFilter(new float[]{0, 0, 0, 0, 0,
                0, 0, 0, 0, 255,
                0, 0, 0, 0, 0,
                0, 0, 0, 1, 0});
        ImageView ivFilter = findViewById(R.id.iv_filter);
        ivFilter.setColorFilter(colorFilter);
        ivFilter.setImageResource(android.R.drawable.stat_notify_more);

        setImgAlpha();
        setImgColorFilter();
        setBlendedTxt();
        setMulticolor();
    }

    private void initView() {
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

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_clear_memory_cache:
                Glide.get(this).clearMemory();
                Toast.makeText(DemonApplication.Companion.getInstance(),
                        "清空Glide内存缓存成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_clear_disk_cache:
                DemonApplication.Companion.getInstance().getExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(GlideActivity.this).clearDiskCache();
                        GlideActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(DemonApplication.Companion.getInstance(),
                                        "清空Glide磁盘缓存成功", Toast.LENGTH_SHORT).show();
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

    public static Bitmap getBitmapByFactory(Context context, int id) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), id, options);
        Log.i(TAG, "only bitmap info: "+options.outMimeType+", "+", "+options.outWidth+" * "+options.outHeight);

        options.inSampleSize = 1;
        // 对JPG管用，对PNG无用
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        // Bitmap实现Parcelable接口，可以通过Bundle跨页面、Parcel跨进程传输。
        // BUGFIX: BitmapFactory#decodeResource解析vector图片失败
        return BitmapFactory.decodeResource(context.getResources(), id, options);
    }

    public static void setBitmapByRegionDecoder(Context context, int id, ImageView imageView, ImageView.ScaleType scaleType) {
//        FileDescriptor fileDescriptor = context.getResources().openRawResourceFd(id).getFileDescriptor();
        int needWidth = imageView.getLayoutParams().width, needHeight = imageView.getLayoutParams().height;
        int rawWidth = 0, rawHeight = 0;
        InputStream inputStream = context.getResources().openRawResource(id);
        try {
            BitmapRegionDecoder regionDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
            rawWidth = regionDecoder.getWidth();
            rawHeight = regionDecoder.getHeight();
            Log.i(TAG, "needSize: "+needWidth+"*"+needHeight+"; rawSize: "+rawWidth+"*"+rawHeight);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            Rect rect;
            if (scaleType == ImageView.ScaleType.CENTER_CROP) {
                rect = new Rect(360, 0, 1560, 1200);
            } else {
                rect = new Rect(0, 0, rawWidth, rawHeight);
            }

            Bitmap bitmap = regionDecoder.decodeRegion(rect, options);
            Log.i(TAG, "bitmap info: "+bitmap.getConfig()+" - "+bitmap.getWidth()
                    +" - "+bitmap.getHeight()+" - "+bitmap.getAllocationByteCount());
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
