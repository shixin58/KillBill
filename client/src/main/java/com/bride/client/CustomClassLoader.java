package com.bride.client;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * <p>Created by shixin on 2019-05-17.
 */
public class CustomClassLoader extends ClassLoader {

    private String mBasePath;

    public CustomClassLoader(String path) {
        this.mBasePath = path;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        FileInputStream is = null;
        try {
            is = new FileInputStream(classNameToPath(name));
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            while ((len = is.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
            byte[] bytes = baos.toByteArray();
            if (bytes == null || bytes.length <= 0) {
                throw new ClassNotFoundException();
            }
            Class cls = defineClass(name, bytes, 0, bytes.length);
            return cls;
        } catch (FileNotFoundException e) {
            throw new ClassNotFoundException();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private String classNameToPath(String className) {
        return mBasePath + "/" + className.replace(".", "/") + ".class";
    }
}