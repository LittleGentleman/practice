package com.example.gmm.mactestapp;

public class LinkedList<E> {
    //链表的第一个节点
    private Node<E> first;
    //链表的最后一个节点
    private Node<E> last;
    //链表长度
    public int size;

    public LinkedList() {
        this.size = 0;
    }

    /**
     * 插入
     * 默认插入到链表头部
     */
    public void insert(E item) {
        if (item != null) {
            Node<E> node = new Node<>(item,null,first);
            first = node;
            size++;
        }
//
//
//
//        Node<E> l = last;
//        Node<E> node = new Node<>(item,null,null);
//        last = node;
//        if (l == null) {//空链表
//            first = node;
//        } else  {//非空链表
//            node.pre = last;
//            l.next = node;
//        }
//        size++;
    }

    /**
     * 根据索引插入数据
     */
    public void insert(E item,int index) {
        checkIndex(index);
        if (item != null) {
            if (index == 0) {
                insert(item);
            } else  {
                Node<E> curNode = first;
                Node<E> preNode = null;
                for (int i = 0; i < index; i++) {
                    preNode = curNode;
                    curNode = curNode.next;
                }
                Node<E> node = new Node<>(item,preNode,curNode);
                preNode.next = node;
                curNode.pre = node;
                size++;
            }

        }
//        Node<E> node = new Node<>(item,null,null);
//        if (index == 0) {
//            if (first == null) {//空链
//                insert(item);
//            } else  {
//                node.next = first;
//                first.pre = node;
//                first = node;
//            }
//            size++;
//        } else if ((index == size)) {
//            insert(item);
//        } else {
//            int location = 1;
//            Node<E> curNode = first;
//            Node<E> nextNode = first.next;
//            while (location < index) {
//                curNode = nextNode;
//                nextNode = curNode.next;
//                location++;
//            }
//            curNode.next = node;
//            node.pre = curNode;
//            node.next = nextNode;
//            nextNode.pre = node;
//            size++;
//        }
    }

    /**
     * 查
     * @param index
     * @return
     */
    public E getItem(int index) {
        checkIndex(index);

        Node<E> curNode = first;
        for (int i = 0; i < index; i++) {
            curNode = curNode.next;
        }

        return curNode.data;

    }

    /**
     * 删
     * @param index
     * @return
     */
    public E remove(int index) {
        checkIndex(index);

        Node<E> curNode = first;
        Node<E> preNode = null;
        for (int i = 0; i < index; i++) {
            preNode = curNode;
            curNode = curNode.next;
        }
        Node<E> node = curNode;
        preNode.next = curNode.next;
        curNode.next.pre = preNode;
        curNode.next = null;
        curNode.pre = null;

        size--;
        return node.data;

    }

    /**
     * 改
     * @param item
     * @param index
     */
    public void set(E item,int index) {
        checkIndex(index);
        Node<E> curNode = first;
        Node<E> preNode = null;
        for (int i = 0; i < index; i++) {
            preNode = curNode;
            curNode = curNode.next;
        }
        curNode.data = item;
    }

    private void checkIndex(int index) {
        if (!(index>=0 && index<=size))
            throw new IndexOutOfBoundsException();
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


    /**
     * 定义节点，双向链表是由这一个一个节点组成，每个节点包括数据、前一个节点的指针、后一个节点的指针
     */
    private class Node<E> {
        E data;
        private Node<E> pre;
        private Node<E> next;

        public Node(E data,Node<E> pre, Node<E> next) {
            this.data = data;
            this.pre = pre;
            this.next = next;
        }
    }
}
