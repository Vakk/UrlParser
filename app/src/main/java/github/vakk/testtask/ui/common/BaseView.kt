package github.vakk.testtask.ui.common

import com.arellomobile.mvp.MvpView

/**
 * Created by Valery Kotsulym on 3/18/18.
 */
interface BaseView : MvpView {
    fun onError(throwable: Throwable)
}