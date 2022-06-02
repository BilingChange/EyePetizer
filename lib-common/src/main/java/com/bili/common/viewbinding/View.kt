package com.bili.common.viewbinding

import android.view.View
import androidx.viewbinding.ViewBinding

inline fun <reified VB : ViewBinding> View.getBinding() = getBinding(VB::class.java)

@Suppress("UNCHECKED_CAST")
fun <VB : ViewBinding> View.getBinding(clazz: Class<VB>) =
    getTag(Int.MIN_VALUE) as? VB ?: (clazz.getMethod("bind", View::class.java)
        .invoke(null, this) as VB)
        .also { setTag(Int.MIN_VALUE, it) }
