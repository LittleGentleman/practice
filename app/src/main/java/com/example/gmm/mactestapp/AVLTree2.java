package com.example.gmm.mactestapp;

/**
 * 手写二叉平衡树  左右子树高度差不大于2的二叉查找树
 * 如果失衡，需要对失衡节点和插入节点进行调整达到平衡
 * LL旋转：顺时针
 * RR旋转：逆时针
 * LR旋转：先对左子树进行RR旋转，再对二叉树进行LL旋转
 * RL旋转：先对右子树进行LL旋转，再对二叉树进行RR旋转
 */
public class AVLTree2 {

    public static Node insert(Node root,int data){
        if (root == null){
            root = new Node(data);
            return root;
        }

        if (data<=root.data){
            root.left = insert(root.left,data);
            if (getHeight(root.left)-getHeight(root.right)>1){//失衡
                if (data<=root.left.data){//需要LL旋转调整平衡 插入节点在左子树的左边
                    root = LLRotate(root);
                } else {//需要LR旋转调整平衡  插入节点在左子树的右边
                    root = LRRotate(root);
                }
            }
        } else {
            root.right = insert(root.right,data);
            if (getHeight(root.right)-getHeight(root.left)>1){//失衡
                if (data>=root.right.data){//需要RR旋转调整平衡 插入节点在右子树的右边
                    root = RRRotate(root);
                } else {//需要RL旋转调整平衡 插入节点在右子树的左边
                    root = RLRotate(root);
                }
            }
        }

        //计算根节点的层级高度
        root.height = Math.max(getHeight(root.left),getHeight(root.right)) + 1;
        return root;
    }

    private static Node LLRotate(Node root){
        Node leftRoot = root.left;
        root.left = leftRoot.right;
        leftRoot.right = root;

        //重新计算层级高度
        root.height = Math.max(getHeight(root.left),getHeight(root.right))+1;
        leftRoot.height = Math.max(getHeight(leftRoot.left),root.height)+1;
        return leftRoot;
    }

    private static Node RRRotate(Node root){
        Node rightRoot = root.right;
        root.right = rightRoot.left;
        rightRoot.left = root;
        //重新计算层级高度
        root.height = Math.max(getHeight(root.left),getHeight(root.right))+1;
        rightRoot.height = Math.max(root.height,getHeight(root.right))+1;
        return rightRoot;
    }

    private static Node LRRotate(Node root){
        //先对左子树RR旋转，变化后的的节点成为root的左子节点
        root.left = RRRotate(root.left);
        //此时的二叉树 需要LL旋转即可调整平衡
        return LLRotate(root);
    }

    private static Node RLRotate(Node root){
        //先对右子树进行LL旋转，变化后的节点成为root的右子节点
        root.right = LLRotate(root.right);
        //此时的二叉树 需要RR旋转即可调整平衡
        return RRRotate(root);
    }


    public static int getHeight(Node node){
        return node==null?-1:node.height;
    }

    public static void printTree(Node root){
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

    static class Node{
        int data;

        Node left;
        Node right;

        int height;

        public Node(int data){
            this.data = data;
        }
    }
}
