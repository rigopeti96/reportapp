package hu.bme.aut.android.reportapp.network

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class BasicAuthInterceptor(username: String, password: String): Interceptor {
    private var credentials: String = Credentials.basic(username, password)

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization", credentials).build()

        return chain.proceed(request)
    }
}