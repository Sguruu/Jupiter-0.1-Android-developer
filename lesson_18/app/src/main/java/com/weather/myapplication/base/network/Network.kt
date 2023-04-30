package com.weather.myapplication.base.network

import android.util.Log
import com.weather.myapplication.base.retrofit.WeatherApi
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object Network {

    const val API_KEY = "a552b39b529b0402a3b40d2affee9ef4"
    const val LANG = "ru"
    const val UNITS = "metric"

    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(CustomHeaderInterceptor("header1", "headerValue1"))
        .addNetworkInterceptor(CustomHeaderInterceptor("header2", "headerValue2"))
        // добавление network Interceptor
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    // создаем retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        // позволяет нам работать с конвертором от Moshi
        .addConverterFactory(MoshiConverterFactory.create())
        // указываем наш клиент, если килент не будем указывать он создасть пустой client без NetworkInterceptor
        .client(client)
        .build()

    val weatherApi: WeatherApi
        // создает  наш интерфейс путем создания экземпляра ретрофит
        get() = retrofit.create()

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
