package com.example.weatherr.ui.main

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.bumptech.glide.annotation.GlideModule
import com.example.weatherr.R
import com.example.weatherr.databinding.DetailsFragmentBinding
import com.example.weatherr.model.data.City
import com.example.weatherr.model.data.Weather
import com.example.weatherr.viewmodel.Apstate
import com.example.weatherr.viewmodel.DetailsViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main_recycler_item.*
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.view.*
import kotlinx.coroutines.InternalCoroutinesApi
import java.io.IOException
import java.util.*


@InternalCoroutinesApi
@GlideModule
class DetailsFragment : Fragment() {


    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherBundle: Weather

    @InternalCoroutinesApi
    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @InternalCoroutinesApi
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Weather()
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.getWeatherFromRemoteSource(weatherBundle.city.lat, weatherBundle.city.lon)

    }



    private fun renderData(apstate: Apstate) {
        when (apstate) {
            is Apstate.Succes -> {
                binding.mainView.show()
                binding.loadingLayout.hide()
                setWeather(apstate.weatherData[0])
            }
            is Apstate.Loading -> {
                binding.mainView.hide()
                binding.loadingLayout.show()
            }
            is Apstate.Error -> {
                binding.mainView.show()
                binding.loadingLayout.hide()

            }
        }
    }

    private fun setWeather(weather: Weather) {
        with(binding){
            weatherBundle.city.let { city ->
                cityName.text = city.city
                cityCoordinates.text = String.format(
                    getString(R.string.city_coordinates),
                    city.lat.toString(),
                    city.lon.toString()
                )
                saveCity(city, weather)
            }
            weather.let {
                temperatureValue.text = it.temperature.toString()
                feelsLikeValue.text = it.feelsLike.toString()
                weatherCondition.text = it.condition
            }
            weather.icon?.let{
                weatherIcon.loadSvg("https://yastatic.net/weather/i/icons/blueye/color/svg/${it}.svg")
            }
            Picasso
                .get()
                .load("https://freepngimg.com/thumb/house/84949-house-housing-recreation-city-hd-image-free-png.png")
                .into(headerIcon)
        }
    }

    private fun saveCity(city: City, weather: Weather) {
        viewModel.saveCityToDb(
            Weather(
                city,
                weather.temperature,
                weather.feelsLike,
                weather.condition
            )
        )
    }

    companion object {

        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun AppCompatImageView.loadSvg(url: String) {
        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry { add(SvgDecoder(this@loadSvg.context)) }
            .build()

        val request = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(500)
            .data(url)
            .target(this)
            .build()

        imageLoader.enqueue(request)
    }
}