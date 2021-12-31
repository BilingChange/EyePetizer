package com.bili.base.utils

/**
 * @description:
 *
 * @author: Y.F
 * @e-mail: yuanfan3@01zhuanche.com
 * @date: on 2021/11/25    11:45.
 **/
class Singleton private constructor(){

    //双重校验锁式（Double Check)
    companion object{
        val instance:Singleton by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            Singleton()
        }
    }

    //静态内部类单例
//    companion object{
//        val instance = SingletonHolder.holder
//    }
//
//    private object SingletonHolder{
//        val holder = Singleton()
//    }
}