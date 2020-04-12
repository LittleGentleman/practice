package com.example.gmm.mactestapp;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * 逆波兰表达式 转化流程
 */
public class ReversePolishNotation {
    private String infixExpression;

    //存放中缀表达式的队列
    private ExpressionQueue<String> infixQueue;

    private ExpressionStack<String> operatorStack;//运算符栈

    private ExpressionQueue<String> numberQueue;//操作数队列

    private Map<String,Integer> priority;

    public ReversePolishNotation(String expression){
        this.infixExpression = expression;
        infixQueue = new ExpressionQueue<>(infixExpression.length());
        operatorStack = new ExpressionStack<>(infixExpression.length());
        numberQueue = new ExpressionQueue<>(infixExpression.length());

        priority = new HashMap<>();
        priority.put("+",1);
        priority.put("-",1);
        priority.put("*",10);
        priority.put("/",10);
        initInfixQueue();
    }

    private void initInfixQueue(){
        for (int i = 0; i < infixExpression.length(); i++) {
            infixQueue.enqueue(String.valueOf(infixExpression.charAt(i)));
        }
//        infixQueue.toString();
    }

    public String reverse(){
        while (!infixQueue.isEmpty()){
            String c = infixQueue.dequeue();
            if (c.equals("(")) {
                operatorStack.push(c);
            } else if (c.equals(")")){
                while (!operatorStack.peek().equals("(")){
                    String top = operatorStack.peek();
                    numberQueue.enqueue(top);
                    operatorStack.pop();
                }
                operatorStack.pop();
            } else if(c.equals("+")||c.equals("-")||c.equals("*")||c.equals("/")) {
                if (operatorStack.isEmpty()){
                    operatorStack.push(c);
                } else {
                    if (operatorStack.peek().equals("(")){
                        operatorStack.push(c);
                    } else if (priority.get(c)>priority.get(operatorStack.peek())){
                        operatorStack.push(c);
                    } else if (priority.get(c)<=priority.get(operatorStack.peek())){
                        while (!operatorStack.isEmpty()&&priority.get(operatorStack.peek())>=priority.get(c)&&!operatorStack.peek().equals('(')){
                            numberQueue.enqueue(operatorStack.pop());
                        }
                        operatorStack.push(c);
                    }
                }

            } else {
                numberQueue.enqueue(c);
            }
        }

        while (!operatorStack.isEmpty()){
            numberQueue.enqueue(operatorStack.pop());
        }

//        operatorStack.toString();
        return numberQueue.toString();
    }


    public static void main(String attrs[]){
        ReversePolishNotation polishNotation = new ReversePolishNotation("(3-1)+9*3+10/2");

        System.out.println(polishNotation.reverse());
    }
}
