package com.bili.base.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import java.util.*
import kotlin.reflect.KClass


internal val activityCache = LinkedList<Activity>()

fun startActivity(intent: Intent) = topActivity.startActivity(intent)

inline fun <reified T : Activity> startActivity(
  vararg pairs: Pair<String, Any?>,
  crossinline block: Intent.() -> Unit = {}
) = topActivity.startActivity<T>(pairs = pairs, block = block)

inline fun <reified T : Activity> Context.startActivity(
  vararg pairs: Pair<String, Any?>,
  crossinline block: Intent.() -> Unit = {}
) = startActivity(intentOf<T>(*pairs).apply(block))

fun Activity.finishWithResult(vararg pairs: Pair<String, *>) {
  setResult(Activity.RESULT_OK, Intent().apply { putExtras(bundleOf(*pairs)) })
  finish()
}

val activityList: List<Activity> get() = activityCache.toList()

val topActivity: Activity get() = activityCache.last()

inline fun <reified T : Activity> isActivityExistsInStack(): Boolean =
  isActivityExistsInStack(T::class)

fun <T : Activity> isActivityExistsInStack(clazz: KClass<T>): Boolean =
  activityCache.any { it.javaClass == clazz }

inline fun <reified T : Activity> finishActivity(): Boolean = finishActivity(T::class)

fun <T : Activity> finishActivity(clazz: KClass<T>): Boolean =
  activityCache.removeAll {
    if (it.javaClass == clazz) it.finish()
    it.javaClass == clazz
  }

fun finishAllActivities(): Boolean =
  activityCache.removeAll {
    it.finish()
    true
  }

fun finishAllActivitiesExceptNewest(): Boolean =
  topActivity.let { topActivity ->
    activityCache.removeAll {
      if (it != topActivity) it.finish()
      it != topActivity
    }
  }

fun ComponentActivity.pressBackTwiceToExitApp(toastText: String, delayMillis: Long = 2000) =
  pressBackTwiceToExitApp(delayMillis) { toast(toastText) }

fun ComponentActivity.pressBackTwiceToExitApp(@StringRes toastText: Int, delayMillis: Long = 2000) =
  pressBackTwiceToExitApp(delayMillis) { toast(toastText) }

inline fun ComponentActivity.pressBackTwiceToExitApp(delayMillis: Long = 2000, crossinline onFirstBackPressed: () -> Unit) {
  onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
    private var lastBackTime: Long = 0

    override fun handleOnBackPressed() {
      val currentTime = System.currentTimeMillis()
      if (currentTime - lastBackTime > delayMillis) {
        onFirstBackPressed()
        lastBackTime = currentTime
      } else {
        finishAllActivities()
      }
    }
  })
}

fun ComponentActivity.pressBackToNotExitApp() {
  onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
    override fun handleOnBackPressed() {
      moveTaskToBack(false)
    }
  })
}

fun Context.checkPermission(permission: String): Boolean =
  ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED

inline val Activity.windowInsetsControllerCompat: WindowInsetsControllerCompat?
  get() = WindowCompat.getInsetsController(window, window.decorView)

var Activity.decorFitsSystemWindows: Boolean
  @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
  get() = noGetter()
  set(value) = WindowCompat.setDecorFitsSystemWindows(window, value)

inline val Activity.contentView: View
  get() = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)

val Context.activity: Activity?
  get() {
    var context: Context? = this
    while (context is ContextWrapper) {
      if (context is Activity) {
        return context
      }
      context = context.baseContext
    }
    return null
  }

inline val Context.context: Context get() = this

inline val Activity.activity: Activity get() = this

inline val FragmentActivity.fragmentActivity: FragmentActivity get() = this

inline val ComponentActivity.lifecycleOwner: LifecycleOwner get() = this
