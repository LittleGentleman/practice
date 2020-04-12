package com.example.gmm.mactestapp;

import android.util.Log;

public class SingleLinkedList<T> {

    //定义链表的size
    public int size = 0;
    private Node<T> firstNode;
    private Node<T> lastNode;

    /**
     * 增
     * @param item
     */
    //在最后面添加一个节点
    public void insert(T item) {
        Node<T> l = lastNode;
        Node<T> newItem = new Node<>(item,null);
        lastNode = newItem;//新添加的节点为最后一个节点
        if (l == null) {//如果之前是空链表，第一个节点也是这个新添加的节点
            firstNode = newItem;
        } else {//如果之前链表内有节点，之前的最后一个节点的指针指向新添加的节点
            l.next = newItem;
        }
        size++;
    }

    //在中间插入一个新的节点
    public void insert(T item,int index) {
        if (index > 0 && index <= size) {//安全判断，防止下角标越界
            Node<T> NewNode = new Node<>(item,null);
            if (size != 0) {//链表不为空
                if (index == 0) {//插在头部
                    NewNode.next = firstNode;
                    firstNode = NewNode;
                    size++;
                } else if (index == size) {//插在尾部
                    insert(item);
                } else  {//在链表中间插入
                    Node<T> preNode = firstNode;
                    Node<T> nextNode = firstNode.next;
                    int i=1;
                    while (i < index) {
                        preNode = nextNode;
                        nextNode = preNode.next;
                        i++;
                    }
                    NewNode.next = nextNode;
                    preNode.next = NewNode;
                    size++;
                }
            } else {//链表为空，添加第一个节点
                insert(item);
            }

        } else  {
            throw  new IndexOutOfBoundsException();
        }
    }

    /**
     * 删
     * @return
     */
    public T remove(int index) {
        if (index < 0 || index > size-1) {
            throw  new IndexOutOfBoundsException();
        }
        T t;
        if (index == 0) {
            Node<T> node = firstNode.next;
            t = firstNode.item;
            firstNode.next = null;
            firstNode = node;
            size--;
        } else if (index == size-1) {
            Node<T> node = firstNode;
            for (int i=1; i<size-1; i++) {
                node = node.next;
            }
            //跳出循环的node 是倒数第二个node
            t = node.next.item;
            node.next = null;
            lastNode = node;
            size--;
        } else  {
            Node<T> node = firstNode;
            for (int i=1;i<index;i++) {
                node = node.next;
            }
            //待删除的节点
            Node<T> removeNode = node.next;
            t = removeNode.item;
            node.next = node.next.next;
            removeNode.next = null;
            size--;
        }
        return t;
    }

    public void remove(T t) {
        if (size == 0) {
            throw  new IndexOutOfBoundsException();
        }
        Node<T> node = firstNode;
        for (int i=0; i<size; i++) {
            if (node.item == t) {
                remove(i);
                break;
            }
            node = node.next;
        }
    }

    public T removeLast() {
        if (size == 0) {
            throw  new IndexOutOfBoundsException();
        }
        return remove(size-1);
    }

    public T removeFirst() {
        if (size == 0) {
            throw new IndexOutOfBoundsException();
        }
        return remove(0);
    }

    /**
     * 改
     * @return
     */
    public void set(T t,int index) {
        if (index<0 || index>size-1) {
            throw new IndexOutOfBoundsException();
        }
         Node<T> node = firstNode;
        for (int i=0; i<index; i++) {
            node = node.next;
        }
        node.item = t;
    }


    /**
     * 查
     * @return
     */
    public T getItem(int index) {
        if (index<0 || index>size-1) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> node = firstNode;
        for (int i=0; i<index; i++) {
            node = node.next;
        }
        return node.item;
    }

    public int getSize() {
        return size;
    }

    /**
     * 定义节点类, List就是由一个一个节点组成的，每个节点除了数据，还会包含指向下一个节点的指针（地址）
     */
    private class Node<T> {
        private  T item;
        private Node<T> next;
        public Node(T item,Node<T> next) {
            this.item = item;
            this.next = next;
        }
    }

    /**
     * 逆置
     * @return
     */
    public void reverse() {
        if (size > 1) {
            Node<T> preNode = null;//前一个节点 （首节点的前一个节点为null）
            Node<T> curNode = firstNode;//当前节点

            while (curNode != null) {//
                Node<T> nextNode = curNode.next;
                if (nextNode == null) {//说明当前的curNode是最后一个节点
                    lastNode = firstNode;//逆置后的尾节点为逆置前的首节点
                    lastNode.next = null;
                    firstNode = curNode;//逆置后的首节点为逆置前的尾节点
                }
                
                curNode.next = preNode;//next 指向前一个节点

                preNode = curNode;
                curNode = nextNode;
            }
        }
    }


    @Override
    public String toString() {
        return "SingleLinkedList{" +
                "size=" + size +
                '}';
    }
}
