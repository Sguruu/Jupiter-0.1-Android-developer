package com.weather.myapplication.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.weather.myapplication.R
import com.weather.myapplication.databinding.ItemCityBinding
import com.weather.myapplication.model.model.City

class ListWeatherAdapter(
    private val onItemClickListener: (name: String, lat: String, lot: String, imageLink: String) -> Unit
) : RecyclerView.Adapter<ListWeatherAdapter.Holder>() {

    private var cityList: List<City> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ItemCityBinding.inflate(inflate)
        return Holder(binding, onItemClickListener = onItemClickListener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(cityList[position])
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    fun updateCity(newCityList: List<City>) {
        cityList = newCityList
    }

    class Holder(
        private val binding: ItemCityBinding,
        private val onItemClickListener: (name: String, lat: String, lot: String, imageLink: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(city: City) {
            binding.nameCityTextView.text = city.name

            Glide.with(itemView)
                .load(city.imageLink)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.cityImageView)

            itemView.setOnClickListener {
                onItemClickListener(city.name, city.lat, city.lon, city.imageLink)
            }
        }
    }
}
