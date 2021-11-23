@file:JvmName("BindingHolderUtil")
@file:Suppress("unused")

package com.bili.base.utils

import android.view.View
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @description:
 *
 * @author: Y.F
 * @e-mail: bilingchange@126.com
 * @date: on 2021/11/23    18:01.
 **/

@JvmName("bind")
fun <VB : ViewBinding> BaseViewHolder.withBinding(bind: (View) -> VB): BaseViewHolder =
  BaseViewHolderWithBinding(bind(itemView))

@JvmName("getBinding")
@Suppress("UNCHECKED_CAST")
fun <VB : ViewBinding> BaseViewHolder.getViewBinding(): VB {
  if (this is BaseViewHolderWithBinding<*>) {
    return binding as VB
  } else {
    throw IllegalStateException("The binding could not be found.")
  }
}

class BaseViewHolderWithBinding<VB : ViewBinding>(val binding: VB) : BaseViewHolder(binding.root)