package com.ehsankhaveh.gitme.ui.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ehsankhaveh.gitme.R
import com.ehsankhaveh.gitme.di.activity.ActivityModule
import com.ehsankhaveh.gitme.di.activity.DaggerActivityComponent
import com.ehsankhaveh.gitme.models.User
import com.ehsankhaveh.gitme.utils.Constants
import com.ehsankhaveh.gitme.utils.checkInternet
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class DetailActivity : AppCompatActivity(), DetailContract.View {

    @Inject
    lateinit var presenter: DetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        injectDependency()

        presenter.attach(this)

        if(intent.hasExtra(Constants.LOGIN_KEY)) {
            var username = intent.getStringExtra(Constants.LOGIN_KEY)

            if(checkInternet())
                presenter.loadUser(username)
            else
                showError(getString(R.string.error_no_internet))
        }
    }

    override fun showUser(user: User) {
        detail_name.text = user.name
        detail_login.text = user.login
        detail_repo_value.text = user.public_repos.toString()
        detail_followers_value.text = user.followers
        detail_following_value.text = user.following
        Picasso.get()
                .load(user.avatar_url)
                .error(R.drawable.icon_robot)
                .into(detail_avatar)
    }

    override fun showError(message: String) {
        tv_detail_error.text = message
        tv_detail_error.visibility = View.VISIBLE
    }
    // function to show/hide the progress bar
    override fun showProgress(show: Boolean) {
        if (show) {
            progress_bar.visibility = View.VISIBLE
            detail_content.visibility = View.INVISIBLE
        } else {
            progress_bar.visibility = View.INVISIBLE
            detail_content.visibility = View.VISIBLE
        }
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .build()

        activityComponent.inject(this)
    }

    override fun onDestroy() {
        presenter.unsubscribe()
        super.onDestroy()
    }

}
