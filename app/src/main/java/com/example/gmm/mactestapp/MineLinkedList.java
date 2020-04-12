package com.example.gmm.mactestapp;

/**
 * 单链表
 */
public class MineLinkedList<E> {
    public int size;
    //头部节点
    protected Node first;

    public MineLinkedList() {
        this.size = 0;
    }

    //添加节点
    //添加到头部
    public void put(E data) {
        if (data != null) {
            Node node = new Node(data,first);
            first = node;
            size++;
        }
    }

    //添加到指定位置
    public void put(E data,int index) {
        checkIndex(index);
        if (data != null) {
            if (index==0) {
                put(data);
            } else  {
                Node curNode = first;
                Node preNode = null;
                for (int i = 0; i < index; i++) {
                    preNode = curNode;
                    curNode = curNode.next;
                }
                Node node = new Node(data,curNode);
                preNode.next = node;
                size++;
            }

        }
    }

    //删除节点
    //删除头部节点
    public E remove() {
        if (first != null) {
            Node head = first;
            first = first.next;
            head.next = null; //GC 回收
            size --;
            return head.data;
        }
        return null;
    }

    //删除指定节点
    public E remove(int index) {
        checkIndex(index);
        Node curNode = first;
        Node preNode = null;
        for (int i = 0; i < index; i++) {
            preNode = curNode;
            curNode = curNode.next;
        }
        preNode.next = curNode.next;
        curNode.next = null;//GC 回收
        size -- ;
        return curNode.data;
    }

    //删除尾部节点
    public E removeLast() {
        Node curNode = first;
        Node preNode = null;
        while (curNode.next != null) {//如果curNode.next == null，说明是curNode为尾节点
            preNode = curNode;
            curNode = curNode.next;
        }
        preNode.next = null;
        size--;
        return curNode.data;
    }

    //修改节点
    public void set(E data,int index) {
        checkIndex(index);
        Node curNode = first;
        for (int i = 0; i < index; i++) {
            curNode = curNode.next;
        }
        curNode.data = data;
    }

    //查询节点
    //查询头部节点
    public E get() {
        if (first != null) {
            return first.data;
        }
        return null;
    }

    //查询指定节点
    public E get(int index) {
        checkIndex(index);
        Node curNode = first;
        for (int i = 0; i < index; i++) {
            curNode = curNode.next;
        }

        return curNode.data;
    }

    public int getSize() {
        return size;
    }

    //index安全监测
    public void checkIndex(int index) {
        if (!(index>=0 && index<=size)) {
            throw  new IndexOutOfBoundsException();
        }
    }

    @Override
    public String toString() {
        Node curNode = first;
        for (int i = 0; i < size; i++) {
            System.out.print(curNode.data + " ");
            curNode = curNode.next;
        }
        System.out.println();
        return super.toString();
    }

    //节点类
    class Node {
        E data;
        Node next;
        public Node(E data,Node next) {
            this.data = data;
            this.next = next;
        }
    }

}
