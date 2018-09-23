package com.ehsankhaveh.gitme.ui.settings

import android.content.Context
import com.ehsankhaveh.gitme.utils.SharedPreferenceHelper


class SettingsPresenter: SettingsContract.Presenter {

    private lateinit var view: SettingsContract.View
    private lateinit var context: Context


    override fun attach(view: SettingsContract.View, context: Context) {
        this.view = view
        this.context = context
    }

    override fun subscribe() {
    }

    override fun unsubscribe() {

    }

    override fun onUpdateTokenClick(token: String) {
        val sharedPrefHelper = SharedPreferenceHelper(context)
        sharedPrefHelper.setToken(token)
        view.tokenUpdated()
    }


}