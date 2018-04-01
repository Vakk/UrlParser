package github.vakk.testtask.model.tasks

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor

/**
 * Created by Valery Kotsulym on 3/28/18.
 */


class UrlSearchTask(val executor: Executor) {
    private val URL_PATTERN = "http[s]*[^\"<>\\['@]+"

    fun execute(source: String): Observable<String> {
        return Observable.just(source)
                .observeOn(Schedulers.from(executor))
                .flatMap {
                    val regex = Regex(URL_PATTERN)
                    val pattern = regex.toPattern()
                    val matcher = pattern.matcher(source)
                    val set: MutableSet<String> = HashSet()
                    while (matcher.find()) {
                        val url = matcher.group()
                        set.add(url)
                    }
                    return@flatMap Observable.fromIterable(set)
                }

    }

}