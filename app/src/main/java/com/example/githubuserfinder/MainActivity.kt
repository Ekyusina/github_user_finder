package com.example.githubuserfinder

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AbsListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserfinder.model.Items
import com.example.githubuserfinder.ui.SearchAdapter
import com.example.githubuserfinder.ui.SearchPresenter
import com.example.githubuserfinder.ui.SearchView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SearchView.InitView{

    var adapter: SearchAdapter? = null
    var isScrolling: Boolean = false
    var users: ArrayList<Items?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val presenter = SearchPresenter(this)
        adapter = SearchAdapter()

        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.getUserList(query)
                return false;
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false;
            }
        })

        val manager = LinearLayoutManager(this)
        recyclerView.layoutManager = manager
        val dividerItemDecoration = androidx.recyclerview.widget.DividerItemDecoration(this, (manager).orientation)
        recyclerView?.addItemDecoration(dividerItemDecoration)
        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                var currentItems = manager.childCount
                var totalItems = manager.itemCount
                var scrollOutItems = manager.findFirstVisibleItemPosition()

                if (isScrolling && (currentItems + scrollOutItems == totalItems)){
                    isScrolling = false
                    fetchData()
                }
            }
        })
    }

    override fun showLoading() {
        progressBar?.visibility = View.VISIBLE
        recyclerView?.visibility = View.GONE
        errorLayout?.visibility = View.GONE
    }

    override fun hideLoading() {
        progressBar?.visibility = View.GONE
        recyclerView?.visibility = View.VISIBLE
    }

    override fun userList(users: ArrayList<Items?>?) {
        this.users = users
        adapter?.items = users
        recyclerView?.setAdapter(adapter)
        adapter?.notifyDataSetChanged()
    }

    override fun userListFailure(errorMessage: String?, keyword: String?) {
        errorLayout?.visibility = View.VISIBLE
        errorTitle?.text = errorMessage
        textViewError?.text = keyword
    }

    fun fetchData() {
        progressBar.visibility = View.VISIBLE

        Handler().postDelayed(object : Runnable{
            override fun run() {
                adapter?.addItems(users)
                progressBar.visibility = View.GONE
            }
        }, 1000)
    }
}