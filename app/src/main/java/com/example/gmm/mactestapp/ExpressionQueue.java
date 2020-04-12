package com.example.gmm.mactestapp;

/**
 * 队列：用于存放数据和操作数  使用数组实现，缺点 数组长度固定，会有不必要的内存开销   可以使用链表来实现队列
 * @param <E>
 */
public class ExpressionQueue<E> {
    private E[] elements;
    private int size;
    private int head,rear;//队头 队尾角标

    public ExpressionQueue(int elementSize){
        elements = (E[]) new Object[elementSize+1];
        head = 0;
        rear = 0;
        size = 0;
    }

    //插入到队头
    public void enqueue(E item){
        if (size>elements.length-1){
            return;
        }
        elements[rear] = item;
        ++rear;
        ++size;
    }

    //队头出队
    public E dequeue(){
        if (size == 0) {
            throw new IndexOutOfBoundsException();
        }
        E obj = elements[head];
        elements[head] = null;//GC回收
        ++head;
        --size;
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
