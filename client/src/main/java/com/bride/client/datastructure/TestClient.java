package com.bride.client.datastructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestClient {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        // readline()会阻塞，直到敲return键
        while ((line=br.readLine()) != null) {
            String[] strArr = line.split("[ ]");
            int[] intArr = new int[strArr.length];
            for (int i=0; i< strArr.length; i++) {
                intArr[i] = Integer.parseInt(strArr[i]);
            }
            StringBuilder sb = new StringBuilder();
            for (int i=0; i<intArr.length; i++) {
                sb.append(intArr[i]).append(",");
            }
            if (intArr.length > 0) {
                sb.deleteCharAt(sb.length()-1);
                System.out.println(sb);
            }
        }
    }
}
