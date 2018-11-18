package com.ehsankhaveh.gitme.di.presenter

import com.ehsankhaveh.gitme.api.ApiServiceInterface
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class PresenterModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiServiceInterface {
        return ApiServiceInterface.create()
    }

    @Provides
    fun provideSubscriptions(): CompositeDisposable {
        return CompositeDisposable()
    }
}