package com.bili.base.utils

import android.graphics.Paint
import android.os.CountDownTimer
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import kotlin.math.roundToInt


inline val TextView.textString: String get() = text.toString()

fun TextView.isTextEmpty(): Boolean = textString.isEmpty()

fun TextView.isTextNotEmpty(): Boolean = textString.isNotEmpty()

inline var TextView.isPasswordVisible: Boolean
  get() = transformationMethod != PasswordTransformationMethod.getInstance()
  set(value) {
    transformationMethod = if (value) {
      HideReturnsTransformationMethod.getInstance()
    } else {
      PasswordTransformationMethod.getInstance()
    }
  }

fun TextView.addUnderline() {
  paint.flags = Paint.UNDERLINE_TEXT_FLAG
}

fun TextView.startCountDown(
  lifecycleOwner: LifecycleOwner,
  secondInFuture: Int = 60,
  onTick: TextView.(secondUntilFinished: Int) -> Unit,
  onFinish: TextView.() -> Unit,
) {
  val countDownTimer = object : CountDownTimer(secondInFuture * 1000L, 1000) {
    override fun onTick(millisUntilFinished: Long) {
      isEnabled = false
      onTick((millisUntilFinished / 1000f).roundToInt())
    }

    override fun onFinish() {
      isEnabled = true
      this@startCountDown.onFinish()
    }
  }
  countDownTimer.start()
  lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
      countDownTimer.cancel()
    }
  })
}

fun TextView.enableWhenOtherTextNotEmpty(vararg textViews: TextView) =
  enableWhenOtherTextChanged(*textViews) { textViews.all { it.isTextNotEmpty() } }

inline fun TextView.enableWhenOtherTextChanged(
  vararg textViews: TextView,
  crossinline block: (Array<out TextView>) -> Boolean
) {
  isEnabled = block(textViews)
  textViews.forEach {
    it.doAfterTextChanged {
      isEnabled = block(textViews)
    }
  }
}
