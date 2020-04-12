package com.example.gmm.mactestapp;

import android.util.Log;

/**
 * 手写循环队列
 */
public class CycleQueue<E> {
    //队列中元素个数，队列的长度
    private int size;
    //数组
    private E[] element;
    //队头的下标
    private int front;
    //队尾的下标
    private int rear;

    public CycleQueue(int elementSize) {
        //假设循环队列总容量为N，并且预留一个空的位置最为队列空、满的长度判断标志,所以总长度+1
        element = (E[])new Object[elementSize+1];
        front = 0;
        rear = 0;
        size = 0;
    }

    public boolean isEmpty() {
        return front==rear;
    }

    /**
     * 队尾入队
     * @param e
     */
    public void enqueue(E e) {
        if ((1+rear)%element.length == front) {//队列已满，进行扩容
            resize(getElementSize()*2);
        }
        element[rear] = e;
        rear = (rear+1)%element.length;
        size++;
    }

    /**
     * 队头出队
     */
    public E dequeue() {
        if (isEmpty()) {
            throw  new IllegalArgumentException();
        }
        E e = element[front];
        element[front] = null;
        front = (front+1)%element.length;
        size--;
        //当数组长度非常大，但是队列中的元素很少，需要缩容
        if (size == getElementSize()/4 && getElementSize()/2 != 0) {//当队列中元素的个数不足数组长度的1/4时，进行缩容
            resize(getElementSize()/2);
        }

        return e;
    }

    public E getFirstElement() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        return element[front];
    }


    /**
     * 获取队列内元素个数
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * 获取数组长度
     * @return
     */
    public int getElementSize() {
        //减去队尾rear占用的位置，即真正可使用的数组长度
        return element.length-1;
    }

    /**
     * 数组长度动态变化，当队列满了，自动扩容，当容量过大，自动缩容
     * @return
     */
    private void resize(int elementSize) {
        //预留一个空的位置作为队列空、满的长度判读标志 队尾
        E[] newElement = (E[])new Object[elementSize+1];
        for (int i = 0; i < size; i++) {
            //为什么是i+front?  因为在出队的时候，队头的下标是变化的，front是记录队头所对应的索引，front对用的E才是队列的第一个元素
            //把旧数组的队头复制到新数组的第0个位置，依次排序，形成一个没有出队过的队列,
            newElement[i] = element[(i+front)%element.length];
        }
        //新数组复制
        element = newElement;
        //重置队列的队头、队尾位置
        front = 0;
        rear = size;//预留位置在数组的最后一项的下一个位置
    }


    @Override
    public String toString() {
        for (int i = 0; i < element.length; i++) {
            System.out.print(element[i] + " ");
        }
        System.out.println();
        return super.toString();
    }
}
