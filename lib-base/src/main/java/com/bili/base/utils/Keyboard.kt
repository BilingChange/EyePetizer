package com.bili.base.utils

import android.widget.EditText
import androidx.core.view.WindowInsetsCompat.Type


fun EditText.showKeyboard() =
  windowInsetsControllerCompat?.show(Type.ime())

fun EditText.hideKeyboard() =
  windowInsetsControllerCompat?.hide(Type.ime())

fun EditText.toggleKeyboard() =
  if (isKeyboardVisible) hideKeyboard() else showKeyboard()

inline val EditText.isKeyboardVisible: Boolean
  get() = rootWindowInsetsCompat?.isVisible(Type.ime()) == true

inline val EditText.keyboardHeight: Int
  get() = rootWindowInsetsCompat?.getInsets(Type.ime())?.bottom ?: -1
