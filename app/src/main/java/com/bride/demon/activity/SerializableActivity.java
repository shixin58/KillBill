package com.bride.demon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

import com.bride.demon.GsonUtils;
import com.bride.demon.databinding.ActivitySerializableBinding;
import com.bride.demon.model.City;
import com.bride.demon.model.House;
import com.bride.demon.model.Person;
import com.bride.demon.model.Student;
import com.bride.ui_lib.BaseActivity;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import timber.log.Timber;

/**
 * <p>Created by shixin on 2019-08-19.
 */
public class SerializableActivity extends BaseActivity {
    private ActivitySerializableBinding mBinding;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, SerializableActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySerializableBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
    }

    public void onClick(View v) {
        if (v == mBinding.tvWriteSerializable) {
            write(new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "file_serializable.txt"),
                    new Student(140429198905048511L, "石鑫", 30, "Mobile Cloud Computing"));
        } else if (v == mBinding.tvReadSerializable) {
            read(new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "file_serializable.txt"), this);
        } else if (v == mBinding.tvWriteSerializableManual) {
            write(new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "file_serializable_manual.txt"),
                    new House("北京", 100f, 6));
        } else if (v == mBinding.tvReadSerializableManual) {
            read(new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "file_serializable_manual.txt"), this);
        } else if (v == mBinding.tvWriteExternalizable) {
            write(new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "file_externalizable.txt"),
                    new Person("Max", "gentleman"));
        } else if (v == mBinding.tvReadExternalizable) {
            read(new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "file_externalizable.txt"), this);
        } else if (v == mBinding.tvWriteGson) {
            writeGson(new House("涿州", 99f, 1),
                    new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "file_gson.txt"));
        } else if (v == mBinding.tvReadGson) {
            readGson(this, new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                    "file_gson.txt"), House.class);
        } else if (v == mBinding.tvFromJson) {
            String json = "{\"price\":1.0,\"name\":\"John\",\"age\":18}";
            Map<String,Object> map = GsonUtils.INSTANCE.stringMapFromJson(json);
            Timber.i(map.toString());
        } else if (v == mBinding.tvSendParcelable) {
            Bundle bundle = new Bundle();
            City city = new City("New York", "International");
            city.setCreateTime(SystemClock.elapsedRealtime());
            bundle.putParcelable("city", city);
            bundle.putInt("index", 1);
            City[] cities = new City[2];
            cities[0] = new City("Paris", "Romantic");
            cities[1] = new City("Hong Kong", "Financial");
            bundle.putParcelableArray("cityList", cities);
            BlankActivity.Companion.openActivity(this, bundle);
        } else if (v == mBinding.tvSendExternalizable) {
            Bundle bundle2 = new Bundle();
            Person person = new Person("Lucy", "lady");
            person.setCreateTime(SystemClock.elapsedRealtime());
            bundle2.putSerializable("person", person);
            bundle2.putInt("index", 2);
            BlankActivity.Companion.openActivity(this, bundle2);
        }
    }

    // 用Serializable将Java对象以字节码形式保存至磁盘
    public static void write(File file, Object o) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(o);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(File file, Context context) {
        try {
            long start = SystemClock.elapsedRealtime();
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
            Object o = inputStream.readObject();
            inputStream.close();
            Toast.makeText(context.getApplicationContext(), o.toString(), Toast.LENGTH_SHORT).show();
            Timber.i("耗时：" + (SystemClock.elapsedRealtime() - start) + "ms");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 用JSON将Java对象以json字符串方式保存至磁盘
    public static void writeGson(Object o, File file) {
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(new Gson().toJson(o).getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readGson(Context context, File file, Class<?> cls) {
        try {
            long start = SystemClock.elapsedRealtime();
            FileInputStream inputStream = new FileInputStream(file);
            byte[] resultBytes = new byte[0];
            byte[] bytes = new byte[16];
            int length;
            while ((length=inputStream.read(bytes)) > 0) {
                byte[] tmp = new byte[resultBytes.length+length];
                System.arraycopy(resultBytes, 0, tmp, 0, resultBytes.length);
                System.arraycopy(bytes, 0, tmp, resultBytes.length, length);
                resultBytes = tmp;
            }
            Toast.makeText(context.getApplicationContext(),
                    new Gson().fromJson(new String(resultBytes), cls).toString(), Toast.LENGTH_SHORT).show();
            Timber.i("耗时：" + (SystemClock.elapsedRealtime() - start) + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
