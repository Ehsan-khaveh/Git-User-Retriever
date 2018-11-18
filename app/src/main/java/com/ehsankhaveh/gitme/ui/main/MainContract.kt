package com.ehsankhaveh.gitme.ui.main

import com.ehsankhaveh.gitme.models.User
import com.ehsankhaveh.gitme.utils.BaseContract

class MainContract {
    interface View: BaseContract.View {
        fun showResults(users: ArrayList<User>)
        fun showError(message: String)
        fun showProgress(show: Boolean)
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun onRetrieveUserClick(username: String)
    }

}