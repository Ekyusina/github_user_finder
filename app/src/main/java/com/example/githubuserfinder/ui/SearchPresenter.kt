package com.example.githubuserfinder.ui

import com.example.githubuserfinder.apiservice.ApiClient
import com.example.githubuserfinder.apiservice.ApiInterface
import com.example.githubuserfinder.model.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchPresenter(initView: SearchView.InitView) : SearchView.GetUsers {
    private val initView: SearchView.InitView

    init {
        this.initView = initView
    }

    override fun getUserList(keyword: String?) {
        initView.showLoading()
        val apiInterface: ApiInterface? = ApiClient().getRetrofitInstance()?.create(ApiInterface::class.java)
        val call: Call<Users?>? = apiInterface?.getUsers(keyword)
        call?.enqueue(object : Callback<Users?> {

            override fun onResponse(call: Call<Users?>, response: Response<Users?>) {
                initView.hideLoading()
                initView.userList(response.body()?.items)
                val totalCount: Int? = response.body()?.totalCount
                if (!response.isSuccessful() || response.body()?.items == null || totalCount == 0)
                {
                    initView.userListFailure(
                        "User '$keyword' tidak ditemukan",
                        "Coba cari dengan username lain"
                    )
                }
            }

            override fun onFailure(call: Call<Users?>, t: Throwable) {
                initView.userListFailure("Error Loading For '$keyword'", t.toString())
                initView.hideLoading()
                t.printStackTrace()
            }
        })
    }
}