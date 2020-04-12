package com.example.gmm.mactestapp;

import java.util.Arrays;

/**
 * 归并排序
 * 2-路归并
 * 降序
 */
public class MergeSort {

    public static  int[] sort(int[] array){
        if (array.length<2){
            return array;
        }

        int mid = array.length/2;
        int[] leftChild = Arrays.copyOfRange(array,0,mid);
        int[] rightChild = Arrays.copyOfRange(array,mid,array.length);
        return merge(sort(leftChild),sort(rightChild));//递归
    }

    public static int[] merge(int[] left,int[] right){

        int[] result = new int[left.length+right.length];

        for (int index = 0,leftIndex=0,rightIndex=0; index < result.length; index++) {
            if (leftIndex>=left.length){//说明左子数组已经全部merge了
                result[index] = right[rightIndex++];//rightIndex++ 后加
            } else if (rightIndex>=right.length){//说明右子数组已经全部merge了
                result[index] = left[leftIndex++];
            } else if (left[leftIndex]>right[rightIndex]){
                result[index] = right[rightIndex++];
            } else {
                result[index] = left[leftIndex++];
            }
        }

        return result;
    }

    public static void main(String[] args){
        PrintArray.print(PrintArray.SRC);
        System.out.println("======================================================");
        PrintArray.print(sort(PrintArray.SRC));
    }
}
