package com.example.gmm.mactestapp;

/**
 * 简单插入排序 升序   与之前的序列进行对比，然后部分元素后移，插入到适当的位置
 */
public class InsertionSort {

    public static int[] sort(int[] array) {
        if (array.length == 0)
            return array;

        int currentValue;
        //默认数组的一个元素是有序的，所以要遍历length-1次
        for (int i = 0; i < array.length - 1; i++) {
            int preIndex = i;
            currentValue = array[preIndex+1];
            //往前扫描
            while (preIndex>=0&&currentValue<array[preIndex]){
                array[preIndex+1] = array[preIndex];
                preIndex--;
            }
            array[preIndex+1] = currentValue;
        }

        return array;
    }


    public static void main(String[] args){
        PrintArray.print(PrintArray.SRC);
        System.out.println("======================================================");
        PrintArray.print(sort(PrintArray.SRC));
    }
}
