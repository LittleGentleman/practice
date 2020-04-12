package com.example.gmm.mactestapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.example.gmm.mactestapp.LinkedList;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements TestInterface {
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = findViewById(R.id.info);

//        SingleLinkedList<String> list = new SingleLinkedList<>();
//        list.insert("张三");
//        list.insert("李四");
//        list.insert("王五");
//
//        list.insert("杨六",1);
//
//        for (int i=0;i<list.size;i++) {
//            Log.e("tag",i + ":" + list.getItem(i));
//        }
//
//        Log.e("tag","----------------------------------");
//
//        list.reverse();
//
//        list.set("张三三",0);
//        for (int i=0;i<list.size;i++) {
//            Log.e("tag",i + ":" + list.getItem(i));
//        }
//
//        list.insert("郑七");


//        LinkedList<String> list = new LinkedList<>();
//        list.insert("一一一一一一一一一一");
//        list.insert("二二二二二二二二二二");
//        list.insert("三三三三三三三三三三");
//        list.insert("四四四四四四四四四四");
//        list.insert("五五五五五五五五五五");
//        list.insert("六六六六六六六六六六");
//        list.insert("七七七七七七七七七七");
//
//        list.toString();
//
//
////        Log.e("tag","data=" + list.getItem(3));
//        list.insert("八八八八八八八八八八",0);
//        list.toString();
//        list.remove(7);
//        list.set("55555555555555",4);
//        for (int i=0;i<list.size;i++) {
//            Log.e("tag",i + ":" + list.getItem(i));
//        }

//        MineLinkedList<Integer> list = new MineLinkedList<>();
//        for (int i = 0; i < 5; i++) {
//            list.put(i);
//        }
//        list.toString();
//        list.put(5,0);
//        list.remove(1);

//        list.toString();
//        Log.e("Tag",list.get(3) + "");

//        LruLinkedList<Integer> list = new LruLinkedList<>(5);
//        for (int i = 0; i < 5; i++) {
//            list.lruPut(i);
//        }
//        list.toString();
//        list.lruPut(5);
//////        list.lruRemove();
////        list.lruGet(1);
////        list.lruPut(20);
//        list.toString();

//        CycleQueue<Integer> element = new CycleQueue<>(7);
//        element.enqueue(1);
//        element.enqueue(2);
//        element.enqueue(3);
//        element.enqueue(4);
//        element.enqueue(5);
//        element.enqueue(6);
//        element.enqueue(7);

//        element.dequeue();
//        element.dequeue();
//
//        element.enqueue(8);
//        element.enqueue(9);

//        element.toString();
//        Log.e("Tag",element.getFirstElement()+"");
//
//        element.enqueue(8);
//        element.enqueue(9);
//
//        element.toString();
//        Log.e("Tag",element.getFirstElement()+"");

//        ReversePolishNotation polishNotation = new ReversePolishNotation("(3-1)+9*3+10/2");
//        polishNotation.reverse();


//        List<HuffmanTree2.Node> nodes = new ArrayList<>();
//        nodes.add(new HuffmanTree2.Node("a",10));
//        nodes.add(new HuffmanTree2.Node("b",15));
//        nodes.add(new HuffmanTree2.Node("c",12));
//        nodes.add(new HuffmanTree2.Node("d",3));
//        nodes.add(new HuffmanTree2.Node("e",4));
//        nodes.add(new HuffmanTree2.Node("f",13));
//        nodes.add(new HuffmanTree2.Node("g",1));
//
//        HuffmanTree2.Node root = HuffmanTree2.createHuffmanTree(nodes);
//        HuffmanTree2.printTree(root);

        AVLTree2.Node root = null;
        root = AVLTree2.insert(root,20);//第一个为根节点
        root = AVLTree2.insert(root,10);
        root = AVLTree2.insert(root,30);
        root = AVLTree2.insert(root,28);
        root = AVLTree2.insert(root,40);//此时的二叉树为二叉平衡树

        //插入的节点会破坏平衡 需要进行LL旋转调整
        root = AVLTree2.insert(root,25);


        AVLTree2.printTree(root);

    }

    public void jump(View view) {
        Intent intent = new Intent(this,SecondActivity.class);
        startActivity(intent);
    }

    @Override
    public void changeInfo(String message) {
        info.setText(message);
    }
}
