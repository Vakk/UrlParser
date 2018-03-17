package github.vakk.testtask.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import github.vakk.testtask.R

class MainActivity : AppCompatActivity() {

    var viewModel: MainViewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
