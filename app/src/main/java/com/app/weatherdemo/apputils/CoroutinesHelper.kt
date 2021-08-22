package com.app.weatherdemo.apputils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object CoroutinesHelper {

    fun delayMain(delayTime:Long, work:suspend (() ->Unit)){
        CoroutineScope(Dispatchers.Main).launch {
            delay(delayTime)
            work()
        }
    }
}