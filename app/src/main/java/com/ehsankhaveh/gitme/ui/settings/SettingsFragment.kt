package com.ehsankhaveh.gitme.ui.settings

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ehsankhaveh.gitme.R
import com.ehsankhaveh.gitme.di.component.DaggerFragmentComponent
import com.ehsankhaveh.gitme.di.module.FragmentModule
import kotlinx.android.synthetic.main.fragment_set_token.*
import javax.inject.Inject

class SettingsFragment: AppCompatDialogFragment(), SettingsContract.View {

    @Inject lateinit var presenter: SettingsContract.Presenter

    lateinit var customView: View;

    fun newInstance(): SettingsFragment {
        return SettingsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        presenter.attach(this, context!!)
        return customView
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        customView = activity!!.layoutInflater.inflate(R.layout.fragment_set_token, null)
        return builder.setView(customView).setPositiveButton("Close") { dialog, whichButton ->
            dialog.dismiss()
        }.create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        set_token.setOnClickListener(){
            token_message.visibility = View.GONE
            presenter.onUpdateTokenClick(token_text.text.toString())
        }

    }

    private fun injectDependency() {
        val settingsComponent = DaggerFragmentComponent.builder()
                .fragmentModule(FragmentModule())
                .build()

        settingsComponent.inject(this)
    }

    override fun tokenUpdated() {
        token_message.text = "New token saved"
        token_message.visibility = View.VISIBLE
    }


}