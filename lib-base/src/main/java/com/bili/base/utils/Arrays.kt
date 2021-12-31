package com.bili.base.utils


inline fun <T> Array<T>.percentage(predicate: (T) -> Boolean) =
  filter(predicate).size.toFloat() / size
