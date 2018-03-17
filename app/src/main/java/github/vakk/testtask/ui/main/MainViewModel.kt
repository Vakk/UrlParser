package github.vakk.testtask.ui.main

import github.vakk.testtask.ui.common.BaseViewModel
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by Valery Kotsulym on 3/16/18.
 */
class MainViewModel : BaseViewModel() {

    fun name(): Observable<String> {
        var counter = 0
        return Observable.interval(1, TimeUnit.SECONDS)
                .map { "test ${++counter}" }
    }

}