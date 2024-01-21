package com.example.midtermproject.presentation.weather_feature.weather_week_day

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.midtermproject.databinding.WeatherWeekDayItemLayoutBinding
import com.example.midtermproject.presentation.weather_feature.model.WeatherDayDetails

class WeatherWeekDayRecyclerAdapter : ListAdapter<WeatherDayDetails, WeatherWeekDayRecyclerAdapter.WeatherDayViewHolder>(
    WeatherDayDiffCallback()
) {

    inner class WeatherDayViewHolder(private val binding: WeatherWeekDayItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(weatherDayDetails: WeatherDayDetails) {
            with(binding) {
                tvTemp.text = "${weatherDayDetails.temperatureCelsius}°C"
                tvHumidity.text = "${weatherDayDetails.relativeHumidity}%"
                progressBar.progress = weatherDayDetails.relativeHumidity
                tvWind.text = "${weatherDayDetails.windSpeed} km/h"
                tvTime.text = weatherDayDetails.formattedTime
                icIcon.setImageResource(weatherDayDetails.iconRes)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherDayViewHolder {
        val binding = WeatherWeekDayItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherDayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherDayViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class WeatherDayDiffCallback : DiffUtil.ItemCallback<WeatherDayDetails>() {
        override fun areItemsTheSame(oldItem: WeatherDayDetails, newItem: WeatherDayDetails): Boolean {
            return oldItem.weatherType == newItem.weatherType
        }

        override fun areContentsTheSame(oldItem: WeatherDayDetails, newItem: WeatherDayDetails): Boolean {
            return oldItem == newItem
        }
    }
}