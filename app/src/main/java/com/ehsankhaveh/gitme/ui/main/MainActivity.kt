package com.ehsankhaveh.gitme.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ehsankhaveh.gitme.R
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.ehsankhaveh.gitme.di.activity.ActivityModule
import com.ehsankhaveh.gitme.di.activity.DaggerActivityComponent
import com.ehsankhaveh.gitme.models.User
import com.ehsankhaveh.gitme.utils.checkInternet
import com.ehsankhaveh.gitme.utils.hideKeyboard
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject lateinit var presenter: MainContract.Presenter
    @Inject lateinit var userListAdapter: UserListAdapter
    @Inject lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // setting the view ad toolbar for the activity
        setContentView(R.layout.activity_main)

        // injecting the dependency for this activity
        injectDependency()

        // attaching this activity as view for our injected presenter
        presenter.attach(this)

        userList.layoutManager = linearLayoutManager
        userList.adapter = userListAdapter

    }

    // function to show/hide the progress bar
    override fun showProgress(show: Boolean) {
        if (show) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.INVISIBLE
        }
    }

    override fun showResults(users: ArrayList<User>) {
        userListAdapter.refreshUserList(users)
    }

    override fun showError(message: String) {
        tv_main_error.text = message
        tv_main_error.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_search -> {
                tv_main_error.visibility = View.GONE
                userListAdapter.refreshUserList(arrayListOf())
                hideKeyboard()
                if(checkInternet())
                    presenter.onRetrieveUserClick(username.text.toString())
                else
                    showError(getString(R.string.error_no_internet))
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .build()

        activityComponent.inject(this)
    }

    /*
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        listState = userList.layoutManager?.onSaveInstanceState()
        Log.e("mamaaaaaa", "ba")
        outState.putParcelable(LIST_STATE_KEY, listState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.e("babaaaaaa", "ba")
        listState = savedInstanceState.getParcelable(LIST_STATE_KEY)
    }

    override fun onResume() {
        super.onResume()
        Log.e("doneeee", "ba")
        userList.layoutManager?.onRestoreInstanceState(listState)
    }
    */
    override fun onDestroy() {
        presenter.unsubscribe()
        super.onDestroy()
    }
}
