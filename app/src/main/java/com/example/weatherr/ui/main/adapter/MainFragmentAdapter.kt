package com.example.weatherr.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherr.R
import com.example.weatherr.model.data.Weather
import com.example.weatherr.ui.main.MainFragment
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*
import kotlin.collections.ArrayList

@InternalCoroutinesApi
class MainFragmentAdapter(private var onItemViewClickListener: MainFragment.OnItemViewClickListener) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>(), Filterable {

    private var weatherData: MutableList<Weather> = arrayListOf()

    private var orig: MutableList<Weather> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setWeather(data: List<Weather>) {
        orig = data as MutableList<Weather>
        notifyDataSetChanged()
    }

    fun removeListener() {
        onItemViewClickListener = null!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_main_recycler_item, parent, false) as View
        )

    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(orig[position])
    }


    override fun getItemCount() = orig.size


    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(weather: Weather) {
            itemView.apply {
                findViewById<TextView>(R.id.mainFragmentRecyclerItemTextView).text =
                    weather.city.city
                setOnClickListener {
                    onItemViewClickListener.onItemViewClick(weather)

                }
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    orig = weatherData
                } else {
                    val results = ArrayList<Weather>()
                    for (row in weatherData){
                        if (row.city.city.lowercase(Locale.ROOT).contains(charSearch.lowercase(
                                Locale.ROOT))){
                            results.add(row)
                        }
                    }
                    orig = results
                }
                val filterResults = FilterResults()
                filterResults.values = orig
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                orig = results?.values as ArrayList<Weather>
                notifyDataSetChanged()
            }

        }
    }

}