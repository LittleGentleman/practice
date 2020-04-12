package com.gmm.wanandroiddemo

import android.content.Context
import android.content.SharedPreferences
import java.lang.IllegalArgumentException
import kotlin.reflect.KProperty

class PreferenceUtil<T>(var key:String,val default:T) {
    private val prefs : SharedPreferences by lazy { MyApp.instance().getSharedPreferences("wanandroid_prefs",Context.MODE_PRIVATE) }

    /**
     *重载委托getValue setValue
     */
    operator fun getValue(thisRef:Any?,property:KProperty<*>):T{
        return getSharedPreferences(key,default)
    }

    operator fun setValue(thisRef: Any?,property: KProperty<*>,value: T){
        putSharedPreferences(key,value)
    }

    //with() 高阶函数
    private fun putSharedPreferences(key:String,value:T) = with(prefs.edit()){
        when(value){
            is Int -> putInt(key,value)
            is Long -> putLong(key,value)
            is Float -> putFloat(key,value)
            is Boolean -> putBoolean(key,value)
            is String -> putString(key,value)
            else -> throw IllegalArgumentException()
        }
    }.apply()

    private fun getSharedPreferences(key:String,defaultValue:T):T = with(prefs){
        val result = when(defaultValue){
            is Int -> getInt(key,defaultValue)
            is Long -> getLong(key,defaultValue)
            is Float -> getFloat(key,defaultValue)
            is Boolean -> getBoolean(key,defaultValue)
            is String -> getString(key,defaultValue)
            else -> throw  IllegalArgumentException()
        }
        return result as T
    }
}