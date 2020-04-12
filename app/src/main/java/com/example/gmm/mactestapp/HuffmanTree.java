package com.example.gmm.mactestapp;

import android.util.Log;

import java.util.List;

public class HuffmanTree {

    /**
     * 创建哈夫曼树-最优二叉树：带权路径长度最小，左节点比右节点小
     *
     * @param nodes
     * @return
     */
    public static Node createHuffmanTree(List<Node> nodes){

        while (nodes.size()>1){
            //增序排序  冒泡排序，两两比较，大的往后排
            sort(nodes);

            //左叶节点为第0个位置的节点
            Node left = nodes.get(0);
            //右叶节点为第一个位置的节点
            Node right = nodes.get(1);

            //创建父节点
            Node node = new Node(null,left.weight+right.weight);
            //父节点绑定子节点
            node.leftNode = left;
            node.rightNode = right;

            //从集合中删除第一个和第二个页节点
            nodes.remove(0);
            nodes.remove(0);

            //添加新的节点
            nodes.add(node);
        }

        //循环结束后，集合里只剩一个根节点
        return nodes.get(0);
    }

    //冒泡，权重大的冒到最后面
    public static void sort(List<Node> nodes){
        if (nodes.size()<=1){
            return;
        }
        for (int i = 0; i < nodes.size(); i++) {

            for (int j = 0; j < nodes.size() - i - 1; j++) {
                //相邻两个节点，当前节点与下一节点比较，如果当前节点的权值大，则交换位置，最后权值大的节点会排到后面，达到冒泡效果
                if (nodes.get(j).weight>nodes.get(j+1).weight){
                    Node node = nodes.get(j);
                    nodes.set(j,nodes.get(j+1));
                    nodes.set(j+1,node);
                }
            }
        }
    }

    public static void printTree(Node root){
        Log.e("tag",root.toString());
        if (root.leftNode!=null){
            Log.e("tag","left:" + root.leftNode.toString());
            printTree(root.leftNode);
        }
        if (root.rightNode!=null){
            Log.e("tag","right:"+ root.rightNode.toString());
            printTree(root.rightNode);
        }
    }

    public static class Node<E>{
        public E data;
        public int weight;
        public Node leftNode;
        public Node rightNode;
        public Node(E data,int weight){
            this.data = data;
            this.weight = weight;
        }

        public String toString(){
            return "Node[" + weight + ",data=" + data + "]";
        }

    }
}
