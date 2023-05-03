package com.weather.myapplication.model.repository

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.weather.myapplication.R
import com.weather.myapplication.base.App
import com.weather.myapplication.base.Result
import com.weather.myapplication.base.network.Network
import com.weather.myapplication.model.model.City
import com.weather.myapplication.model.model.RequestWeather
import com.weather.myapplication.model.model.ResponseWeather
import com.weather.myapplication.model.model.Weather
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class WeatherRepository {
    fun createListCity(): List<City> {
        val moscowLink =
            "https://cdn2.tu-tu.ru/image/pagetree_node_data/1/055c4b01273eb986fead1a6db4c20e9f/"
        val samaraLink =
            "https://247510.selcdn.ru/mybiz_production/posts/images/posters/cadb20593bcd701d4c42f40132f416928fe30f12.jpg"
        val sakhalinLink = "https://top10.travel/wp-content/uploads/2017/10/mys-mayak-aniva.jpg"

        return listOf(
            City(
                name = App.res.getString(R.string.moscow),
                imageLink = moscowLink,
                lat = "37.6024",
                lon = "55.7367"
            ),
            City(
                name = App.res.getString(R.string.samara),
                imageLink = samaraLink,
                lat = "53.186457",
                lon = "50.119760"
            ),
            City(
                name = App.res.getString(R.string.sakhalin),
                imageLink = sakhalinLink,
                lat = "46.959746",
                lon = "142.731299"
            )
        )
    }

    suspend fun requestWeather(
        lat: String,
        lon: String
    ): Result<Weather> {
        return suspendCoroutine { continuation ->
            Network.weatherApi.getWeather(lat, lon).apply {
                enqueue(object : Callback<ResponseWeather> {
                    // если успешно
                    override fun onResponse(
                        call: retrofit2.Call<ResponseWeather>,
                        response: Response<ResponseWeather>
                    ) {
                        if (response.isSuccessful) {
                            val weather = response.body()?.let {
                                Weather(
                                    tempMin = it.main.tempMin,
                                    tempMax = it.main.tempMax,
                                    descriptionWeather = it.weatherCurrent[0].description
                                )
                            }

                            weather?.let {
                                // блок кода который мы должны вернуть, важно если не вернем continuation
                                // корутина никогда не остановится и приложение зависнит
                                continuation.resume(Result.Success(it))
                            }
                                ?: continuation.resume(Result.Error(RuntimeException("Ошибка парсинга")))
                        } else {
                            continuation.resumeWithException(RuntimeException("Ошибка ответа"))
                        }
                    }

                    // если ошибка
                    override fun onFailure(call: retrofit2.Call<ResponseWeather>, t: Throwable) {
                        continuation.resumeWithException(t)
                    }
                })
            }
        }
    }

    fun convertWeatherToJson(value: Weather): String {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(RequestWeather::class.java).nonNull()

        try {
            val jsonWeather = adapter.toJson(
                RequestWeather(
                    value.tempMin,
                    value.tempMax,
                    value.descriptionWeather
                )
            )
            return jsonWeather
        } catch (e: Exception) {
            return e.message.toString()
        }
    }

    private fun parseMovieResponse(responseBodyString: String): ResponseWeather? {
        val moshi = Moshi.Builder().build()

        // далее нам нужно создать адаптер для связи данных из JSON с нашим классом
        // .nonNull Возвращает адаптер JSON, эквивалентный этому адаптеру JSON, но не допускающий
        // пустых значений. Если значение null прочитано или записано, это вызовет исключение JsonDataException.
        val adapter = moshi.adapter(ResponseWeather::class.java).nonNull()

        try {
            // может выкинуть ошибку при некорректном JSON или не удалось распарсить JSON
            val responseWeather: ResponseWeather? = adapter.fromJson(responseBodyString)
            return responseWeather
        } catch (e: java.lang.Exception) {
            return null
        }
    }
}
