package com.ehsankhaveh.gitme.di.module

import com.ehsankhaveh.gitme.ui.settings.SettingsContract
import com.ehsankhaveh.gitme.ui.settings.SettingsPresenter
import dagger.Module
import dagger.Provides

@Module
class FragmentModule {

    @Provides
    fun provideSettingsPresenter(): SettingsContract.Presenter {
        return SettingsPresenter()
    }

}