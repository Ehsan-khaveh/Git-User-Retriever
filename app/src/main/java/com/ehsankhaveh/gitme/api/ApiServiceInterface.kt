package com.ehsankhaveh.gitme.api

import com.ehsankhaveh.gitme.models.User
import com.ehsankhaveh.gitme.models.UserList
import com.ehsankhaveh.gitme.utils.Constants
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.Path


interface ApiServiceInterface {

    @GET("search/users?sort=repositories")
    fun searchUsers(@Query("q") username: String): Observable<UserList>

    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Observable<User>

    companion object Factory {
        fun create(): ApiServiceInterface {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = retrofit2.Retrofit.Builder()
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(Constants.BASE_URL)
                        .client(client)
                        .build()

            return retrofit.create(ApiServiceInterface::class.java)

        }
    }
}