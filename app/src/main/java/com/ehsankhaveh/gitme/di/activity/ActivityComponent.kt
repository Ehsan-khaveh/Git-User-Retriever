package com.ehsankhaveh.gitme.di.activity

import com.ehsankhaveh.gitme.ui.detail.DetailActivity
import com.ehsankhaveh.gitme.ui.main.MainActivity
import dagger.Component

@Component(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: DetailActivity)
    fun inject(activity: MainActivity)
}