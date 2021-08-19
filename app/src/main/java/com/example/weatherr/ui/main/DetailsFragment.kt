package com.example.weatherr.ui.main

import android.graphics.drawable.PictureDrawable
import android.media.ImageReader
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.RequestBuilder;
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.util.CoilUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.example.weatherr.R
import com.example.weatherr.databinding.DetailsFragmentBinding
import com.example.weatherr.viewmodel.Apstate
import com.example.weatherr.model.data.Weather
import com.example.weatherr.viewmodel.DetailsViewModel
import com.squareup.picasso.Picasso

@GlideModule
class DetailsFragment : Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherBundle: Weather
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
            }
            weather.let {
                temperatureValue.text = it.temperature.toString()
                feelsLikeValue.text = it.feelsLike.toString()
                weatherCondition.text = it.condition
            }
            weather.icon?.let{
                //GlideToConvertYou не работает как сделать не знаю.
            }
            Picasso
                .get()
                .load("https://freepngimg.com/thumb/house/84949-house-housing-recreation-city-hd-image-free-png.png")
                .into(headerIcon)
        }
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
}