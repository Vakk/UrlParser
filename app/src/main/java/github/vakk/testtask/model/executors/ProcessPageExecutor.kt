package github.vakk.testtask.model.executors

import android.util.Log
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors


/**
 * Created by Valery Kotsulym on 3/28/18.
 */
class ProcessPageExecutor : Executor {

    private val tasks = ArrayDeque<Runnable>()
    private var active: Runnable? = null
    private val executor = Executors.newSingleThreadExecutor()

    override fun execute(command: Runnable) {
        tasks.offer(Runnable {
            try {
                Log.d("executor ${javaClass.name}", "${Thread.currentThread().id}")
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