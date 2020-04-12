package com.example.gmm.mactestapp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Solution {

    private static int evalRPN(String[] tokens) {

        Stack<Integer> stack = new Stack<>();
        int m, n;
        for (String str : tokens) {
            if (str.equals("+")) {
                stack.push(stack.pop() + stack.pop());
            } else if (str.equals("-")) {
                m = stack.pop();
                n = stack.pop();
                stack.push(n - m);
            } else if (str.equals("*")) {
                stack.push(stack.pop() * stack.pop());
            } else if (str.equals("/")) {
                m = stack.pop();
                n = stack.pop();
                stack.push(n / m);
            } else {
                stack.push(Integer.parseInt(str));
            }
        }


        return stack.pop();
    }


    /**
     * 1.确定中括号内的内容
     * 2.确定重复次数
     * 3.新字符串压栈
     * 4.倒序出栈
     *
     * @param s 字符串
     * @return 返回值
     */
    private static String decodeString(String s) {
        char[] chars = s.toCharArray();
        Stack<String> stack = new Stack<>();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ']') {
                String str = "";
                while (!stack.peek().equals("[")) {//当出现'['则跳出循环
                    str = stack.pop() + str;
                }
                stack.pop();//出栈'['

                String countStr = "";
                while (!stack.isEmpty() && (stack.peek().charAt(0) >= '0' && stack.peek().charAt(0) <= '9')) {
                    countStr = stack.pop() + countStr;//数字出栈
                }

                int count = Integer.parseInt(countStr);

                String newStr = "";
                for (int j = 0; j < count; j++) {
                    newStr = newStr + str;
                }
                if (!newStr.equals("")) {
                    stack.push(newStr);//新内容入栈
                }
            } else {
                stack.push(chars[i] + "");
            }
        }

        String result = "";
        while (!stack.isEmpty()) {
            result = stack.pop() + result;
        }
        return result;
    }

    public int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        // // 双重循环 循环极限为(n^2-n)/2
//        for(int i=0;i<nums.length;i++){
//            for(int j=i+1;j<nums.length;j++){
//                if(nums[i]+nums[j]==target){
//                    result[0] = i;
//                    result[1] = j;
//                    return result;
//                }
//            }
//        }

