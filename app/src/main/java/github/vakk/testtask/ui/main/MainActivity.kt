package github.vakk.testtask.ui.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.arellomobile.mvp.presenter.InjectPresenter
import github.vakk.testtask.R
import github.vakk.testtask.model.manager.search.SearchResult
import github.vakk.testtask.ui.common.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search.*

class MainActivity : BaseActivity(), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAdapter()
        btnSearch.setOnClickListener({ presenter.search(etSearchIn.text.toString(), etSearchFor.text.toString(), 0) })
    }

    private fun initAdapter() {
        rvSearchResults.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
    }

    override fun searchStarted() {
        btnSearch.isEnabled = false
    }

    override fun searchFinished() {
        btnSearch.isEnabled = true
    }

    override fun newResultAppeared(result: SearchResult) {

    }

}