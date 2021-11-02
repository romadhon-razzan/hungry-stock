package id.co.ptn.hungrystock.core

import android.content.Context
import androidx.core.content.edit

private const val USER_PREFERENCES_NAME = "user_preferences"

private const val KEY_TOKEN = "token"

class SessionManager private constructor(context: Context) {
    private val sharedPreferences =
        context.applicationContext.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)


    val token: String
        get() {
            val order = sharedPreferences.getString(KEY_TOKEN, "")
            return order ?: ""
        }

    fun setToken(token: String) {
        sharedPreferences.edit { putString(KEY_TOKEN, token) }
    }

    companion object {
        @Volatile
        private var INSTANCE: SessionManager? = null

        fun getInstance(context: Context): SessionManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }

                val instance = SessionManager(context)
                INSTANCE = instance
                instance
            }
        }
    }
}