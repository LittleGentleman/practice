package com.gmm.kotlindemo

//(a:Int,b:Int) -> Int 函数类型  作为一个参数传入     ----高阶函数
fun add(c:Int,d:Int,sum:(a:Int,b:Int) -> Int):Int{
    return sum.invoke(c,d)

}

//重载方法  关键字operator
data class Test1(var x:Int,var y:Int){

    //重载 + 方法
    operator fun plus(target: Test1):Test1{
        return Test1(x+target.x,y+target.y)
    }
}

//重载 ++ 方法  跟扩展函数一样
operator fun Test1.inc():Test1{
    return Test1(++x,++y)
}

/**
 * 1.inline 内联函数修饰
 * 2.泛型是类型擦拭的 如果要获取类型信息 需要用reified修饰泛型T
 */
inline fun <reified T> getType(){
    println(T::class)
}

//扩展函数 不可以重载
fun MutableList<Int>.swap(from: Int,to: Int){
    var tmp = this[from]
    this[from] = this[to]
    this[to] = tmp
}

//扩展属性  list的个数是否是偶数
val MutableList<Int>.isEven:Boolean
    get() = this.size % 2 ==0


interface A {
    fun display(msg:String)
}

class AImpl : A{
    override fun display(msg: String) {
        println("***$msg***")
    }
}

//委托AImpl
class B : A by AImpl()

fun main(args: Array<String>) {
//    add { a, b -> a+b }

    //{a,b -> a+b}的函数类型是(a:Int,b:Int) -> Int
    println("sum=${add(2,4,{a,b -> a+b})}")

    val test1:Test1 = Test1(2,3)
    val  test2:Test1 = Test1(1,2)
    var test3 = test1 + test2
    println("test1+test2=$test3")
    test3++
    println("test3++=$test3")

    // 2..6  是闭区间 [2,6]
    for (i in 2..6){
//        println(i)
    }

    //2 until 6 半开区间 [2,6)
    for (i in 2 until 6){
//        println(i)
    }

    for (i in 10  downTo 6 step 2){
        println(i)
    }

    val array = arrayOf(2,4,6,8)
    for ((index,value) in array.withIndex()){
        println("index:$index,value:$value")
    }

    println(array.all { it > 4 })
    println(array.any { it > 4 })

    //数组转成 map
    var map = array.associate { "A$it" to it }
    println(map)

    val array02 = arrayOf("1","3","5","7")
    for (str in array02){
        println(str)
    }

    getType<String>()

    val b:B = B()
    b.display("kotlin")
}