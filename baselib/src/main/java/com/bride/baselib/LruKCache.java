package com.bride.baselib;

import java.util.HashMap;

import androidx.annotation.NonNull;

/**
 * <p>Created by shixin on 2019/4/12.
 */
public class LruKCache<K, V> {

    private final HashMap<K, Node<K, V>> mNodeMap = new HashMap<>();
    private Node<K, V> mHead, mTail;

    private final HashMap<K, HistoryNode<K>> mHistoryMap = new HashMap<>();
    private HistoryNode<K> mHistoryHead, mHistoryTail;

    private int mMaxSize;
    private int mMaxHistorySize;

    public LruKCache(int size, int historySize) {
        mMaxSize = size;
        mMaxHistorySize = historySize;
    }

    public V put(@NonNull K key, @NonNull V value) {
        Node<K, V> node;
        // 若缓存没有，历史记录也没有，则不缓存
        if ((node = mNodeMap.get(key)) == null && !checkHistory(key))
            return null;
        V preValue = null;
        if (node != null) {// map更新节点
            preValue = node.value;
            node.value = value;
            if (node.before != null) {
                setHead(node);
            }
        } else {
            node = new Node<>(key, value);
            if (mHead != null) {
                mHead.before = node;
                node.after = mHead;
                mHead = node;
            } else {
                // 插入第一个节点
                mHead = mTail = node;
            }
            mNodeMap.put(key, node);

            if (mNodeMap.size() > mMaxSize) {
                remove(mTail.key);
            }
        }
        return preValue;
    }

    public V get(@NonNull K key) {
        Node<K, V> node = mNodeMap.get(key);
        if (node == null) {
            return null;
        }
        if (node.before != null) {// 若非头节点，移动到头节点
            setHead(node);
        }
        return node.value;
    }

    // 将双链表节点移动至头部
    private void setHead(@NonNull Node<K, V> node) {
        node.before.after = node.after;
        if (node.after != null) {
            node.after.before = node.before;
        } else {
            mTail = node.before;
        }
        node.after = mHead;
        node.before = null;
        mHead = node;
    }

    public V remove(@NonNull K key) {
        Node<K, V> node = mNodeMap.remove(key);
        if (node == null)
            return null;
        if (node.before == null && node.after == null) {
            mHead = mTail = null;
            return node.value;
        }
        if (node.before == null) {// 删除头节点
            node.after.before = null;
            mHead = node.after;
        }else if (node.after == null) {// 删除尾节点
            node.before.after = null;
            mTail = node.before;
        } else {
            node.before.after = node.after;
            node.after.before = node.before;
        }
        return node.value;
    }

    private boolean checkHistory(@NonNull K key) {
        if (!mHistoryMap.containsKey(key)) {
            HistoryNode<K> historyNode = new HistoryNode<>(key, 1);
            mHistoryMap.put(key, historyNode);
            if (mHistoryHead != null) {
                historyNode.after = mHistoryHead;
                mHistoryHead.before = historyNode;
                mHistoryHead = historyNode;
            } else {
                // 插入第一个节点
                mHistoryHead = mHistoryTail = historyNode;
            }
            // 超出按FIFO删除
            if (mHistoryMap.size() > mMaxHistorySize) {
                mHistoryMap.remove(mHistoryTail.key);
                if (mHistoryTail.before == null) {
                    mHistoryHead = mHistoryTail = null;
                } else {
                    mHistoryTail.before.after = null;
                    mHistoryTail = mHistoryTail.before;
                }
            }
            return false;
        }
        // 第二次访问删除历史，插入缓存
        removeHistory(key);
        return true;
    }

    private int removeHistory(@NonNull K key) {
        HistoryNode<K> historyNode = mHistoryMap.remove(key);
        if (historyNode == null)
            return -1;
        if (historyNode.before == null && historyNode.after == null) {
            mHistoryHead = mHistoryTail = null;
        } else if (historyNode.before == null) {
            historyNode.after.before = null;
            mHistoryHead = historyNode.after;
        } else if (historyNode.after == null) {
            historyNode.before.after = null;
            mHistoryTail = historyNode.before;
        } else {
            historyNode.before.after = historyNode.after;
            historyNode.after.before = historyNode.before;
        }
        return historyNode.count;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(LruKCache.class.getSimpleName());
        sb.append('[');
        Node<K, V> node = mHead;
        while (node != null) {
            sb.append(node.key).append(" -> ").append(node.value).append(", ");
            node = node.after;
        }
        if (sb.lastIndexOf(", ") == sb.length()-2) {
            sb.delete(sb.length()-2, sb.length());
        }
        sb.append(']');
        sb.append('\n').append("HistoryMap").append('[');
        HistoryNode<K> historyNode = mHistoryHead;
        while (historyNode != null) {
            sb.append(historyNode.key).append(" -> ").append(historyNode.count).append(", ");
            historyNode = historyNode.after;
        }
        if (sb.lastIndexOf(", ") == sb.length()-2) {
            sb.delete(sb.length()-2, sb.length());
        }
        sb.append(']');
        return sb.toString();
    }

    static class Node<K, V> {
        K key;
        V value;
        Node<K, V> before;
        Node<K, V> after;
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    static class HistoryNode<K> {
        K key;
        int count;
        HistoryNode<K> before;
        HistoryNode<K> after;
        HistoryNode(K key, int count) {
            this.key = key;
            this.count = count;
        }
    }

    public static void main(String[] args) {
        LruKCache<String, String> lruKCache = new LruKCache<>(5, 10);
        lruKCache.put("A", "Apple");
        lruKCache.put("B", "Banana");
        lruKCache.put("C", "Cat");
        lruKCache.put("D", "Dog");
        lruKCache.put("E", "Eat");
        System.out.println(lruKCache);
        System.out.println("执行get(B) "+lruKCache.get("B"));
        System.out.println(lruKCache);
        System.out.println("执行put(C) "+lruKCache.put("C", "Call"));
        System.out.println(lruKCache);
        System.out.println("执行put(F) "+lruKCache.put("F", "Fox"));
        System.out.println(lruKCache);
        System.out.println("执行remove(C) "+lruKCache.remove("C"));
        System.out.println(lruKCache);
        lruKCache.put("G", "Girl");
        lruKCache.put("H", "home");
        lruKCache.put("I", "Icon");
        lruKCache.put("J", "Jeans");
        lruKCache.put("K", "Kitkat");
        lruKCache.put("L", "Lollipop");
        System.out.println(lruKCache);
    }
}
