package com.gmm.wanandroiddemo

import android.app.Application
import com.gmm.wanandroiddemo.network.HttpClient
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class MyApp :Application() {


    //单例化的第三种方式：自定义一个非空且只能一次性赋值的委托属性
    companion object {
        //委托关键字 by
        private var instance : MyApp by NotNullSingleValueVar()
        fun instance() = instance
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        HttpClient.instance.createRetrofit()

    }

    //定义一个属性管理类，进行非空和重复赋值的判断
    private class NotNullSingleValueVar<T>() : ReadWriteProperty<Any?, T> {
        private var value: T? = null
        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return value ?: throw IllegalStateException("application not initialized")
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            this.value = if (this.value == null) value
            else throw IllegalStateException("application already initialized")
        }
    }


}