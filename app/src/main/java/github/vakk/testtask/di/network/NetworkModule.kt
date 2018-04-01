package github.vakk.testtask.di.network

import dagger.Module
import dagger.Provides
import github.vakk.testtask.model.services.RestService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 * Created by Valery Kotsulym on 3/22/18.
 */
@Module
class NetworkModule {

    @Provides
    @Network
    fun retrofitBuilder(): Retrofit.Builder = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    @Provides
    @Network
    fun retrofit(builder: Retrofit.Builder, okHttpClient: OkHttpClient): Retrofit = builder
            .client(okHttpClient)
            .baseUrl("http://google.com").build()

    @Provides
    @Network
    fun okHttpBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }

    @Provides
    @Network
    fun okHttp(builder: OkHttpClient.Builder): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        return builder
                .addInterceptor(loggingInterceptor)
                .build()
    }

    @Provides
    @Network
    fun service(retrofit: Retrofit): RestService = retrofit.create(RestService::class.java)

}