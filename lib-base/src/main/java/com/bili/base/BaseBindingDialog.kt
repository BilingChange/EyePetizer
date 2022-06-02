package com.bili.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.bili.base.utils.ViewBindingUtil.inflateWithGeneric

/**
 * @description:
 *
 * @author: Y.F
 * @e-mail: bilingchange@126.com
 * @date: on 2021/11/23    18:01.
 **/

abstract class BaseBindingDialog<VB : ViewBinding>(context: Context, themeResId: Int = 0)
  : Dialog(context, themeResId) {

  lateinit var binding: VB

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = inflateWithGeneric(this, layoutInflater)
    setContentView(binding.root)
  }
}