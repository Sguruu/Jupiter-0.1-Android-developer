package com.weather.myapplication.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.weather.myapplication.model.City

class CustomDuffCallback(private val oldListCity: List<City>, private val newListCity: List<City>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldListCity.size
    }

    override fun getNewListSize(): Int {
        return newListCity.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItemCity = oldListCity[oldItemPosition]
        val newItemCity = newListCity[newItemPosition]
        return oldItemCity.name == newItemCity.name &&
            oldItemCity.lat == newItemCity.lat &&
            oldItemCity.lon == newItemCity.lon
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItemCity = oldListCity[oldItemPosition]
        val newItemCity = newListCity[newItemPosition]
        return oldItemCity == newItemCity
    }
}
