package com.huangwu.etcd.other;

import java.util.ArrayList;
import java.util.Stack;

/**
 * 笔试题练习
 *
 * @Package: com.huangwu.etcd.other
 * @Author: huangwu
 * @Date: 2018/6/24 20:25
 * @Description:
 * @LastModify:
 */
public class TestSubject {
    /**
     * 2、请实现一个函数，将一个字符串中的空格替换成“%20”。例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
     */
    public String replaceSpace(StringBuffer str) {
        String string = null;
        if (str != null) {
            string = str.toString();
            string = string.replace(" ", "%20");
        }
        return string;
    }

    /**
     * 3、输入一个链表，从尾到头打印链表每个节点的值。
     *
     * @param listNode
     * @return
     */
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> list = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        while (listNode != null) {
            stack.push(listNode.val);
            listNode = listNode.next;
        }
        int size = stack.size();
        for (int i = 0; i < size; i++) {
            list.add(stack.pop());
        }
        return list;
    }





    public static void main(String[] args) {
        TestSubject subject = new TestSubject();
        ListNode listNode = new ListNode(10);
        ListNode head = listNode;
        for (int i = 0; i < 5; i++) {
            listNode.next = new ListNode(i);
            listNode = listNode.next;
        }
        System.out.println(subject.printListFromTailToHead(head));
    }

}

class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}


