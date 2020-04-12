package com.example.gmm.mactestapp;

/**
 * 冒泡排序  升序  两两交换
 */
public class BubbleSort {
    public static int[] sort(int[] array){

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length-(i+1); j++) {
                if (array[j]>array[j+1]){
                    //两两交换
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
        }

        return array;
    }

    public static void main(String[] args){
        PrintArray.print(PrintArray.SRC);
        System.out.println("======================================");
        PrintArray.print(sort(PrintArray.SRC));
    }
}
