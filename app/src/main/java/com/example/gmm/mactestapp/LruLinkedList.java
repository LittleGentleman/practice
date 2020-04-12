package com.example.gmm.mactestapp;

/**
 * 单链表实现--LRU算法  因为内存限制，所以需要缓存淘汰算法
 * @param <E>
 */
public class LruLinkedList<E> extends MineLinkedList<E> {

    int memorySize;//用于限定内存空间大小，也就是缓存的大小  节点数量
    static final int DEFAULT_CAP = 5;
    public LruLinkedList() {
        this(DEFAULT_CAP);
    }

    public LruLinkedList(int defaultMemorySize) {
        this.memorySize = defaultMemorySize;
    }

    //LRU添加节点 ①新数据插入到链表头部
    public void lruPut(E data) {
        if (data != null) {
            if (size >= memorySize) {//缓存已满
                removeLast();//淘汰链表尾部的节点
                put(data);//新的节点添加到链表头部
            } else  {//缓存没满，直接将新节点添加到链表头部
                put(data);
            }

        }
    }

    //LRU删除节点  ③当链表满的时候，将链表尾部的数据丢弃
    public E lruRemove() {
       return removeLast();
    }

    //LRU访问 ②当缓存命中（即缓存数据被访问），数据要移到表头
    public E lruGet(int index) {
        checkIndex(index);
        //1、查找节点
        Node curNode = first;
        Node preNode = null;
        for (int i = 0; i < index; i++) {
            preNode = curNode;
            curNode = curNode.next;
        }
        //2、将命中节点移到表头
        preNode.next = curNode.next;
        curNode.next = first;
        first = curNode;
        return curNode.data;
    }
}
