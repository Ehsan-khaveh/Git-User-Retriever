package com.ehsankhaveh.gitme.models

import com.google.gson.annotations.SerializedName

data class UserList (
        @SerializedName("items") val items: ArrayList<User>,
        @SerializedName("total_count") val total: Int,
        @SerializedName("incomplete_results") val incompleteResults: Boolean
)