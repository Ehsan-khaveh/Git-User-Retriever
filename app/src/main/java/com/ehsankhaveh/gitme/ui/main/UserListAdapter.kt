package com.ehsankhaveh.gitme.ui.main

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.ehsankhaveh.gitme.R
import com.ehsankhaveh.gitme.models.User
import com.ehsankhaveh.gitme.ui.detail.DetailActivity
import com.ehsankhaveh.gitme.utils.inflate
import com.squareup.picasso.Picasso
import com.ehsankhaveh.gitme.utils.Constants
import kotlinx.android.synthetic.main.user_item.view.*
import javax.inject.Inject

class UserListAdapter @Inject constructor() : RecyclerView.Adapter<UserListAdapter.UserHolder>() {

    private var usersList: ArrayList<User> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val layout = parent.inflate(R.layout.user_item, false)
        return UserHolder(layout)
    }

    override fun getItemCount() = usersList.size

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        var itemUser = usersList[position]
        holder.bindUser(itemUser)
    }

    fun refreshUserList(newList: List<User>) {
        usersList.clear()
        usersList.addAll(newList)
        notifyDataSetChanged()
    }

    class UserHolder(v: View): RecyclerView.ViewHolder(v), View.OnClickListener {

        private var user: User? = null
        private var view: View = v

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val context = itemView.context
            val showPhotoIntent = Intent(context, DetailActivity::class.java)
            showPhotoIntent.putExtra(Constants.LOGIN_KEY, user?.login)
            context.startActivity(showPhotoIntent)
        }

        fun bindUser(user: User) {
            this.user = user
            Picasso.get()
            .load(user.avatar_url)
            .into(view.avatar)
            view.fullName.text = user.login
        }

    }
}