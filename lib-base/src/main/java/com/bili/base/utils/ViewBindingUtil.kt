@file:JvmName("ViewBindingUtil")
@file:Suppress("UNCHECKED_CAST", "unused")

package com.bili.base.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

/**
 * @description:
 *
 * @author: Y.F
 * @e-mail: bilingchange@126.com
 * @date: on 2021/11/23    18:01.
 **/

@JvmName("inflateWithGeneric")
fun <VB : ViewBinding> Any.inflateBindingWithGeneric(layoutInflater: LayoutInflater): VB =
  withGenericBindingClass<VB>(this) { clazz ->
    clazz.getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as VB
  }.also { binding ->
    if (this is ComponentActivity && binding is ViewDataBinding) {
      binding.lifecycleOwner = this
    }
  }

@JvmName("inflateWithGeneric")
fun <VB : ViewBinding> Any.inflateBindingWithGeneric(layoutInflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean): VB =
  withGenericBindingClass<VB>(this) { clazz ->
    clazz.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
      .invoke(null, layoutInflater, parent, attachToParent) as VB
  }.also { binding ->
    if (this is Fragment && binding is ViewDataBinding) {
      binding.lifecycleOwner = viewLifecycleOwner
    }
  }

@JvmName("inflateWithGeneric")
fun <VB : ViewBinding> Any.inflateBindingWithGeneric(parent: ViewGroup): VB =
  inflateBindingWithGeneric(LayoutInflater.from(parent.context), parent, false)

fun <VB : ViewBinding> Any.bindViewWithGeneric(view: View): VB =
  withGenericBindingClass<VB>(this) { clazz ->
    clazz.getMethod("bind", View::class.java).invoke(null, view) as VB
  }.also { binding ->
    if (this is Fragment && binding is ViewDataBinding) {
      binding.lifecycleOwner = viewLifecycleOwner
    }
  }

private fun <VB : ViewBinding> withGenericBindingClass(any: Any, block: (Class<VB>) -> VB): VB {
  var genericSuperclass = any.javaClass.genericSuperclass
  var superclass = any.javaClass.superclass
  while (superclass != null) {
    if (genericSuperclass is ParameterizedType) {
      genericSuperclass.actualTypeArguments.forEach {
        try {
          return block.invoke(it as Class<VB>)
        } catch (e: NoSuchMethodException) {
        } catch (e: ClassCastException) {
        } catch (e: InvocationTargetException) {
          throw e.targetException
        }
      }
    }
    genericSuperclass = superclass.genericSuperclass
    superclass = superclass.superclass
  }
  throw IllegalArgumentException("There is no generic of ViewBinding.")
}
