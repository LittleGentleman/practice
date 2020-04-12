package com.example.gmm.mactestapp.generic;

/**
 * 类型变量的限定
 * @param <T>
 */
public class TypeLimit2<T extends Comparable> {
    private T data;

    public T min(T outer) {
        if (this.data.compareTo(outer) > 0)
            return outer;
        else
            return this.data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static void main(String[] args) {
        TypeLimit2<String> typeLimit = new TypeLimit2<>();
        typeLimit.setData("mark");
        System.out.println(typeLimit.min("av"));
    }
}
