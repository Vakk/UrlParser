package github.vakk.testtask.utils.extensions

import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by Valery Kotsulym on 3/21/18.
 */
fun ViewGroup.layoutInflater(): LayoutInflater {
    return LayoutInflater.from(context)
}