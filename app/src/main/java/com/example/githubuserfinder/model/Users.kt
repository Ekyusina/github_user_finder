package com.example.githubuserfinder.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Users {

    @SerializedName("items")
    @Expose
    var items: ArrayList<Items?>? = null

    @SerializedName("total_count")
    @Expose
    var totalCount = 0
}