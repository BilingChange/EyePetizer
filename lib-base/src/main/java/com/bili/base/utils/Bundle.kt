package com.bili.base.utils

import android.os.Bundle


inline operator fun <reified T> Bundle?.get(key: String): T? =
  this?.get(key).let { if (it is T) it else null }
