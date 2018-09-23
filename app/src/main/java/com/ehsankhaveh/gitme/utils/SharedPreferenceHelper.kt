package com.ehsankhaveh.gitme.utils

import android.content.Context

class SharedPreferenceHelper (context: Context) {

    val preference = context.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getToken(): String {
        return preference.getString(Constants.PREFERENCE_TOKEN, "")
    }

    fun setToken(token: String) {
        val editor = preference.edit()
        editor.putString(Constants.PREFERENCE_TOKEN, token)
        editor.apply()

    }
}