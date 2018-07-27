package com.huangwu.etcd.other;

import org.junit.Test;

import java.util.Stack;

/**
 * @Package: com.huangwu.etcd.other
 * @Author: huangwu
 * @Date: 2018/6/28 9:41
 * @Description:
 * @LastModify:
 */
public class BinaryTree {

    public static Node init() {
        Node J = new Node(8, null, null);
        Node H = new Node(4, null, null);
        Node G = new Node(2, null, null);
        Node F = new Node(7, null, J);
        Node E = new Node(5, H, null);
        Node D = new Node(1, null, G);
        Node C = new Node(9, F, null);
        Node B = new Node(3, D, E);
        Node A = new Node(6, B, C);
        return A;
    }


    public void printNode(Node node) {
        System.out.print(node.getData());
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        Node root = init();
        tree.theFirstTraversal(root);
    }

    public void theFirstTraversal(Node node) {
        printNode(node);
        if (node.getLeftNode() != null) {
            theFirstTraversal(node.getLeftNode());
        }
        if (node.getRightNode() != null) {
            theFirstTraversal(node.getRightNode());
        }
    }

    public void theInOrderTraversal(Node root) {  //中序遍历
        if (root.getLeftNode() != null) {
            theInOrderTraversal(root.getLeftNode());
        }
        printNode(root);
        if (root.getRightNode() != null) {
            theInOrderTraversal(root.getRightNode());
        }
    }


    public void thePostOrderTraversal(Node root) {  //后序遍历
        if (root.getLeftNode() != null) {
            thePostOrderTraversal(root.getLeftNode());
        }
        if (root.getRightNode() != null) {
            thePostOrderTraversal(root.getRightNode());
        }
        printNode(root);
    }

    public void theFirstTraversal_Stack(Node root) {  //先序遍历
        Stack<Node> stack = new Stack<Node>();
        Node node = root;
        while (node != null || stack.size() > 0) {  //将所有左孩子压栈
            if (node != null) {   //压栈之前先访问
                printNode(node);
                stack.push(node);
                node = node.getLeftNode();
            } else {
                node = stack.pop();
                node = node.getRightNode();
            }
        }
    }

    public void theInOrderTraversal_Stack(Node root) {  //中序遍历
        Stack<Node> stack = new Stack<Node>();
        Node node = root;
        while (node != null || stack.size() > 0) {
            if (node != null) {
                stack.push(node);   //直接压栈
                node = node.getLeftNode();
            } else {
                node = stack.pop(); //出栈并访问
                printNode(node);
                node = node.getRightNode();
            }
        }
    }

    public void thePostOrderTraversal_Stack(Node root) {   //后序遍历
        Stack<Node> stack = new Stack<Node>();
        Stack<Node> output = new Stack<Node>();//构造一个中间栈来存储逆后序遍历的结果
        Node node = root;
        while (node != null || stack.size() > 0) {
            if (node != null) {
                output.push(node);
                stack.push(node);
                node = node.getRightNode();
            } else {
                node = stack.pop();
                node = node.getLeftNode();
            }
        }
        System.out.println(output.size());
        while (output.size() > 0) {

            printNode(output.pop());
        }
    }

}

class Node {
    private int data;
    private Node leftNode;
    private Node rightNode;

    public Node(int data, Node leftNode, Node rightNode) {
        this.data = data;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Node getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }

    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }
}
