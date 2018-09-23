package com.ehsankhaveh.gitme

import android.app.Application
import com.ehsankhaveh.gitme.di.component.ApplicationComponent
import com.ehsankhaveh.gitme.di.component.DaggerApplicationComponent
import com.ehsankhaveh.gitme.di.module.ApplicationModule

class BaseApp: Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        instance = this
        setup()
    }

    fun setup() {
        component = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this)).build()
        component.inject(this)
    }

    fun getApplicationComponent(): ApplicationComponent {
        return component
    }

    companion object {
        lateinit var instance: BaseApp private set
    }
}