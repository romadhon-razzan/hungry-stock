package id.co.ptn.hungrystock.core

import android.content.Context
import java.util.prefs.Preferences

private const val USER_PREFERENCES_NAME = "user_preferences"

class SessionManager private constructor(context: Context) {
    private val sharedPreferences =
        context.applicationContext.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
    companion object {

    }
}