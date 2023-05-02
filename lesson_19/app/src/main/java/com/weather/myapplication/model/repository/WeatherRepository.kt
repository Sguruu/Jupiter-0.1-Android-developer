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
import okhttp3.Call
import retrofit2.Callback
import retrofit2.Response

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

    fun requestWeather(
        lat: String,
        lon: String,
        resultCallback: (Result<Weather>) -> Unit
    ): retrofit2.Call<ResponseWeather> {
        return Network.weatherApi.getWeather(lat, lon).apply {
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
                            resultCallback.invoke(Result.Success(it))
                        }
                            ?: resultCallback.invoke(Result.Error(RuntimeException("Ошибка парсинга")))
                    } else {
                        resultCallback.invoke(Result.Error(RuntimeException("Некорректный статус кода")))
                    }
                }

                // если ошибка
                override fun onFailure(call: retrofit2.Call<ResponseWeather>, t: Throwable) {
                    resultCallback.invoke(Result.Error(t))
                }
            })
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
