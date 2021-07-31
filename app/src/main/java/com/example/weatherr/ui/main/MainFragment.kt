package com.example.weatherr.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherr.R
import com.example.weatherr.databinding.MainFragmentBinding
import com.example.weatherr.model.Apstate
import com.example.weatherr.model.data.Weather
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {


    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val  binding
    get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { a -> renderData(a) })
        viewModel.getWeather()
    }
    private fun renderData(data: Apstate) {
        when(data){
            is Apstate.Succes -> {
                val weatherData = data.weatherData
                binding.loadingLayout.visibility = View.GONE
                populateData(weatherData)
            }
            is Apstate.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is Apstate.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.mainView, "it.s a tost", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun populateData(weatherData: Weather){
        with(binding){
            cityName.text = weatherData.city.city
            cityCoordinates.text = String.format(
                getString(R.string.city_coordinates),
                weatherData.city.lat.toString(),
                weatherData.city.lon.toString()
            )
            temperatureValue.text = weatherData.temperature.toString()
            feelsLikeValue.text = weatherData.feelsLike.toString()
        }
    }


}