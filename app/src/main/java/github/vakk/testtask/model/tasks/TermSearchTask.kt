package github.vakk.testtask.model.tasks

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.Executor

/**
 * Created by Valery Kotsulym on 3/28/18.
 */
class TermSearchTask(val executor: Executor) {

    fun execute(source: String, query: String): Observable<Int> {
        val subject: Subject<Int> = PublishSubject.create<Int>()

        Observable.just(0)
                .observeOn(Schedulers.from(executor))
                .map {
                    val regex = Regex(query)
                    val pattern = regex.toPattern()
                    val matcher = pattern.matcher(source)
                    var count = 0
                    while (matcher.find()) {
                        subject.onNext(count)
                        ++count
                    }
                    count
                }
                .subscribeWith(subject)
                .take(1)


        return subject

    }

}