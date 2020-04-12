package com.example.gmm.mactestapp;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 拓补排序算法（确定有向图的顺序）  两种实现方法：①广度优先遍历 ②深度优先遍历
 * 节点之间存在依赖关系
 * AOV网络拓补排序：
 * 度：顶点连接边的个数
 * 入度：进入当前顶点的边的个数
 * 出度：从当前顶点出发的边的个数
 *
 * 关键路径算法 如果节点的最晚时间和最早时间相等，说明这个节点是关键点
 */
public class Aov {
    private String[] nodes;
    private int[][] edges;
    private int size;
    private int[] eSize;
    private int[] fast;//最早时间
    private int[] last;//最晚时间
    public Aov(){
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

    //使用广度优先遍历实现 拓补排序算法 队列--先进先出
    private int[] getPath(){
        int[] path = new int[size];
        //2.入度为0的节点入队
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            if (eSize[i] == 0){
                queue.offer(i);
            }
        }

        //3.入队节点的邻接点的入队-1
        int count = 0;
        while (true){
            Integer node = queue.poll();
            if (null == node){
                break;
            }
//            System.out.print("------>"+nodes[node]);
            path[count++] = node;
            for (int j = 0; j < size; j++) {
                if (edges[node][j]>0){
                    eSize[j]--;
                    if (eSize[j] == 0){
                        queue.offer(j);
                    }
                }
            }
        }

        return path;
    }

    //关键路径
    private void exeKey(){
        int[] path = getPath();//拓补排序
        int start = path[0];
        int end = path[size-1];

        exeFast(start);

        for (int i = 0; i < size; i++) {
            last[i] = fast[end];
        }
        exeLast(end);

        //节点完成的最快时间和最晚时间一样的话，说明该节点是关键点
        for (int i = 0; i < size; i++) {
            int node = path[i];
            if (fast[node]==last[node]){
                System.out.print("--->" + nodes[node]);
            }
        }
    }

    //计算每个节点最快完成时间
    private void exeFast(int node){
        for (int i = 0; i < size; i++) {
            if (edges[node][i] > 0){
                int cost = fast[node] + edges[node][i];
                fast[i] = Math.max(cost,fast[i]);
                exeFast(i);
            }
        }
    }

    //计算每个节点的最晚完成时间
    private void exeLast(int node){
        for (int i = 0; i < size; i++) {
            if (edges[i][node] > 0){
                int cost = last[node] - edges[i][node];
                last[i] = Math.min(cost,last[i]);
                exeLast(i);
            }
        }
    }

    private void init(){
        nodes = new String[]{"AA","A","B","C","D","E","F","G","H","K","M","N"};
        final int AA=0,A=1,B=2,C=3,D=4,E=5,F=6,G=7,H=8,K=9,M=10,N=11;
        size = nodes.length;
        edges = new int[size][size];
        eSize = new int[size];
        fast = new int[size];
        last = new int[size];
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
        Aov aov  = new Aov();
        aov.flush();
//        aov.getPath();
        aov.exeKey();
    }
}
