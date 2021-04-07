package com.example.githubuserfinder.ui

import com.example.githubuserfinder.model.Items

interface SearchView {
    interface InitView {
        fun showLoading()
        fun hideLoading()
        fun userList(users: ArrayList<Items?>?)
        fun userListFailure(errorMessage: String?, keyword: String?)
    }

    interface GetUsers {
        fun getUserList(keyword: String?)
    }
}