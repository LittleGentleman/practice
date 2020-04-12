package com.example.gmm.mactestapp;

/**
 * 二分查找（折半查找）
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] array = new int[]{1,2,3,4,5,6,7,8,9,10};
        int key = 8;
        int index = search(array, key);
        System.out.println(index);
    }

    private static int search(int[] array, int target){
        int start = 0;
        int end = array.length-1;
        int mid;

        while (start <= end) {
            mid = (start+end)/2;
            if (target > array[mid]){
                start = mid + 1;
            } else if (target < array[mid]) {
                end = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;//目标不在数组内
    }
}
