package hu.bme.aut.android.reportapp.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class BasicAuthClient<T>(username: String, password: String) {
    private val client =  OkHttpClient.Builder()
        .addInterceptor(BasicAuthInterceptor(username, password))
        .build()

    val gson = GsonBuilder()
        .setLenient()
        .create();

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.171:8080/api/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun create(service: Class<T>): T {
        return retrofit.create(service)
    }
}