package hu.bme.aut.android.reportapp.network

import hu.bme.aut.android.reportapp.data.HelloResponse
import hu.bme.aut.android.reportapp.data.Report
import hu.bme.aut.android.reportapp.data.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ReportApi {
    @GET("user/auth_hello")
    fun loginAsUser(): Call<User>

    @GET("user/admin_hello")
    fun loginAsAdmin(): Call<User>

    @GET("user/hello")
    fun getHello(): Call<HelloResponse>

    @GET("reports/getAllReports")
    fun getAllReports(): Call<ArrayList<Report>>

    @Headers("Content-type: application/json")
    @POST("reports/postReport")
    fun postNewReport(@Body report: Report): Call<Report>
}