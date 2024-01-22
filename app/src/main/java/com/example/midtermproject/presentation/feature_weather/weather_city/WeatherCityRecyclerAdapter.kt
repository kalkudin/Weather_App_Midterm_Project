package com.example.midtermproject.presentation.feature_weather.weather_city

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.midtermproject.databinding.CityWeatherItemLayoutBinding
import com.example.midtermproject.presentation.feature_weather.model.WeatherCityDetails

class WeatherCityRecyclerAdapter : ListAdapter<WeatherCityDetails, WeatherCityRecyclerAdapter.WeatherCityViewHolder>(
    WeatherCityDiffCallback()
) {

    inner class WeatherCityViewHolder(private val binding: CityWeatherItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(weatherWeeklyDetails: WeatherCityDetails) {
            with(binding) {
                tvDate.text = weatherWeeklyDetails.formattedDate
                tvTemp.text = "${weatherWeeklyDetails.temperatureCelsius}°C"
                icIcon.setImageResource(weatherWeeklyDetails.iconRes)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherCityViewHolder {
        val binding = CityWeatherItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherCityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherCityViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class WeatherCityDiffCallback : DiffUtil.ItemCallback<WeatherCityDetails>() {
        override fun areItemsTheSame(oldItem: WeatherCityDetails, newItem: WeatherCityDetails): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WeatherCityDetails, newItem: WeatherCityDetails): Boolean {
            return oldItem == newItem
        }
    }
}