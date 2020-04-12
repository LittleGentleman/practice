package com.example.gmm.mactestapp;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class MyStack {

    Deque<Integer> queue;

    /** Initialize your data structure here. */
    public MyStack() {
        queue = new LinkedList<>();
    }

    /** Push element x onto stack. */
    public void push(int x) {
        queue.addLast(x);
    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        return queue.removeLast();
    }

    /** Get the top element. */
    public int top() {
        return queue.getLast();
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {
        return queue.size()==0;
    }
}
