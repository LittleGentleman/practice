package com.example.gmm.mactestapp;

public class PrintArray {
    public final static int[] SRC = {86,11,77,23,32,45,58,63,93,4,37,22,19};

    public static void print(int[] array){
        for (int i:array){
            System.out.print(i+"    ");
        }
        System.out.println();
    }
}
