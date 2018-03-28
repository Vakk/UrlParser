package github.vakk.testtask.ui.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.arellomobile.mvp.presenter.InjectPresenter
import github.vakk.testtask.R
import github.vakk.testtask.model.manager.search.dto.SearchResultItem
import github.vakk.testtask.ui.common.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    private lateinit var resultsAdapter: ResultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnSearch.setOnClickListener({
            presenter.search(etSearchIn.text.toString(), etSearchFor.text.toString(),
                    etSearchThreads.text.toString().toIntOrNull() ?: 1,
                    etSearchDeep.text.toString().toIntOrNull() ?: 1)
        })
        initResultsList()
        init()
    }

    private fun init() {
        etSearchIn.setText("https://developer.android.com/studio/index.html")
        etSearchFor.setText("Android")
        etSearchThreads.setText("10")
        etSearchDeep.setText("5")
    }

    private fun initResultsList() {
        resultsAdapter = ResultsAdapter()
        rvSearchResults.adapter = resultsAdapter
        rvSearchResults.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
    }

    override fun searchStarted() {
        resultsAdapter.clearList()
        pbProgress.visibility = View.VISIBLE
        btnSearch.text = resources.getText(R.string.stop)
    }

    override fun searchFinished() {
        pbProgress.visibility = View.GONE
        btnSearch.text = resources.getText(R.string.start)
    }

    override fun changeData(result: List<SearchResultItem>) {
        resultsAdapter.changeDataSet(result)
    }

}