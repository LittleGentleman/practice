package com.example.gmm.mactestapp;

/**
 * 简单选择排序  降序  先确认最大值，然后与最前面交换位置
 */
class ChoiceSort {

    public static int[] sort(int[] array){

        if (array.length==0)
            return array;
        for (int i = 0; i < array.length; i++) {
            int maxIndex = i;
            //查找最大值
            for (int j = i; j < array.length; j++) {
                if (array[j] > array[maxIndex]){
                    maxIndex = j;
                }
            }
            //把最大值与最前面的交换位置
            int temp = array[maxIndex];
            array[maxIndex] = array[i];
            array[i] = temp;
        }
        return array;
    }

    public static void main(String[] args){
        PrintArray.print(PrintArray.SRC);
        System.out.println("======================================================");
        PrintArray.print(sort(PrintArray.SRC));
    }
}
