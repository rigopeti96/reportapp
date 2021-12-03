package hu.bme.aut.android.reportapp.network

import hu.bme.aut.android.reportapp.data.User
import retrofit2.Call
import retrofit2.http.GET

import retrofit2.http.POST

interface LoginService {
    @POST("user/auth_hello")
    fun basicLogin(): Call<User>?
}