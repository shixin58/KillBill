package com.bride.client.datastructure;

import java.util.Stack;
import java.util.Vector;

/**
 * 演示栈Stack用法
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
        stack.push("cloud");// Vector#addElement(E)
        stack.push("java");
        stack.push("algorithm");
        while (!stack.empty()) {
            System.out.println(stack.peek());// Vector#elementAt(int)
            stack.pop();// peek() + Vector#removeElementAt(int)
        }
    }

    // Vector实现了List接口，使用了synchronized达成线程安全
    private static void testVector() {
        System.out.println("\n=== Stack ===");
        Vector<String> vector = new Vector<>(5);
        vector.add("Apple");
        vector.remove(0);
        vector.add("noodle");
        System.out.println("elementAt "+vector.elementAt(0));
    }
}
