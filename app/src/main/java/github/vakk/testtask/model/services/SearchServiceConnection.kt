package github.vakk.testtask.model.services

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log

/**
 * Created by Valery Kotsulym on 4/1/18.
 */
class SearchServiceConnection : ServiceConnection {
    override fun onServiceDisconnected(name: ComponentName?) {
        Log.d(SearchService::class.java.name, "$name disconnected")
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        Log.d(SearchService::class.java.name, "$name connected")
    }

}