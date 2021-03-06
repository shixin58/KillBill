package com.bride.client.datastructure;

import java.util.Stack;
import java.util.Vector;

/**
 * <p>Created by shixin on 2019/3/18.
 */
public class StackClient {

    public static void main(String[] args) {
        testStack();
        testVector();
    }

    private static void testStack() {
        System.out.println("=== Stack ===");
        Stack<String> stack = new Stack<>();
        stack.push("cloud");
        stack.push("java");
        stack.push("algorithm");
        while (!stack.empty()) {
            System.out.println(stack.peek());
            stack.pop();
        }
    }

    // 实现List，使用synchronized
    private static void testVector() {
        System.out.println("\n=== Stack ===");
        Vector<String> vector = new Vector<>(5);
        vector.add("Apple");
        vector.remove(0);
        vector.add("noodle");
        System.out.println("elementAt "+vector.elementAt(0));
    }
}
