package com.example.gmm.mactestapp;

import android.util.Log;

import java.util.List;

public class HuffmanTree2 {

    public static Node createHuffmanTree(List<Node> nodes){

        while (nodes.size()>1){
            //冒泡排序
            sort(nodes);

            //最小节点最为左叶节点
            Node left = nodes.get(0);
            //第二小节点作为右叶节点
            Node right = nodes.get(1);

            //创建父节点
            Node node = new Node<>(null,left.weight+right.weight);
            node.left = left;
            node.right = right;

            //移除叶节点
            nodes.remove(0);
            nodes.remove(0);

            //添加新节点
            nodes.add(node);
        }

        //循环结束，结合内的唯一节点就是 根节点root
        return nodes.get(0);
    }

    /**
     * 冒泡排序 升序排序
     * @param nodes
     */
    public static void sort(List<Node> nodes){
        if (nodes.size()<=1){
            return;
        }
        //把最大的值往后排
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.size() - 1 - i; j++) {
                if (nodes.get(j).weight>nodes.get(j+1).weight){
                    Node node = nodes.get(j);
                    nodes.set(j,nodes.get(j+1));
                    nodes.set(j+1,node);
                }
            }
        }
    }


    static class Node<E>{
        E data;
        int weight;
        Node left;
        Node right;
        public Node(E data,int weight){
            this.data = data;
            this.weight = weight;
        }

        public String toString(){
            return "Node[" + weight + ",data:" + data + "]";
        }
    }

    /**
     * 递归打印
     * @param root
     */
    public static void printTree(Node root){

        Log.e("tag",root.toString());
        if (root.left != null) {
            Log.e("tag","left:" + root.left.toString());
            printTree(root.left);
        }
        if (root.right != null) {
            Log.e("tag","right:" + root.right.toString());
            printTree(root.right);
        }
    }
}
