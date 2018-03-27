package github.vakk.testtask.model.services

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface RestService {
    @GET
    fun getBody(@Url url: String): Call<ResponseBody>
}