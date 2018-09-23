package com.ehsankhaveh.gitme.ui.main

import android.content.Context
import com.ehsankhaveh.gitme.api.ApiServiceInterface
import com.ehsankhaveh.gitme.models.User
import com.ehsankhaveh.gitme.utils.SharedPreferenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainPresenter: MainContract.Presenter {

    private lateinit var api: ApiServiceInterface
    private val subscriptions = CompositeDisposable()
    private lateinit var view: MainContract.View
    private lateinit var context: Context

    override fun subscribe() {}

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: MainContract.View, context: Context) {
        this.view = view
        this.context = context
        this.api = ApiServiceInterface.create(context)
    }

    override fun onRetrieveUserClick(username: String) {
        view.showProgress(true)

        var subscribe = api.getUser(username)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ user: User? ->
                                view.showProgress(false)
                                view.showUserDetails(user!!)
                            },{ error ->
                                view.showProgress(false)
                                view.showError(error.message!!)

                            })

        subscriptions.add(subscribe)
    }

}