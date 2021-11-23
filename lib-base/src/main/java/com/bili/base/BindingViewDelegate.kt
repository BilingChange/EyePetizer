package com.bili.base

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bili.base.utils.inflateBindingWithGeneric
import com.drakeet.multitype.ItemViewDelegate

/**
 * @description:
 *
 * @author: Y.F
 * @e-mail: bilingchange@126.com
 * @date: on 2021/11/23    18:00.
 **/

abstract class BindingViewDelegate<T, VB : ViewBinding> :
  ItemViewDelegate<T, BindingViewDelegate.BindingViewHolder<VB>>() {

  override fun onCreateViewHolder(context: Context, parent: ViewGroup): BindingViewHolder<VB> {
    return BindingViewHolder(inflateBindingWithGeneric(parent))
  }

  class BindingViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)
}