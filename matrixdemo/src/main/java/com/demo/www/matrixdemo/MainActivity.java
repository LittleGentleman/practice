package com.demo.www.matrixdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private SimpleAdapter adapter;
    private List list = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        Map<String,Object> map1 = new HashMap<>();
        map1.put("title","01 canvas基本图形的绘制");
        map1.put("content","通过canvas、使用Paint进行基本图形的绘制");
        list.add(map1);

        Map<String,Object> map2 = new HashMap<>();
        map2.put("title","02 canvas变换");
        map2.put("content","通过canvas实现平移、旋转、缩放、错切变换");
        list.add(map2);

        Map<String,Object> map3 = new HashMap<>();
        map3.put("title","03 Canvas保存于回滚");
        map3.put("content","canvas的堆栈操作实现Canvas保存于回滚");
        list.add(map3);

        Map<String,Object> map4 = new HashMap<>();
        map4.put("title","04 Canvas合成");
        map4.put("content","介绍Canvas如何合成");
        list.add(map4);

        Map<String,Object> map5 = new HashMap<>();
        map5.put("title","05 文字绘制方法");
        map5.put("content","常用的文字绘制方法");
        list.add(map5);

        Map<String,Object> map6 = new HashMap<>();
        map6.put("title","06 绘制圆形头像");
        map6.put("content","介绍两种绘制圆形头像的方法，第二种在图像的边缘有锯齿");
        list.add(map6);

        Map<String,Object> map7 = new HashMap<>();
        map7.put("title","07 Matrix矩阵");
        map7.put("content","Matrix矩阵的各种操作，内与多个例子，可以自己调试");
        list.add(map7);

        Map<String,Object> map8 = new HashMap<>();
        map8.put("title","08 自定义drawable");
        map8.put("content","自定义drawable实现一键清除动画");
        list.add(map8);

        Map<String,Object> map9 = new HashMap<>();
        map9.put("title","09 浏览图片demo");
        map9.put("content","通过拖动控制顶点，实现图片的旋转、平移、缩放，还有view的事件响应");
        list.add(map9);

        adapter = new SimpleAdapter(this,list,android.R.layout.simple_list_item_2,
                new String[]{"title","content"},new int[]{android.R.id.text1,android.R.id.text2});

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(getApplication(),CanvasDrawActivity.class);
                        startActivity(intent);
                        break;

                    case 1:
                        intent = new Intent(getApplication(),CanvasTransformationActivity.class);
                        startActivity(intent);
                        break;

                    case 2:
                        intent = new Intent(getApplication(),CanvasStackActivity.class);
                        startActivity(intent);
                        break;

                    case 3:
                        intent = new Intent(getApplication(),CanvasComposeActivity.class);
                        startActivity(intent);
                        break;

                    case 4:
                        intent = new Intent(getApplication(),TextDrawActivity.class);
                        startActivity(intent);
                        break;

                    case 5:
                        intent = new Intent(getApplication(),BitmapDrawActivity.class);
                        startActivity(intent);
                        break;

                    case 6:
                        intent = new Intent(getApplication(),MatrixActivity.class);
                        startActivity(intent);
                        break;

                    case 7:
                        intent = new Intent(getApplication(),SelfDrawableActivity.class);
                        startActivity(intent);
                        break;

                    case 8:
                        intent = new Intent(getApplication(),CommonImgEffectActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}
