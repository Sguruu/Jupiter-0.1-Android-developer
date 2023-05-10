package com.weather.myapplication.repository

import com.squareup.moshi.Moshi
import com.weather.myapplication.R
import com.weather.myapplication.base.App
import com.weather.myapplication.base.Result
import com.weather.myapplication.base.db.DataBase
import com.weather.myapplication.base.network.Network
import com.weather.myapplication.base.network.model.RequestWeather
import com.weather.myapplication.base.network.model.ResponseWeather
import com.weather.myapplication.model.City
import com.weather.myapplication.model.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository {

    private val db = DataBase.instance

    suspend fun getAllCity(): List<City> {
        return db.CityDao().getAllCity().map {
            City(
                it.name,
                it.pathImage ?: "",
                it.lat.toString(),
                it.lon.toString()
            )
        }
    }

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
        // укажем в каком потоке у нас будет происходить запрос (это не обязательно в случае работы с
        // retrofit, так как он под капотом все делает за нас
        return withContext(Dispatchers.IO) {
            try {
                val weather = Network.weatherApi.getWeather(lat, lon).let {
                    Weather(
                        it.main.tempMin,
                        it.main.tempMax,
                        it.weatherCurrent[0].description
                    )
                }
                Result.Success(weather)
            } catch (e: Throwable) {
                Result.Error(e)
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
