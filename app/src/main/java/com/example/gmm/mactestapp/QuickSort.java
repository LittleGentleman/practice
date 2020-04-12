package com.example.gmm.mactestapp;

import android.support.annotation.ArrayRes;

/**
 * 快速排序
 * 1.找到基准数
 * 2.分割指示器
 */
public class QuickSort {

    public static int[] sort(int[] array,int start, int end) {
        if (array.length<1 || start<0 || end >= array.length || start > end)
            return null;
        //分割指示器 （分区指示器）
        int zoneIndex = partition(array,start,end);
        if (zoneIndex > start) {
            sort(array,start,zoneIndex-1);
        }
        if (zoneIndex < end) {
            sort(array,zoneIndex+1,end);
        }
        return array;
    }

    /**
     * 分区操作
     * @param array
     * @param start
     * @param end
     * @return
     */
    private static int partition(int[] array, int start, int end) {
        //基准数
        int pivot = (int)(start + Math.random()*(end-start+1));
        //分区指示器
        int zoneIndex = start-1;
        //交换基准数和数组尾元素,把基准数放到了最后面，交换后尾元素就是基准数
        swap(array,pivot,end);

        for (int i = start; i <= end; i++) {
            if (array[i]<=array[end]){//此时，array[end] 就是基准数
                //当前元素小于等于基准数时，分割指示器右移一位
                zoneIndex++;
                //当前元素下标大于分割指示器时，当前元素和分割指示器所指元素交换
                if (i > zoneIndex) {
                    swap(array,i,zoneIndex);
                }
            }
        }

        return zoneIndex;
    }

    /**
     * 交换数组内两个元素
     * @param array
     * @param i
     * @param j
     */
    public static void swap(int[] array, int i, int j){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        PrintArray.print(PrintArray.SRC);
        System.out.println("==========================================================");
        int[] result = QuickSort.sort(PrintArray.SRC,0,PrintArray.SRC.length-1);
        PrintArray.print(result);
    }
}
