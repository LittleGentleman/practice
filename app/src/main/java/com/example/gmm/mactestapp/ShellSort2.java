package com.example.gmm.mactestapp;

public class ShellSort2 {

    public static int[] sort(int[] array){

        if (array.length==0)
            return array;

        int currentValue;
        int gap = array.length / 2;
        for (int i = gap; i < array.length; i++) {
            int preIndex = i - gap;
            currentValue = array[i];
            while (gap > 0){
                while (preIndex>=0&&currentValue<array[preIndex]){
                    array[preIndex+gap] = array[preIndex];
                    preIndex = preIndex - gap;
                }
                array[preIndex+gap] = currentValue;
            }

            gap = gap/2;
        }

        return array;
    }
}
