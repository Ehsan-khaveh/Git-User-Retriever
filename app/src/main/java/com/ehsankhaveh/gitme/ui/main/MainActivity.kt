package com.ehsankhaveh.gitme.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.ehsankhaveh.gitme.R
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.ehsankhaveh.gitme.di.component.DaggerActivityComponent
import com.ehsankhaveh.gitme.di.module.ActivityModule
import com.ehsankhaveh.gitme.models.User
import com.ehsankhaveh.gitme.ui.settings.SettingsFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.user_details.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject
    lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // setting the view ad toolbar for the activity
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // injecting the dependency for this activity
        injectDependency()

        // attaching this activity as view for our injected presenter
        presenter.attach(this, baseContext)

        // enable "Retrieve User" button only when there is a username typed in
        username.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                when (s?.length) {
                    0 -> search_button.isEnabled = false
                    else -> search_button.isEnabled = true
                }
            }
        })

        // attach listener to search_button
        search_button.setOnClickListener {
            error.visibility = View.GONE
            user_info.visibility = View.GONE
            presenter.onRetrieveUserClick(username.text.toString())
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unsubscribe()
    }

    // function to show/hide the progress bar
    override fun showProgress(show: Boolean) {
        if (show) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.GONE
        }
    }

    // function to show settings fragment when user clicks on Settings toolbar item
    override fun showSettingsFragment() {
        val fm = supportFragmentManager;
        val settingsfragment = SettingsFragment().newInstance()
        settingsfragment.show(fm, "fragment_set_token");
    }

    override fun showUserDetails(user: User) {
        user_name.text = user.name
        user_bio.text = user.bio
        user_public_repos.text = user.public_repos.toString()+" public repos"

        //Picasso.get().load(user.avatar_url).into(user_avatar);

        user_info.visibility = View.VISIBLE

    }

    override fun showError(message: String) {
        error.text = message
        error.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_change_token -> {
                showSettingsFragment()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }

        }

    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .build()

        activityComponent.inject(this)
    }
}
