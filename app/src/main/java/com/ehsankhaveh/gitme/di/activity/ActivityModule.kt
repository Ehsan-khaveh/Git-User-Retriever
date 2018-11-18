package com.ehsankhaveh.gitme.di.activity

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import com.ehsankhaveh.gitme.ui.detail.DetailContract
import com.ehsankhaveh.gitme.ui.detail.DetailPresenter
import com.ehsankhaveh.gitme.ui.main.MainContract
import com.ehsankhaveh.gitme.ui.main.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(act: Activity) {

    private val activity = act

    @Provides
    fun provideMainPresenter(): MainContract.Presenter {
        return MainPresenter()
    }

    @Provides
    fun provideDetailPresenter(): DetailContract.Presenter {
        return DetailPresenter()
    }

    @Provides
    fun provideLayoutManager(): LinearLayoutManager {
        return LinearLayoutManager(activity)
    }
}