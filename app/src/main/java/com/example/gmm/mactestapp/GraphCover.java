package com.example.gmm.mactestapp;

import java.util.ArrayList;
import java.util.List;

/**
 * 图的遍历
 */
public class GraphCover extends Graph {

    private int[] visit = new int[size];//标记节点是否走过，防止进入死循环


    /**
     * 深度优先遍历
     * 一条路走到黑，不撞南墙不回头
     * 对每一个可能的分支路径深入到不能在深入为止
     * @param start
     */
    public void deepFirst(int start){//从第start的节点开始遍历
        visit[start] = 1; //标记该节点已走过（表示该顶点已被处理过）
        System.out.println("齐天大圣到——>" + nodes[start] +"一游");

        for (int i = 0; i < size; i++) {
            if (edges[start][i]==1&&visit[i]==0){//邻接点
                //邻接点
                deepFirst(i);//递归
            }
        }
    }

    /**
     * 广度优先遍历
     * 广度优先搜索遍历图的过程中以v为起始点，由近至远，
     * 依次访问和v 有路径相同且路径长度为1，2，。。。的顶点
     * @param tmp_nodes
     */
    private int[] queue = new int[size];
    public void breadthFirst(int front,int tail){
        int last = tail;
        for (int index=front;index<=tail;index++) {
            int node = queue[index];

            System.out.println("齐天大圣到——>" + nodes[node] +"一游");

            //找出所有邻接点
            for (int i = 0; i < size; i++) {
                if (edges[node][i]==1&&visit[i]==0){//邻接点
                    visit[i] = 1;
                    queue[++last] = i;
                }
            }

        }

        if (last > tail){
            //遍历下一批节点
            breadthFirst(tail+1,last);
        }

    }

    public void breadthFirst(int start){
        queue[0] = start;
        visit[start] = 1;

        breadthFirst(0,0);
    }

    public static void main(String[] attrs){
        GraphCover graphCover = new GraphCover();
//        graphCover.deepFirst(0);//深度遍历


        graphCover.breadthFirst(0);//广度遍历
    }
}
