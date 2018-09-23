package com.ehsankhaveh.gitme.ui.settings

import com.ehsankhaveh.gitme.utils.BaseContract

class SettingsContract {

    interface View: BaseContract.View {
        fun tokenUpdated()
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun onUpdateTokenClick(token: String)
    }
}