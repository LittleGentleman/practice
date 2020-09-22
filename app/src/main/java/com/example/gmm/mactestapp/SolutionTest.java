package com.example.gmm.mactestapp;


/**
 * @author:gmm
 * @date:2020/8/3
 * @类说明:
 */
public class SolutionTest {

    public static void main(String[] args) {

        System.out.println(longestPalindrome("abababa"));
    }

    public static String longestPalindrome(String s) {

        int[] range = new int[2];
        char[] array = s.toCharArray();

        for (int i = 0; i < array.length; i++) {
            i = findLongest(array,range,i);
        }

        return s.substring(range[0],range[1]+1);

    }

    public static int findLongest(char[] array,int[] range,int low) {

        int high = low;
        while (high < array.length-1 && array[low] == array[high+1]) {
            high ++;
        }

        int ans = high;//下一次回文字符串的开始位置
        while (low > 0 && high < array.length - 1 && array[low-1] == array[high+1]) {
            low--;
            high++;
        }

        if (high - low  > range[1] - range[0]) {
            range[0] = low;
            range[1] = high;
        }

        return ans;

    }


}
