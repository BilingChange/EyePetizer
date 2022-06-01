package com.bili.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bili.base.utils.inflateBindingWithGeneric

/**
 * @description:
 *
 * @author: Y.F
 * @e-mail: bilingchange@126.com
 * @date: on 2021/11/23    18:01.
 **/

abstract class BaseBindingFragment<VB : ViewBinding> : Fragment() {

  private var _binding: VB? = null
  val binding: VB get() = _binding!!

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    _binding = inflateBindingWithGeneric(layoutInflater, container, false)
    return binding.root
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}