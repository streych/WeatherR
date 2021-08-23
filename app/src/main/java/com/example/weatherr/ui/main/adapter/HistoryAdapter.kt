package com.example.weatherr.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherr.databinding.HistoryRecyclerItemBinding
import com.example.weatherr.model.data.Weather

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    private var data: List<Weather> = arrayListOf()

    fun setData(data: List<Weather>){
        this.data = data
        notifyDataSetChanged()
    }


    inner class HistoryViewHolder(private val binding: HistoryRecyclerItemBinding):
    RecyclerView.ViewHolder(binding.root){
        fun bind(data: Weather){
            binding.apply {
                cityName.text = data.city.city
                weatherCondition.text = data.condition
                weatherTemperature.text = data.temperature.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = HistoryRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

}