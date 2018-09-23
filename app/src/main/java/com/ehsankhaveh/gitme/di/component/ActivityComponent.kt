package com.ehsankhaveh.gitme.di.component

import com.ehsankhaveh.gitme.di.module.ActivityModule
import com.ehsankhaveh.gitme.ui.main.MainActivity
import dagger.Component

@Component(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
}