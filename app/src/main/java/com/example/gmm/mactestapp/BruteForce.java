package com.example.gmm.mactestapp;

/**
 * 字符串朴素的模式匹配算法 -- 暴力算法
 */
public class BruteForce {

    public static void main(String[] args) {
        String mainStr = "BBC ABCDAB ABCDABCDABDE";
        String subStr = "ABCDABD";
        System.out.println("主串：" + mainStr);
        System.out.println("子串：" + subStr);
        
        bruteForce(mainStr,subStr);
    }

    private static void bruteForce(String mainStr, String subStr) {
        int mainLength = mainStr.length();
        int subLength = subStr.length();
        if (subLength > mainLength) {
            System.out.println("Error");
            return;
        }

        int i=0,j=0;
        while (i<mainLength && j<subLength) {
            if (mainStr.charAt(i) == subStr.charAt(j)) {
                i++;
                j++;
            } else {
                i = i-j+1;//主串回溯到上次开始匹配的下一个字符
                j = 0;
            }
        }

        if (j>=subLength){
            System.out.println("Success! index=" + (i-j));
        } else {
            System.out.println("Fail");
        }
    }
}
