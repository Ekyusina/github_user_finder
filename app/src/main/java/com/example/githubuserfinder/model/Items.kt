package com.example.githubuserfinder.model

import com.google.gson.annotations.SerializedName

class Items {
    @SerializedName("login")
    var name: String? = null

    @SerializedName("avatar_url")
    var avatar: String? = null

    @SerializedName("url")
    var url: String? = null
}