package com.bili.base.utils


inline fun <T> List<T>.percentage(predicate: (T) -> Boolean) =
  filter(predicate).size.toFloat() / size
