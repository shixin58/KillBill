package com.bride.client.strings;

import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Created by shixin on 2019-06-04.
 */
public class RegexClient {

    public static void main(String[] args) {
        testRegularExpressions();
        testStringTokenizer();
        replacement();
    }

    public static void testRegularExpressions() {
        System.out.println("\n*** regular expressions ***");
        System.out.println("-123".matches("-?\\d+")+" "+"-123".matches("-?\\w+"));
        System.out.println("\\".matches("\\\\"));
        System.out.println("+911".matches("(-|\\+)?\\d+"));
        // illegal escape character in String literal。Java不支持\e么？
        // \r \015
        System.out.println("e\n\t\r\f".matches("e\\e?\\s+"));
        String greetings = "Victor, welcome to Beijing";
        System.out.println(Arrays.toString(greetings.split("\\W+")));
        System.out.println(Arrays.toString(greetings.split(" ", 3)));
        System.out.println(Arrays.toString(greetings.split("c\\w+")));
        System.out.println(greetings.replaceFirst("B\\w+", "China"));
        System.out.println(greetings.replaceFirst("B[a-zA-Z0-9]+", "China"));
        System.out.println(greetings.replaceAll("\\x6f", "\117"));// o(十六进制) O(八进制)

        String dolphin = "Dolphin";
        // .任意单个字符，*0到多个字符，?0到1个字符，+1到多个字符
        System.out.println("Dolphin "+dolphin.matches("Dolphi.")+" "+dolphin.matches("Dolphin.*")+" "+dolphin.matches("Dolphi.?"));
        System.out.println("MainActivity".matches("^M.*y$"));

        Pattern pattern = Pattern.compile("[a-zA-Z]{2}", 0);
        Matcher matcher = pattern.matcher("abcde_");
        // 查找多个匹配
        /*while (matcher.find()) {
            System.out.println("Match \""+matcher.group()+"\" at positions "+matcher.start()+"-"+(matcher.end()-1));
        }*/
        while (matcher.find()) {
            for (int i=0; i<=matcher.groupCount(); i++) {
                System.out.print("("+i+")"+matcher.group(i)+", ");
            }
            System.out.println();
        }
        System.out.println(matcher.replaceAll("*"));
        // 是否匹配整个input，从开始判断是否匹配, 从指定位置查找匹配
        System.out.println(matcher.matches()+" "+matcher.lookingAt()+" "+matcher.find(3));
        System.out.println(Arrays.asList(pattern.split("1a2b3c")));

        // regex是否匹配整个input
        System.out.println("matches "+Pattern.matches("[0-9]", "123"));
    }

    // delimiters = " \t\n\r\f"
    // tokenizer 分词器
    // token 代币，代价券，symbol
    public static void testStringTokenizer() {
        String input = "Thank god, I'm alive now!";
        StringTokenizer stringTokenizer = new StringTokenizer(input);
        /*while (stringTokenizer.hasMoreElements()) {
            System.out.print(stringTokenizer.nextElement()+"|");
        }*/
        while (stringTokenizer.hasMoreTokens()) {
            System.out.print(stringTokenizer.nextToken()+"|");
        }
        System.out.println();
        System.out.println(Arrays.toString(input.split(" ")));
    }

    public static void replacement() {
        StringBuffer sbuf = new StringBuffer();
        Pattern p = Pattern.compile("[aeiou]", 0);
        Matcher m = p.matcher("beautiful");
        while (m.find()) {
            m.appendReplacement(sbuf, m.group().toUpperCase());
        }
        System.out.println(sbuf);
        m.appendTail(sbuf);
        System.out.println(sbuf);
    }
}
