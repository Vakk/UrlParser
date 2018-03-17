package github.vakk.testtask.model

import io.reactivex.Scheduler

/**
 * Created by Valery Kotsulym on 3/16/18.
 */
class ControlableScheduler :Scheduler() {

    override fun createWorker(): Worker {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}