package com.bride.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * <p>Created by shixin on 2019-05-17.
 */
public class NetworkClassLoader extends ClassLoader {
    private String mBaseUrl;

    public NetworkClassLoader(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String path = classNameToPath(name);
        InputStream is = null;
        try {
            URL url = new URL(path);
            is = url.openStream();
            byte[] buffer = new byte[1024];
            int len;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((len = is.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
            byte[] bytes = baos.toByteArray();
            if (bytes == null || bytes.length <= 0) {
                throw new ClassNotFoundException();
            }
            Class cls = defineClass(name, bytes, 0, bytes.length);
            return cls;
        } catch (MalformedURLException e) {
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
        return mBaseUrl + "/" + className.replace(".", "/") + ".class";
    }
}
