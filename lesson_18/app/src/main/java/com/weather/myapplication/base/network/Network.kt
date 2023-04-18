package com.weather.myapplication.base.network

import android.util.Log
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

private const val API_KEY = "a552b39b529b0402a3b40d2affee9ef4"
private const val LANG = "ru"
private const val UNITS = "metric"

object Network {
    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(CustomHeaderInterceptor("header1", "headerValue1"))
        .addNetworkInterceptor(CustomHeaderInterceptor("header2", "headerValue2"))
        // добавление network Interceptor
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    fun getWeatherCall(lat: String, lon: String): Call {
        // https://api.openweathermap.org/data/2.5/weather?lat={{lat}}&lon={{lon}}&appid={{API_KEY}}&lang=ru&units=metric
        val url = HttpUrl.Builder()
            .scheme("https")
            .host("api.openweathermap.org")
            .addPathSegment("data")
            .addPathSegment("2.5")
            .addPathSegment("weather")
            .addQueryParameter("lat", lat)
            .addQueryParameter("lon", lon)
            .addQueryParameter("appid", API_KEY)
            .addQueryParameter("lang", LANG)
            .addQueryParameter("units", UNITS)
            .build()

        Log.d("MyTest", "Uri $url")

        // специальный класс для создания запроса от okHttp
        val request = Request.Builder()
            // можно указать http метод gep/post/delete/put и тд
            // также можно добавить заголовок с помощью addHeader
            .get()
            // указываем url, но его нужно сначала сконфигурировать
            .url(url)
            .build()

        return client.newCall(request)
    }
}