//        时间复杂度比嵌套循环低
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {//当前值与map的key值相等，则说明这两个数的sum为target
                result[0] = map.get(nums[i]);//取出补值对应的索引
                result[1] = i;
                return result;
            }
            map.put(target - nums[i], i);//把补值存入map，value是对应的index
        }

        return result;
    }

    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }
        if (s.length() == 1) {
            return 1;
        }
        int maxLength = 0;
        int leftIndex = 0;//非重复子字符串的左边界
        for (int i = 0; i < s.length(); i++) {
            for (int j = leftIndex; j < i; j++) {//与之前的所有字符进行比较
                if (s.charAt(j) == s.charAt(i)) {//发现重复的字符
                    maxLength = Math.max(maxLength, i - leftIndex);//i-leftIndex= 不含重复字符的字符串的长度
                    leftIndex = j + 1;//更新左边界，作为下一个不含重复字符的字符串的左边界
                }
            }
        }

        return Math.max(maxLength, s.length() - leftIndex);//s.length()-leftIndex //最后一组不含重复字符的字符串长度
    }

    /**
     * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
     * 回文特点：上海自来水来自海上
     * 突破口：把回文看成中间的部分全是同一字符，左右部分相对称
     *
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        int[] range = new int[2];
        char[] chars = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            i = findLongest(chars, range, i);
        }
        return s.substring(range[0], range[1] + 1);
    }

    public static int findLongest(char[] chars, int[] range, int low) {

        int high = low;
        //中间部分的最后一个字符
        while (high < chars.length - 1 && chars[low] == chars[high + 1]) {
            high++;
        }
        int ans = high;
        //从中间向左右扩散
        while (low > 0 && high < chars.length - 1 && chars[low - 1] == chars[high + 1]) {
            low--;
            high++;
        }
        if (high - low > range[1] - range[0]) {
            range[0] = low;
            range[1] = high;
        }
        return ans;

    }

    public static String convert(String s, int numRows) {
        if (s == null || numRows <= 1 || s.length() < numRows) {
            return s;
        }
        char[] chars = s.toCharArray();
        int space = numRows * 2 - 2;
        int p = 0;
        //第一行
        for (int i = 0; i < s.length(); i = i + space) {
            chars[p++] = s.charAt(i);//p++后加
        }
        //中间行
        for (int i = 0; i < numRows - 2; i++) {
            for (int j = i + 1, k = space - (i + 1); j < s.length(); j = j + space, k = k + space) {
                chars[p++] = s.charAt(j);
                if (k < s.length()) {
                    chars[p++] = s.charAt(k);
                }
            }
        }
        //最后一行
        for (int i = numRows - 1; i < s.length(); i = i + space) {
            chars[p++] = s.charAt(i);
        }

        return String.valueOf(chars);

    }

    public static int reverse(int x) {
        long result = 0;
        while (x != 0) {
            result = result * 10 + x % 10;
            x = x / 10;
        }

        return (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) ? 0 : (int) result;
    }

    public static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        long result = 0;
        long origin = x;
        while (x != 0) {
            result = result * 10 + x % 10;
            x = x / 10;
        }
        return result == origin;
    }

    public int romanToInt(String s) {
        char[] chars = s.toCharArray();
        int result = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == 'M') {
                result = result + 1000;
            } else if (chars[i] == 'D') {
                result = result + 500;
            } else if (chars[i] == 'C') {
                if ((i + 1) < chars.length && (chars[i + 1] == 'D' || chars[i + 1] == 'M')) {
                    result = result - 100;
                } else {
                    result = result + 100;
                }
            } else if (chars[i] == 'L') {
                result = result + 50;
            } else if (chars[i] == 'X') {
                if ((i + 1) < chars.length && (chars[i + 1] == 'L' || chars[i + 1] == 'C')) {
                    result = result - 10;
                } else {
                    result = result + 10;
                }
            } else if (chars[i] == 'V') {
                result = result + 5;
            } else if (chars[i] == 'I') {
                if ((i + 1) < chars.length && (chars[i + 1] == 'V' || chars[i + 1] == 'X')) {
                    result = result - 1;
                } else {
                    result = result + 1;
                }
            }
        }
        return result;
    }


//    public String longestCommonPrefix(String[] strs) {
//        if(strs.length==0){
//            return "";
//        }
//        String first = strs[0];
//        for (int i = 1; i < strs.length-1; i++) {
//            int index = 0;
//            while (index<first.length()&&index<strs[i].length()&&first.charAt(index) == strs[i].charAt(index)){
//                index++;
//            }
//            first = first.substring(0,index);
//        }
//
//        return first;
//    }

    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        String result = strs[0];
        for (String str : strs) {
            while (!str.startsWith(result)) {
                if (result.length() == 1) {
                    return "";
                }
                result = result.substring(0, result.length() - 1);
            }
        }

        return result;
    }

    public int removeElement(int[] nums, int val) {
        if (nums.length == 0) {
            return 0;
        }

        int currentValue;
        int valCount = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i]==val){
                valCount++;
            }
        }
        for (int j = 0; j < nums.length - 1; j++) {
            int preIndex = j;
            currentValue = nums[preIndex + 1];
            while (preIndex >= 0 && currentValue != val && nums[preIndex]==val) {
                nums[preIndex + 1] = nums[preIndex];
                preIndex--;
            }
            nums[preIndex + 1] = currentValue;
        }

//        int k = 0;
//        for (int i = 0; i < nums.length; i++) {
//            if (nums[i] != val){
//                nums[k] = nums[i];
//                k++;
//            }
//        }

        return nums.length - valCount;

    }

    public int strStr(String haystack, String needle) {
        if (needle.equals("")){
            return 0;
        }
        if (haystack.contains(needle)){
            return haystack.indexOf(needle);

        } else {
            return -1;
        }
    }

    public String countAndSay(int n) {
        String pre = "1";

        for (int i = 1; i < n; i++) {
            StringBuilder temp = new StringBuilder();
            char c = pre.charAt(0);
            int count = 1;
            for (int j = 1; j < pre.length(); j++) {
                char cc = pre.charAt(j);
                if (c == cc){
                    count++;
                } else {
                    temp.append(count).append(c);
                    count = 1;
                    c = cc;
                }
            }
            temp.append(count).append(c);
            pre = temp.toString();
        }

        return pre;
    }

    public static void main(String attrs[]) {
        String[] array = {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};
        int result = evalRPN(array);
        System.out.println("结果=" + result);

        System.out.println(decodeString("a3[a2[c]]"));

        System.out.println("lengthOfLongestSubstring=" + lengthOfLongestSubstring("abcabcbb"));

        System.out.println(longestPalindrome("ceabbbabbbaff"));

        System.out.println(convert("LEETCODEISHIRING", 3));


    }


}
