package com.gmm.www.serializabledemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Gson gson = new Gson();

        Bean bean = gson.fromJson("{\n" +
                "  \"key\": \"value\",\n" +
                "  \"simpleArray\": [1,2,3],\n" +
                "  \"arrays\": [\n" +
                "    [{\n" +
                "      \"arrInnerClsKey\": \"arrInnerClsValue\",\n" +
                "      \"arrInnerClsKeyNub\": 1\n" +
                "    }]\n" +
                "  ],\n" +
                "  \"innerclass\": {\n" +
                "    \"name\": \"zero\",\n" +
                "    \"age\": 25,\n" +
                "    \"sax\": \"男\"\n" +
                "  }\n" +
                "}", Bean.class);

        String json = gson.toJson(bean);

        //fastjson
        String json2 = JSONObject.toJSONString(bean);

        Object obj = JSONObject.parse(json2);
    }
}
