package github.vakk.testtask.model.tasks

import android.util.Log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.Executor

/**
 * Created by Valery Kotsulym on 3/28/18.
 */


class UrlSearchTask(val executor: Executor) {
    private val URL_PATTERN = "http[s]*\\S+[^\"\\[\'\\@]\\.html"

    fun execute(source: String): Observable<String> {
        val subject = PublishSubject.create<String>()

        Observable.just("")
                .observeOn(Schedulers.from(executor))
                .doOnNext {
                    val regex = Regex(URL_PATTERN)
                    val pattern = regex.toPattern()
                    val matcher = pattern.matcher(source)
                    while (matcher.find()) {
                        val url = matcher.group()
                        subject.onNext(url)
                    }
                }
                .subscribeWith(subject)
                .take(1)

        return subject
                .distinct()
                .doOnNext {
                    Log.d(javaClass.name, it)
                }
    }

}