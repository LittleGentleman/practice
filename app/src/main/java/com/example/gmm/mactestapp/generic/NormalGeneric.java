package com.example.gmm.mactestapp.generic;

/**
 * 泛型类
 */
public class NormalGeneric<T,K> {
    private T data;
    private K key;

    public NormalGeneric() {
    }

    public NormalGeneric(T data) {
        this.data = data;
    }

    /**
     * 这是一个普通方法，不是泛型方法
     * @return
     */
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public static void main(String[] args) {
        NormalGeneric<String,Integer> normalGeneric = new NormalGeneric<>();
        normalGeneric.setData("OK");
        normalGeneric.setKey(1);
        System.out.println(normalGeneric.getData());
    }
}
