package com.bride.client.language;

/**
 * <p>Created by shixin on 2019-05-05.
 */
public class LinkedList<T> {

    private static class Node1<U> {

        U item;
        Node1<U> next;

        Node1() {
            this.item = null;
            this.next = null;
        }

        Node1(U item, Node1<U> next) {
            this.item = item;
            this.next = next;
        }

        private boolean isEnd() {
            return item == null && next == null;
        }
    }

    private Node1<T> top = new Node1<>();// End sentinel

    public void push(T item) {
        top = new Node1<>(item, top);
    }

    public T pop() {
        T result = top.item;
        if (!top.isEnd()) {
            top = top.next;
        }
        return result;
    }

    private class Node2 {
        T item;
        Node2 next;
        Node2(T item, Node2 next) {
            this.item = item;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        Node1<Character> node1 = new Node1<>(null, null);

        LinkedList<Integer> linkedList = new LinkedList<>();
        LinkedList.Node2 node2 = linkedList.new Node2(1, null);

        LinkedList<Character> characterLinkedList = new LinkedList<>();
        String str = "Awesome";
        for (Character c : str.toCharArray()) {
            characterLinkedList.push(c);
        }
    }
}
