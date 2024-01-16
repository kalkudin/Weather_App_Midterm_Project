package com.example.midtermproject.weather_feature.presentation.waether_weekly

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.midtermproject.databinding.WeeklyWeatherItemLayoutBinding
import com.example.midtermproject.weather_feature.presentation.model.WeatherWeeklyDetails

class WeatherWeeklyRecyclerAdapter :
    ListAdapter<WeatherWeeklyDetails, WeatherWeeklyRecyclerAdapter.WeatherWeeklyViewHolder>(
        WeatherWeeklyDiffCallback()
    ) {

    class WeatherWeeklyViewHolder(private val binding: WeeklyWeatherItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(weather: WeatherWeeklyDetails) {
            binding.tvDate.text = weather.formattedDate
            binding.tvTemp.text = "${weather.temperatureCelsius}°C"
            binding.icIcon.setImageResource(weather.iconRes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherWeeklyViewHolder {
        val binding = WeeklyWeatherItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherWeeklyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherWeeklyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class WeatherWeeklyDiffCallback : DiffUtil.ItemCallback<WeatherWeeklyDetails>() {
        override fun areItemsTheSame(oldItem: WeatherWeeklyDetails, newItem: WeatherWeeklyDetails): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WeatherWeeklyDetails, newItem: WeatherWeeklyDetails): Boolean {
            return oldItem == newItem
        }
    }

}