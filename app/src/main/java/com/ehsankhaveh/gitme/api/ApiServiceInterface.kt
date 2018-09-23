package com.ehsankhaveh.gitme.api

import android.content.Context
import com.ehsankhaveh.gitme.models.User
import com.ehsankhaveh.gitme.utils.Constants
import com.ehsankhaveh.gitme.R
import com.ehsankhaveh.gitme.utils.SharedPreferenceHelper
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import okhttp3.OkHttpClient
import java.io.InputStream
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import java.security.cert.X509Certificate


interface ApiServiceInterface {


    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Observable<User>


    companion object Factory {
        fun create(context: Context): ApiServiceInterface {

            val cf: CertificateFactory = CertificateFactory.getInstance("X.509")
            val caInput: InputStream = context.resources.openRawResource(R.raw.git_cert)
            val ca: X509Certificate = caInput.use {
                cf.generateCertificate(it) as X509Certificate
            }
            System.out.println("ca=" + ca.subjectDN)

            // Create a KeyStore containing our trusted CAs
            val keyStoreType = KeyStore.getDefaultType()
            val keyStore = KeyStore.getInstance(keyStoreType).apply {
                load(null, null)
                setCertificateEntry("ca", ca)
            }


            // Create a TrustManager that trusts the CAs inputStream our KeyStore
            val tmfAlgorithm: String = TrustManagerFactory.getDefaultAlgorithm()
            val tmf: TrustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm).apply {
                init(keyStore)
            }

            // Create an SSLSocketFactory that uses our TrustManager
            // Create an SSLContext that uses our TrustManager
            val sslContext: SSLContext = SSLContext.getInstance("TLS").apply {
                init(null, tmf.trustManagers, null)
            }
            // Get the token saved in the SharedPreferences
            val sharedPrefHelper = SharedPreferenceHelper(context)

           //  creating an OkHttpClient that uses our SSLSocketFactory
            val okHttpClient = OkHttpClient().newBuilder()
                        .sslSocketFactory(sslContext.socketFactory)
                        .addInterceptor {
                            var token = sharedPrefHelper.getToken()
                            it.proceed(it.request().newBuilder()
                                    .addHeader("Authorization", "Bearer $token")
                                    .build())
                        }.build()

            val retrofit = retrofit2.Retrofit.Builder()
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(Constants.BASE_URL)
                        .client(okHttpClient)
                        .build()

            return retrofit.create(ApiServiceInterface::class.java)
        }
    }
}