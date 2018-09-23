package com.ehsankhaveh.gitme.di.component

import com.ehsankhaveh.gitme.di.module.FragmentModule
import com.ehsankhaveh.gitme.ui.settings.SettingsFragment
import dagger.Component

@Component(modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(settingsFragment: SettingsFragment)
}