package github.vakk.testtask.ui.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.arellomobile.mvp.presenter.InjectPresenter
import github.vakk.testtask.R
import github.vakk.testtask.model.manager.search.dto.SearchResultItem
import github.vakk.testtask.ui.common.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search.*

class MainActivity : BaseActivity(), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    private lateinit var resultsAdapter: ResultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnSearch.setOnClickListener({
            presenter.search(etSearchIn.text.toString(), etSearchFor.text.toString(), 5, 10)
            btnSearch.isEnabled = false
        })
        initResultsList()
    }

    private fun initResultsList() {
        resultsAdapter = ResultsAdapter()
        rvSearchResults.adapter = resultsAdapter
        rvSearchResults.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
    }

    override fun searchStarted() {
        btnSearch.isEnabled = false
    }

    override fun searchFinished() {
        btnSearch.isEnabled = true
    }

    override fun newResultAppeared(result: SearchResultItem) {
        resultsAdapter.addItem(result)
    }

}