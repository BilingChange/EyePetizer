package com.bili.base.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.*
import androidx.core.content.res.ResourcesCompat


@ColorInt
fun View.getCompatColor(@ColorRes id: Int): Int =
  context.getCompatColor(id)

@ColorInt
fun Context.getCompatColor(@ColorRes id: Int): Int =
  ResourcesCompat.getColor(resources, id, null)

fun View.getCompatDrawable(@DrawableRes id: Int): Drawable? =
  context.getCompatDrawable(id)

fun Context.getCompatDrawable(@DrawableRes id: Int): Drawable? =
  ResourcesCompat.getDrawable(resources, id, null)

fun View.getCompatDimen(@DimenRes id: Int): Float =
  context.getCompatDimen(id)

fun Context.getCompatDimen(@DimenRes id: Int): Float =
  ResourcesCompat.getFloat(resources, id)

fun View.getCompatFont(@FontRes id: Int): Typeface? =
  context.getCompatFont(id)

fun Context.getCompatFont(@FontRes id: Int): Typeface? =
  ResourcesCompat.getFont(this, id)

fun View.getString(@StringRes id: Int): String =
  context.getString(id)

@ColorInt
fun parseColor(colorString: String): Int =
  Color.parseColor(colorString)
