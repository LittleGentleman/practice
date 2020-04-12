package com.gmm.kotlindemo


/**
 * 父类 open关键字
 */
open class Person{

}


/**
 * Student() 空参主构造器
 */
class Student(var name: String,var age: Int,var sex:String):Person(){
//    val a:Int = 0//val只读   private final int a;
//    var b:Int = 10//可读可写  private int b = 10;

//    var name:String//这个是属性，不是字段，不表示状态     字段 = 属性 + setter + getter
//    set(value) {
//        field = value//field幕后字段  默认生成
//    }
//    get() = field //field存储状态
//    var age:Int = 0
//    set(value) {
//        field = value
//    }
//    get() = field
//    var sex:String
//    set(value) {
//        field = value
//    }
//    get() = field


//    init {
//        this.name = name
//        this.age = age
//        this.sex = sex
//    }

    constructor():this("",0,"male"){

    }

//    /**
//     * 次构造器
//     */
//    constructor(name:String):this(){
//        this.name = name
//        this.age = 0
//        this.sex = "male"
//    }
//
//    /**
//     * 次构造器
//     */
//    constructor(name:String,age: Int):this(){
//        this.name = name
//        this.age = age
//        this.sex = "male"
//    }
//
//    /**
//     * 次构造器
//     */
//    constructor(name:String,age: Int,sex: String):this(){
//        this.name = name
//        this.age = age
//        this.sex = sex
//    }

    override fun toString(): String {
        return "姓名：$name,年龄：$age,性别：$sex"
    }

}

data class User(var name: String,var age: Int,var sex: String){

    constructor():this("",0,"male"){

    }
}


fun strDisplay(str: String?){//可空
//    println("str的长度：${str!!.length}")//非空断言
    println("str的长度：${str?.length}")//?.不为空时
}

fun main(args: Array<String>) {//不在类里的：函数  在类里的：方法
    var  stu1 = Student("Susan",17,"female")
//    stu1.name = "john"
//    stu1.age = 20
//    stu1.sex = "female"

//    stu1.name = "jack"

    println(stu1.toString())
    println("姓名：${stu1.name}")



    val  a:Int = 10//val只读
    var b:Int = 20//可读可写

    val c = 5//自动推断类型  静态语言 不会动态改变类型

    var d:Int?//包装类型Integer
    var e:Int//int


    //java里，成员变量有默认值 局部变量是没有默认值的
    //kotlin 都没有默认值


    strDisplay(null)
    println(sum(4,3))

    lateinit var lateVar:String
    lateVar = "LATEINIT"

    val lazyVal:String by lazy {
        "BY LAZY"
    }

    var user = User("Potter",10,"male")
    println(user.toString())
    println(user.name)

    var(name,_,sex) = user //解构
    println("解构:" + name + " " + sex)
}

fun sum(a:Int,b:Int)=a+b
