package com.ehsankhaveh.gitme.di.module

import android.app.Application
import com.ehsankhaveh.gitme.BaseApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val baseApp: BaseApp) {

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return baseApp
    }
}