package com.example.gmm.mactestapp;

/**
 * 二叉平衡树 AVL树
 */
public class AVLTree {

    /**
     * AVL树 插入
     * 每次都需要从根节点往后查
     * 递归是难点
     * @param root
     * @param data
     * @return
     */
    public static Node insert(Node root,int data){
        if (root == null){
            root = new Node(data);
            return root;
        }

        if (data<=root.data){
            //递归 二叉查找树 二叉排序树
            root.left = insert(root.left,data);
            //失衡 需要平衡调整
            if (getHeight(root.left)-getHeight(root.right)>1){
                if (data<=root.left.data){ //LL旋转调整
                    System.out.println("LL旋转");
                    root = LLRotate(root);
                } else {//LR旋转调整
                    System.out.println("LR旋转");
                    root = LRRotate(root);
                }
            }
        } else {
            root.right = insert(root.right,data);
            if (getHeight(root.right)-getHeight(root.left)>1){
                if (data>=root.right.data){//RR旋转调整
                    System.out.println("RR旋转");
                    root = RRRotate(root);
                } else {//RL旋转调整
                    System.out.println("RL旋转");
                    root = RLRotate(root);
                }
            }
        }

        //重新调整root节点的高度值
        root.height = Math.max(getHeight(root.left),getHeight(root.right)) + 1;

        //返回的始终是根节点
        return root;
    }

    /**
     * LL旋转
     * @param node
     * @return
     */
    private static Node LLRotate(Node node){
        Node leftTree = node.left;
        node.left = leftTree.right;
        leftTree.right = node;
        // 重新设置失衡点30和新节点20的高度
        node.height = Math.max(getHeight(node.left),getHeight(node.right))+1;
        leftTree.height = Math.max(getHeight(leftTree.left),node.height)+1;
        return leftTree;

    }

    /**
     * LR旋转
     * @param node
     * @return
     */
    private static Node LRRotate(Node node){
        node.left = RRRotate(node.left);
        return LLRotate(node);
    }

    private static Node RRRotate(Node node){
        Node rightTree = node.right;
        node.right = rightTree.left;
        rightTree.left = node;

        node.height = Math.max(getHeight(node.left),getHeight(node.right))+1;
        rightTree.height = Math.max(node.height,getHeight(rightTree.right))+1;
        return rightTree;
    }

    /**
     * RL旋转
     * @param node
     * @return
     */
    private static Node RLRotate(Node node){
        node.right = LLRotate(node.right);
        return RRRotate(node);
    }

    public static class Node{
         int data;
         Node left;
         Node right;
         int height;//节点层级
        public Node(int data){
            this.data = data;
        }

    }

    public static int getHeight(Node node){
        return node==null?-1:node.height;
    }

    public static void  printTree(Node root){
        System.out.println(root.data);
        if (root.left != null){
            System.out.print("left:");
            printTree(root.left);
        }
        if (root.right != null){
            System.out.print("right:");
            printTree(root.right);
        }
    }
}
