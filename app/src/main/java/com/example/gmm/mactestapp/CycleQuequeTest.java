package com.example.gmm.mactestapp;

import java.util.LinkedList;
import java.util.Stack;

public class CycleQuequeTest<E> {
    private int front,rear;
    private E[] elements;
    private int size;
    public CycleQuequeTest(int elementSize){
        elements = (E[]) new Object[elementSize+1];
        front = 0;
        rear = 0;
        size = 0;
    }

    public void enqueue(E item) {
        if ((rear+1)%elements.length == front) {//队列已满，需要扩容
            resize(getElementSize()*2);
        }

        elements[rear] = item;
        rear = (rear+1)%elements.length;
        size++;
    }

    public void dequeue() {
        if (isEmpty()) {
            throw  new IllegalArgumentException();
        }
        elements[front] = null;
        front = (front+1)%elements.length;
        size--;

        if (size == getElementSize()/4 && getElementSize()/2 != 0) {
            resize(getElementSize()/2);
        }
    }

    private void resize(int elementSize) {
        E[] newElements = (E[]) new Object[elementSize+1];
        for (int i = 0; i < elements.length; i++) {
            newElements[i] = elements[(i+front)%elements.length];
        }
        elements = newElements;
        front = 0;
        rear = size;
    }

    public int getElementSize() {
        return elements.length-1;
    }

    public boolean isEmpty() {
        return front == rear;
    }
}
