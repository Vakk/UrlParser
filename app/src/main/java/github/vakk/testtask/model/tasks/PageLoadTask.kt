package github.vakk.testtask.model.tasks

import github.vakk.testtask.model.services.RestService
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor

/**
 * Created by Valery Kotsulym on 3/28/18.
 */
class PageLoadTask(val executor: Executor) {

    fun execute(url: String, restService: RestService): Observable<String> {
        return Observable.just(restService)
                .observeOn(Schedulers.from(executor))
                .switchMap { restService.getBody(url).map { body -> body.string() } }
    }

}