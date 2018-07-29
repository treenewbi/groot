package com.huangwu.etcd.other;

import java.util.Stack;

/**
 * 用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。
 *
 * @Package: com.huangwu.etcd.other
 * @Author: huangwu
 * @Date: 2018/6/25 21:06
 * @Description:
 * @LastModify:
 */
public class Test5 {

    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        while (!stack1.empty()) {
            stack2.push(stack1.pop());
        }
        int value = stack2.pop();
        while (!stack2.empty()) {
            stack1.push(stack2.pop());
        }
        return value;
    }

    public static void main(String[] args) {
        Math.pow(2,10-1);
    }
}
