package com.bride.demon.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bride.baselib.BaseFragment;
import com.bride.demon.R;
import com.bride.demon.activity.BlankActivity;
import com.bride.demon.model.City;
import com.bride.demon.model.House;
import com.bride.demon.model.Person;
import com.bride.demon.model.Student;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * <p>Created by shixin on 2019/4/10.
 */
public class NotificationsFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_write_1:
                write(new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "file_1.txt"),
                        new Student(140429198905048511L, "石鑫", 30, "Mobile Cloud Computing"));
                break;
            case R.id.tv_read_1:
                read(new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "file_1.txt"), getActivity());
                break;
            case R.id.tv_write_2:
                write(new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "file_2.txt"),
                        new House("北京", 100f, 6));
                break;
            case R.id.tv_read_2:
                read(new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "file_2.txt"), getActivity());
                break;
            case R.id.tv_write_3:
                write(new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "file_3.txt"),
                        new Person("Max", "gentleman"));
                break;
            case R.id.tv_read_3:
                read(new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "file_3.txt"), getActivity());
                break;
            case R.id.tv_write_4:
                writeGson(new House("涿州", 99f, 1),
                        new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "file_4.txt"));
                break;
            case R.id.tv_read_4:
                readGson(getActivity(), new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                        "file_4.txt"), House.class);
                break;
            case R.id.tv_send_1:
                Bundle bundle = new Bundle();
                City city = new City("New York", "International");
                city.setCreateTime(SystemClock.elapsedRealtime());
                bundle.putParcelable("city", city);
                bundle.putInt("index", 1);
                City[] cities = new City[2];
                cities[0] = new City("Paris", "Romantic");
                cities[1] = new City("Hong Kong", "Financial");
                bundle.putParcelableArray("cityList", cities);
                BlankActivity.Companion.openActivity(getActivity(), bundle);
                break;
            case R.id.tv_send_2:
                Bundle bundle2 = new Bundle();
                Person person = new Person("Lucy", "lady");
                person.setCreateTime(SystemClock.elapsedRealtime());
                bundle2.putSerializable("person", person);
                bundle2.putInt("index", 2);
                BlankActivity.Companion.openActivity(getActivity(), bundle2);
                break;
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
            Log.i(TAG, "耗时："+(SystemClock.elapsedRealtime()-start)+"ms");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
            Log.i(TAG, "耗时："+(SystemClock.elapsedRealtime()-start)+"ms");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
