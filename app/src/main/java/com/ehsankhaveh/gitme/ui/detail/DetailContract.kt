package com.ehsankhaveh.gitme.ui.detail

import com.ehsankhaveh.gitme.models.User
import com.ehsankhaveh.gitme.utils.BaseContract

class DetailContract {
    interface View: BaseContract.View {
        fun showUser(user: User)
        fun showError(message: String)
        fun showProgress(show: Boolean)
    }
    interface Presenter: BaseContract.Presenter<View> {
        fun loadUser(username: String)
    }
}