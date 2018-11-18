package com.ehsankhaveh.gitme.ui.main

import com.ehsankhaveh.gitme.api.ApiServiceInterface
import com.ehsankhaveh.gitme.di.presenter.DaggerPresenterComponent
import com.ehsankhaveh.gitme.di.presenter.PresenterModule
import com.ehsankhaveh.gitme.models.UserList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter: MainContract.Presenter {

    @Inject lateinit var api: ApiServiceInterface
    @Inject lateinit var subscriptions: CompositeDisposable
    private lateinit var view: MainContract.View

    init {
        injectDependencies()
    }

    override fun subscribe() {}

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: MainContract.View) {
        this.view = view
    }

    override fun onRetrieveUserClick(username: String) {
        view.showProgress(true)

        var subscribe = api.searchUsers(username)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ userList: UserList ->
                                view.showResults(userList.items)
                            },{ error ->
                                view.showError(error.message!!)
                            }, {
                                view.showProgress(false)
                            })

        subscriptions.add(subscribe)
    }

    private fun injectDependencies() {
        var component = DaggerPresenterComponent.builder()
                .presenterModule(PresenterModule())
                .build()

        component.inject(this)
    }

}