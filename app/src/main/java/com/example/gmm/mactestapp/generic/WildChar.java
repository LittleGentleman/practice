package com.example.gmm.mactestapp.generic;

/**
 * 通配符  往往是在方法上使用
 */
public class WildChar {
    public static void print(GenericType<Fruit> p) {
        System.out.println(p.getData().getColor());
    }

    public static void use() {
        GenericType<Fruit> a = new GenericType<>();
        print(a);

        GenericType<Orange> b = new GenericType<>();
        /**这是不可以的，虽然Fruit和Orange是继承关系，但是GenericType<Fruit>
         * 和GenericType<Orange>是没有继承关系的，所以print()方法里是不能
         * 传入GenericType<Orange>的实例的，通配符就是解决这个问题的
         */
//        print(b);
    }

    /**
     * ? extends 上界通配符 用于安全的访问数据
     * @param p
     */
    public static void print2(GenericType<? extends Fruit> p) {
        System.out.println(p.getData().getColor());
    }

    public static void use2() {
        GenericType<Fruit> a = new GenericType<>();
        print2(a);

        GenericType<Orange> b = new GenericType<>();
        /**
         * 使用了? extends通配符后，就可以这样使用了
         */
        print2(b);

        GenericType<Food> c = new GenericType<>();
        //这样是不可以的，因为print2方法指定的传参类型是GenericType<? extends Fruit>，规定了上界是Fruit
//        print2(c);

        GenericType<? extends Fruit> d = b;
        Apple apple = new Apple();
        Fruit fruit = new Fruit();
        //上界不存：？ extends 上界通配符是不可以set的,只可以get
//        d.setData(apple);
//        d.setData(fruit);
        Fruit fruit1 = d.getData();//上界是Fruit，具体是apple还是orange哪一个类不能确定，可以肯定的是Fruit类
    }

    /**
     * ? super 下界通配符 用于安全的写入数据
     * @param p
     */
    public static void printSuper(GenericType<? super Apple> p) {
        System.out.println(p.getData());
    }

    public static void userSuper() {
        GenericType<Fruit> a = new GenericType<>();
        GenericType<Apple> b = new GenericType<>();
        GenericType<Orange> c = new GenericType<>();
        GenericType<HongFuShi> d = new GenericType<>();

        printSuper(a);//可以
        printSuper(b);//可以
        //因为printSuper方法指定的传参类型是GenericType<? super Apple>，规定了下界是Apple，
        //也就是说只能传入Apple的超类，orange和子类hongfushi都不可以
//        printSuper(c);//不可以
//        printSuper(d);//不可以

        GenericType<? super Apple> x = new GenericType<>();
        //只能set本类和子类，不能set超类
        x.setData(new Apple());//可以set
        x.setData(new HongFuShi());//可以set
//        x.setData(new Fruit());//不可以 不可以set超类
        Object data = x.getData();//get返回的一定是apple的超类，具体哪一个超类不能确定，可以肯定的是object类
    }
}
