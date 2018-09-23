package com.ehsankhaveh.gitme.di.component

import com.ehsankhaveh.gitme.BaseApp
import com.ehsankhaveh.gitme.di.module.ApplicationModule
import dagger.Component

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(application: BaseApp)
}