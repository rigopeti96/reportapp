package hu.bme.aut.android.reportapp.network

import hu.bme.aut.android.reportapp.data.Report
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.text.TextUtils
import android.util.Log
import hu.bme.aut.android.reportapp.data.HelloResponse
import okhttp3.Credentials

object NetworkManager {
    private var retrofit: Retrofit
    private val reportApi: ReportApi

    private const val SERVICE_URL = "http://192.168.0.171:8080/api/"

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        reportApi = retrofit.create(ReportApi::class.java)
    }

    fun getAllReports(): Call<ArrayList<Report>>{
        return reportApi.getAllReports()
    }

    fun postNewReport(report: Report): Call<Report>{
        return reportApi.postNewReport(report)
    }

    fun getHello(): Call<HelloResponse>{
        return reportApi.getHello()
    }

}


