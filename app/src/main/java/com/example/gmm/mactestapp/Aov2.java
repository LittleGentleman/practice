package com.example.gmm.mactestapp;

import java.util.Stack;

/**
 * 拓补排序算法  两种实现方法：①广度优先遍历 ②深度优先遍历
 * 节点之间存在依赖关系
 * AOV网络拓补排序：
 * 度：顶点连接边的个数
 * 入度：进入当前顶点的边的个数
 * 出度：从当前顶点出发的边的个数
 */
public class Aov2 {
    private String[] nodes;
    private int[][] edges;
    private int size;
    private int[] eSize;

    public Aov2(){
        init();
    }

    //1.计算各个节点的入度
    private void flush(){
        for (int node = 0; node < size; node++) {
            for (int i = 0; i < size; i++) {
                if (edges[i][node] > 0){
                    eSize[node]++;
                }
            }
        }
    }

    //使用深度优先遍历实现 拓补排序算法 栈--后进先出
    private int[] getPath(){
        int[] path = new int[size];

        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < size; i++) {
            //2.入度为0的节点入队
            if (eSize[i] == 0){
                stack.push(i);
            }
        }

        int count = 0;
        while (!stack.isEmpty()){
            Integer node = stack.pop();
            path[count++] =  node;
            System.out.print("---->" + nodes[node]);
            for (int i = 0; i < size; i++) {
                if (edges[node][i]>0){
                    eSize[i]--;
                    if (eSize[i] == 0){
                        stack.push(i);
                    }
                }
            }
        }

        return path;
    }


    //3.入队节点的邻接点的入队-1

    private void init(){
        nodes = new String[]{"AA","A","B","C","D","E","F","G","H","K","M","N"};
        final int AA=0,A=1,B=2,C=3,D=4,E=5,F=6,G=7,H=8,K=9,M=10,N=11;
        size = nodes.length;
        edges = new int[size][size];
        eSize = new int[size];
        edges[AA][A] = 3;
        edges[AA][B] = 2;
        edges[AA][C] = 5;
        edges[A][D] = 4;
        edges[B][G] = 2;
        edges[B][E] = 3;
        edges[C][E] = 2;
        edges[C][F] = 3;
        edges[D][G] = 1;
        edges[F][K] = 4;
        edges[E][M] = 8;
        edges[E][K] = 1;
        edges[G][H] = 2;
        edges[H][M] = 3;
        edges[K][N] = 2;
        edges[M][N] = 3;
    }

    public static void main(String[] args){
        Aov2 aov = new Aov2();
        aov.flush();
        aov.getPath();
    }
}
