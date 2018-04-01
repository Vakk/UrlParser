package github.vakk.testtask.ui.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.arellomobile.mvp.presenter.InjectPresenter
import github.vakk.testtask.R
import github.vakk.testtask.model.dto.SearchResultItem
import github.vakk.testtask.model.services.SearchService
import github.vakk.testtask.ui.common.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainView, ServiceConnection {
    private val LOG_TAG = "tag"

    @InjectPresenter
    lateinit var presenter: MainPresenter

    private lateinit var resultsAdapter: ResultsAdapter

    private var bound = false

    override fun onStart() {
        super.onStart()
        initService()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnSearch.setOnClickListener({
            if (presenter.searchStarted()) {
                stopSearch()
            } else {
                startSearch()
            }
        })
        initResultsList()
        init()
    }

    private fun stopSearch() {
        presenter.stop()
    }

    private fun startSearch() {
        presenter.start(etSearchIn.text.toString(), etSearchFor.text.toString(),
                etSearchThreads.text.toString().toIntOrNull() ?: 1,
                etSearchDeep.text.toString().toIntOrNull() ?: 1)
    }

    private fun initService() {
        val intent = Intent(this, SearchService::class.java)
        bound = bindService(intent, this, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (bound) {
            unbindService(this)
        }
    }

    private fun init() {
        etSearchIn.setText("https://stackoverflow.com/questions/39059026/retrofit-404-not-found-web-api")
        etSearchFor.setText("for")
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

    override fun onServiceDisconnected(name: ComponentName?) {
        Log.d(LOG_TAG, "$name disconnected")
        presenter.disconnect()
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        Log.d(LOG_TAG, "$name connected")
        if (service is SearchService.LocalBinder) {
            presenter.connect(service.getService())
        }
    }


}