package com.example.gmm.mactestapp;

/**
 * 堆排序
 *
 * 完全二叉树
 */
public class HeapSort {
    //声明全局变量，用于记录数组array的长度
    private static int len;

    public static int[] sort(int[] array) {
        len = array.length;
        if (len < 1) return array;
        //构建一个最大堆
        buildMaxHeap(array);
        //取出堆顶元素和尾元素交换
        while (len>0){
            swap(array,0,len-1);
            len--;
            adjustHeap(array,0);
        }
        return array;
    }

    //构建一个最大堆
    private static void buildMaxHeap(int[] array) {
        //len/2-1：最后一个非叶节点
        //找到最后一个非叶节点后
        for (int i = len/2-1; i >= 0 ; i--) {
            adjustHeap(array, i);
        }
    }

    //调整成为新的堆
    private static void adjustHeap(int[] array,int i) {
        int maxIndex = i;//保存最大元素的索引
        int left = 2*i + 1;//左子节点索引
        int right = 2*(i+1);//右子节点索引
        if (left<len&&array[left]>array[maxIndex]){
            maxIndex = left;
        }
        if (right<len&&array[right]>array[maxIndex]){
            maxIndex = right;
        }
        if (maxIndex != i) {
            swap(array,maxIndex,i);
            adjustHeap(array,maxIndex);
        }
    }

    /**
     * 交换数组内两个元素
     * @param array
     * @param i
     * @param j
     */
    public static void swap(int[] array,int i,int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        PrintArray.print(PrintArray.SRC);
        System.out.println("=====================================");
        int[] result = HeapSort.sort(PrintArray.SRC);
        PrintArray.print(result);
    }
}
