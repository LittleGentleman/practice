package com.gmm.kotlindemo;

public class Generic {

    static class Fruit{}

    static class Apple extends Fruit{}

    static class Banana extends Fruit{}

    //盘子容器
    static class Plate<T>{
        T item;
        public Plate(T t){
            this.item = t;
        }

        public void setItem(T t){
            this.item = t;
        }

        public T getItem(){
            return item;
        }
    }

    public static void main(String args[]){

        //定义装水果的盘子A
//        Plate<Fruit> plateA =  new Plate<Apple>(new Apple());//这么写编译不通过的，即使Apple继承了Fruit，但是JVM认为Plate<Apple> 并不是继承Plate<Fruit>的
        //? extends 是上界通配符  这种泛型是伪泛型  类型会被擦除  只能取 不能存
        Plate<? extends Fruit> plateA = new Plate<Apple>(new Apple());
//        plateA.setItem(new Banana());//不可以保存，由于类型擦除，容器Plate不能确定保存的类型是否是Fruit
        Fruit fruit = plateA.getItem();//但是可以取，取出来的肯定是Fruit类型

        //? super 是下界通配符 这种泛型是伪泛型  类型会被擦除  只能存 不能取
        Plate<? super Fruit> plateB = new Plate<Fruit>(new Banana());
        plateB.setItem(new Apple());//可以存，因为new Plate<Fruit>()，只要是Fruit的子类都可以存入Plate盘子容器内
//        Fruit fruit1 = plateB.getItem();//但是不可以取，无法取出Fruit类型，由于类型擦除，取的时候不能确定类型

        //总结 PECS:Producer Extends Consumer Super
    }
}
