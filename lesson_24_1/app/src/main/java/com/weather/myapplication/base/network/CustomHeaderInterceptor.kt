package com.weather.myapplication.base.network

import okhttp3.Interceptor
import okhttp3.Response

class CustomHeaderInterceptor(
    private val headerName: String,
    private val headerValue: String
) : Interceptor {
    // chain это запрос из цепочки Interceptor
    override fun intercept(chain: Interceptor.Chain): Response {
        // давайте модифицируем запрос чтобы добавить в него заголовок
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest
            // позволяет модифицировать текущий запрос
            .newBuilder()
            .addHeader(headerName, headerValue)
            .build()

        // дальше нам наш запрос нужно отправить дальше по цепочке
        // данный метод выполнит запрос (пройде дальше по цепочке Interceptor, и вернет нам ответ
        val response = chain.proceed(modifiedRequest)
        return response
    }
}
