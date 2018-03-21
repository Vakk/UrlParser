package github.vakk.testtask.ui.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.arellomobile.mvp.presenter.InjectPresenter
import github.vakk.testtask.R
import github.vakk.testtask.ui.adapter.SearchResultAdapter
import github.vakk.testtask.ui.common.BaseActivity
import kotlinx.android.synthetic.main.fragment_search.*

class MainActivity : BaseActivity(), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    private var searchResultAdapter: SearchResultAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAdapter()
    }

    private fun initAdapter() {
        searchResultAdapter = SearchResultAdapter()
        rvSearchResults.adapter = searchResultAdapter
        rvSearchResults.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
    }

    override fun searchStarted() {

    }

}