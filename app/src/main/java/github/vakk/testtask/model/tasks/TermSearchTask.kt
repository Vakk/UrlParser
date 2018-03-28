package github.vakk.testtask.model.tasks

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor

/**
 * Created by Valery Kotsulym on 3/28/18.
 */
class TermSearchTask(val executor: Executor) {

    fun execute(source: String, query: String): Observable<Int> {
        return Observable.just(0)
                .observeOn(Schedulers.from(executor))
                .map {
                    val regex = Regex(query)
                    val pattern = regex.toPattern()
                    val matcher = pattern.matcher(source)
                    var count = 0
                    while (matcher.find()) {
                        ++count
                    }
                    count
                }

    }

}