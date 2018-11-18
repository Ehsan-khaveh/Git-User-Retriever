package com.ehsankhaveh.gitme.ui.detail

import com.ehsankhaveh.gitme.api.ApiServiceInterface
import com.ehsankhaveh.gitme.di.presenter.DaggerPresenterComponent
import com.ehsankhaveh.gitme.di.presenter.PresenterModule
import com.ehsankhaveh.gitme.models.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailPresenter: DetailContract.Presenter {

    @Inject lateinit var api: ApiServiceInterface
    @Inject lateinit var subscriptions: CompositeDisposable
    private lateinit var view: DetailContract.View

    init {
        injectDependencies()
    }

    override fun loadUser(username: String) {

        view.showProgress(true)

        api.getUser(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ user: User ->
                    view.showUser(user)
                },{ error ->
                    view.showError(error.message!!)
                }, {
                    view.showProgress(false)
                })
    }

    override fun subscribe() {}

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: DetailContract.View) {
        this.view = view
    }

    private fun injectDependencies() {
        var component = DaggerPresenterComponent.builder()
                .presenterModule(PresenterModule())
                .build()

        component.inject(this)
    }
}