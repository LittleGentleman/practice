package com.gmm.kotlindemo


/**
 *  JAVA            Kotlin
 * ？extends        out/协变      上界（只能取，不能存）
 * ？super          in/逆变       下界（只能存，不能取）
 */


fun main(args: Array<String>) {
    test("Kotlin") {
        var str = it + "123"
        println(str)
        str
    }



    test1(::hello)

    fun1()
}


fun test(a: String, display: (String) -> String) {//高阶函数
    display(a)
}

fun hello() {
    println("hello World")
}

fun test1(func: () -> Unit) {
//    fun test2(){
    func()
//    }
//    return test2()
}


//扩展函数
fun fun1() {
    var str = "kotlin"

    str.run {
        //扩展函数
        var len = this.length
        println("字符串长度：$len")
    }

    str.apply {

    }.apply {

    }

    str.let {
        it.reversed()
    }.run {
        println(this)
    }
}

