@file:Suppress("unused")

package com.bili.base.utils

import android.app.Application
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import android.os.Process
import android.provider.Settings
import androidx.core.content.pm.PackageInfoCompat


val application: Application get() = AppInitializer.application

inline val packageName: String get() = application.packageName

inline val activitiesPackageInfo: PackageInfo
  get() = application.packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)

inline val appName: String
  get() = application.applicationInfo.loadLabel(application.packageManager).toString()

inline val appVersionName: String get() = activitiesPackageInfo.versionName

inline val appVersionCode: Long
  get() = PackageInfoCompat.getLongVersionCode(activitiesPackageInfo)

inline val isAppDebug: Boolean
  get() = application.packageManager.getApplicationInfo(packageName, 0).flags and
      ApplicationInfo.FLAG_DEBUGGABLE != 0

inline val isAppDarkMode: Boolean
  get() = (application.resources.configuration.uiMode and UI_MODE_NIGHT_MASK) == UI_MODE_NIGHT_YES

fun launchAppDetailsSettings(): Boolean =
  Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    .apply { data = Uri.fromParts("package", packageName, null) }
    .startForActivity()

fun relaunchApp(killProcess: Boolean = true) =
  application.packageManager.getLaunchIntentForPackage(packageName)?.let {
    it.addFlags(FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_CLEAR_TOP)
    startActivity(it)
    if (killProcess) Process.killProcess(Process.myPid())
  }
