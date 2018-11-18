package com.ehsankhaveh.gitme.di.presenter

import com.ehsankhaveh.gitme.ui.detail.DetailPresenter
import com.ehsankhaveh.gitme.ui.main.MainPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [PresenterModule::class])
interface PresenterComponent {
    fun inject(mainPresenter: MainPresenter)
    fun inject(detailPresenter: DetailPresenter)
}