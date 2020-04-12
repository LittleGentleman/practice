package com.example.gmm.mactestapp.generic;

/**
 * <T>这里指定的泛型只会影响普通方法，不会影响泛型方法
 * @param <T>
 */
public class GenericInterfaceImp<T> implements GenericInterface<T> {

    @Override
    public T next() {
        return null;
    }

    /**
     * 泛型方法  <K>表明这是一个泛型方法  这里的K类型可以泛型类里的T相同，也可以不同
     * 也就是说，泛型方法自己指定了泛型类型
     * @param a
     * @param <K>
     * @return
     */
    public <K> K genericMethod(K...a){
        return  a[a.length/2];
    }
}
