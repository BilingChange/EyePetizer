@file:Suppress("unused")

package com.dylanc.viewbinding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * @description:
 *
 * @author: Y.F
 * @e-mail: bilingchange@126.com
 * @date: on 2021/11/23    18:01.
 **/

inline fun <reified VB : ViewBinding> BindingViewHolder(parent: ViewGroup) =
  BindingViewHolder(inflateBinding<VB>(parent))

class BindingViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root) {
  constructor(parent: ViewGroup, inflate: (LayoutInflater, ViewGroup, Boolean) -> VB) :
      this(inflate(LayoutInflater.from(parent.context), parent, false))
}

inline fun <VB : ViewBinding> BindingViewHolder<VB>.onClick(view: View, crossinline action: VB.(Int) -> Unit) =
  apply { view.setOnClickListener { binding.action(adapterPosition) } }

inline fun <VB : ViewBinding, T> BindingViewHolder<VB>.onClick(
  view: View,
  listener: OnItemClickListener<T>,
  crossinline block: VB.(Int) -> T
) =
  onClick(view) { listener.onItemClick(block(it), it) }

inline fun <VB : ViewBinding> BindingViewHolder<VB>.onItemClick(crossinline action: VB.(Int) -> Unit) =
  onClick(itemView, action)

inline fun <VB : ViewBinding, T> BindingViewHolder<VB>.onItemClick(listener: OnItemClickListener<T>, crossinline block: VB.(Int) -> T) =
  onItemClick { listener.onItemClick(block(it), it) }

fun interface OnItemClickListener<T> {
  fun onItemClick(item: T, position: Int)
}

inline fun <T, reified VB : ViewBinding> ListAdapter(
  diffCallback: DiffUtil.ItemCallback<T>,
  crossinline onBindViewHolder: BindingViewHolder<VB>.(T) -> Unit
) = object : ListAdapter<T, BindingViewHolder<VB>>(diffCallback) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    BindingViewHolder<VB>(parent)

  override fun onBindViewHolder(holder: BindingViewHolder<VB>, position: Int) {
    onBindViewHolder(holder, currentList[position])
  }
}