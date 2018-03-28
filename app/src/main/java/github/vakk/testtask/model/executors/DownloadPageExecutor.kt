package github.vakk.testtask.model.executors

import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors


/**
 * Created by Valery Kotsulym on 3/28/18.
 */
class DownloadPageExecutor(threadsCount: Int) : Executor {

    private val tasks = ArrayDeque<Runnable>()
    private var active: Runnable? = null
    private val executor = Executors.newFixedThreadPool(threadsCount)

    override fun execute(command: Runnable) {
        tasks.offer(Runnable {
            try {
                command.run()
            } finally {
                scheduleNext()
            }
        })
        if (active == null) {
            scheduleNext()
        }
    }

    @Synchronized
    protected fun scheduleNext() {
        active = tasks.poll()
        if (active != null) {
            executor.execute(active)
        }
    }
}