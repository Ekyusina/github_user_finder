package com.example.githubuserfinder.apiservice

import com.example.githubuserfinder.model.Users
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("search/users")
    fun getUsers(@Query("q") keyword: String?): Call<Users?>?
}