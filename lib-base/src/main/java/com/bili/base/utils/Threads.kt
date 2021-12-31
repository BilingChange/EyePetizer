package com.bili.base.utils

import android.os.Handler
import android.os.Looper


val mainHandler = lazy { Handler(Looper.getMainLooper()) }

fun mainThread(block: () -> Unit) {
  if (Looper.myLooper() == Looper.getMainLooper()) {
    block()
  } else {
    mainHandler.value.post(block)
  }
}

fun mainThread(delayMillis: Long, block: () -> Unit) =
  mainHandler.value.postDelayed(block, delayMillis)
