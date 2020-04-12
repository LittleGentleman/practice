package com.example.gmm.mactestapp;

import android.util.Log;

/**
 * 运算符栈，用于存放运算符  数组实现栈，数组长度固定，会有不必要的内存开销  可以使用链表实现栈
 * @param <E>
 */
public class ExpressionStack<E> {
    private int top;
    private E[] elements;
    private int size;
    public ExpressionStack(int elementSize){
        elements = (E[]) new Object[elementSize];
        top = -1;
        size = 0;
    }

    public void push(E obj){
        if (size==elements.length){
            throw  new  IndexOutOfBoundsException();
        }

        elements[++top] = obj;
        size++;
    }

    public E peek(){
        if (top == -1){
            throw new IndexOutOfBoundsException();
        }
        E obj = elements[top];
        return obj;
    }

    public E pop(){
        if (top == -1){
            throw new IndexOutOfBoundsException();
        }
        E obj = elements[top];
        elements[top] = null;
        top--;
        size--;
        return obj;
    }

    public boolean isEmpty(){
        return size==0;
    }

    @Override
    public String toString() {
        for (int i = 0; i < size; i++) {
            System.out.print(elements[i] + " ");
        }
        System.out.println();
        return super.toString();
    }
}
