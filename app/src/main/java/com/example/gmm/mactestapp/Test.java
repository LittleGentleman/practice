package com.example.gmm.mactestapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author:gmm
 * @date:2020/7/25
 * @类说明:
 */
public class Test {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

//        int a = 5;
//        int b = 10;
//        List<Integer> array = new ArrayList<>();
//        while (in.hasNextInt()) {
//            a = in.nextInt();
//            b = in.nextInt();
//        }

//        for(int i=a;i<=b-2;i++) {
//
//            for (int j = i; j <= b-1; j++) {
//
//                for (int k = j; k <= b; k++) {
//                    if (i*i+j*j == k*k) {
//                        array.add(i);
//                        array.add(j);
//                        array.add(k);
//                    }
//                }
//            }
//        }
//
//        int count = 0;
//        for (int i = 0; i < array.size(); i+=3) {
//            for (int j = 2; j <= 9 ; j++) {
//                if (array.get(i)!=0 && array.get(i+1)!=0 && array.get(i+2)!=0 &&
//                        array.get(i)%j==0 && array.get(i+1)%j==0 && array.get(i+2)%j==0) {
//                    array.set(i,0);
//                    array.set(i+1,0);
//                    array.set(i+2,0);
//
//                    count++;
//                }
//            }
//        }
//
//        if (count*3 == array.size()) {
//            System.out.println("NA");
//        } else {
//            for (int i = 0; i < array.size(); i+=3) {
//                if (array.get(i) != 0) {
//                    System.out.print(array.get(i) + " ");
//                    System.out.print(array.get(i+1) + " ");
//                    System.out.print(array.get(i+2));
//                    System.out.println();
//                }
//            }
//        }

//        for (int i = 0; i < array.size(); i++) {
//            System.out.println(array.get(i) + "");
//        }






//        String str = "ABC  DEF! CDC";
//        int start = 0;
//        int end = 1;
//
//        String[] strArray = str.split(" ");
//        int size = 0;
//        for (int i = 0; i < strArray.length; i++) {
//            if (!strArray[i].trim().equals("")) {
//                ++size;
//            }
//        }
//        String[] resultArray = new String[size];
//        int index = 0;
//        for (int i = 0; i < strArray.length; i++) {
//            if (!strArray[i].trim().equals("")) {
//                resultArray[index] = strArray[i];
//                index++;
//            }
//        }
//
//        if (resultArray.length <= 1 || end-start > resultArray.length-1) {
//            System.out.println("EMPTY");
//            return;
//        }
//
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < start; i++) {
//            sb.append(resultArray[i]).append(" ");
//        }
//        for (int i = end; i >= start; i--) {
//            sb.append(resultArray[i]).append(" ");
//        }
//        for (int i = end+1; i < resultArray.length; i++) {
//            sb.append(resultArray[i]).append(" ");
//        }
//        System.out.println(sb.toString());


        longestZeros(new int[]{1,1,1,0,0,0,1,1,1,1,0},2);

    }


    public static int longestZeros (int[] A, int K) {
        // write code here

        int max = 0;

        List<Integer> array = new ArrayList<>();

        for (int i = 0; i < A.length; i++) {
            if (A[i] == 1) {
                array.add(i);
            }
        }

        if (array.size() <= K) {
            return A.length;
        } else {
            for (int i = 0; i < K; i++) {
                for (int j = 0; j < array.size(); j++) {

                }
            }
        }



        return max;
    }
}
