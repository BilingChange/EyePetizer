package com.bili.base.utils

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.net.URLConnection


inline val File.mimeType: String? get() = URLConnection.guessContentTypeFromName(name)

inline val fileSeparator: String get() = File.separator

inline fun File.print(crossinline block: PrintWriter.() -> Unit) =
  PrintWriter(BufferedWriter(FileWriter(this))).apply(block).close()
