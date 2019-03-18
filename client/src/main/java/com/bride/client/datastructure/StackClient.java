package com.bride.client.datastructure;

import java.util.Stack;

/**
 * <p>Created by shixin on 2019/3/18.
 */
public class StackClient {

    public static void main(String[] args) {
        testStack();
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
}
