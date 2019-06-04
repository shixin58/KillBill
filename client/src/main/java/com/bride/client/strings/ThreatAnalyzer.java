package com.bride.client.strings;

import java.util.Scanner;
import java.util.regex.MatchResult;

/**
 * <p>Created by shixin on 2019-06-04.
 */
public class ThreatAnalyzer {
    static String threatData = "47.91.249.201@03/30/2020\n"+
            "127.0.0.1@06/04/2019\r\n"+
            "192.168.199.113@06/04/2019";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(threatData);
        String pattern = "(\\d+[.]\\d+[.]\\d+[.]\\d+)@"+
                "(\\d{2}/\\d{2}/\\d{4})";
        while (scanner.hasNext(pattern)) {
            scanner.next(pattern);
            MatchResult result = scanner.match();
            String ip = result.group(1);
            String date = result.group(2);
            System.out.format("ip = %s, date = %s\r\n", ip, date);
        }
    }
}
