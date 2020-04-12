package com.example.gmm.mactestapp;

/**
 * 希尔排序 也叫缩小增量排序，引入了缩小增量序列  降序
 * 简单插入排序+希尔序列=希尔排序
 * 常用的增量序列有：希尔增量序列{N/2,(N/2)/2,...,1}，Hibbard序列{2^k-1,...,3,1}，Sedgewick序列{9*4^i-9*2^i+1...,109,41,19,5,1}
 */
public class ShellSort {

    public static int[] sort(int[] array){
        if (array.length == 0){
            return array;
        }
        //增量
        int gap = array.length/2;
        //组内待排序的数据
        int currentValue;
        while (gap>0){
            for (int i = gap; i < array.length; i++) {
                currentValue = array[i];
                int preIndex = i-gap;
                while (preIndex>=0&&currentValue>array[preIndex]){
                    array[preIndex+gap] = array[preIndex];
                    preIndex = preIndex - gap;
                }
                array[preIndex+gap] = currentValue;
            }
            System.out.print("gap="+ gap + ",array=     ");
            PrintArray.print(array);
            System.out.println("======================================================");
            gap = gap/2;
        }
        return array;
    }

    public static void main(String[] args){
        PrintArray.print(PrintArray.SRC);
        System.out.println("======================================================");
        PrintArray.print(sort(PrintArray.SRC));
    }
}
