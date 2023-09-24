package id.co.ptn.hungrystock.helper.extension

import android.util.Patterns

fun String.isValidUrl(): Boolean = Patterns.WEB_URL.matcher(this).matches() && (this.contains("http://") || this.contains("https://"))