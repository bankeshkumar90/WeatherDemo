package com.app.weatherdemo.ui.weatherdetails

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.app.weatherdemo.R
import com.app.weatherdemo.appdata.adapters.MobiWeatherAdapter
import com.app.weatherdemo.appdata.api.ResponseState
import com.app.weatherdemo.databinding.FragmentWeatherDetailsBinding
import com.app.weatherdemo.appdata.model.Coord
import com.app.weatherdemo.appdata.model.NetworkWeatherForecast
import com.app.weatherdemo.appdata.model.WeatherAPIResponse
import com.app.weatherdemo.apputils.*
import com.app.weatherdemo.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherDetails : Fragment() {

    var lat =""
    var long = ""
    var cityName = ""

    @Inject
    lateinit var weatherForecastAdapter: MobiWeatherAdapter
    lateinit var mList:List<NetworkWeatherForecast>
    lateinit var binding: FragmentWeatherDetailsBinding
    private  val viewModel: WeatherDetailsViewModel by lazy {
        ViewModelProvider(this).get(WeatherDetailsViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            lat = savedInstanceState.getString(Constants.CITY_LOCATION,"")!!
            long = savedInstanceState.getString(Constants.CITY_LONG,"")!!

        }
        val bundle = this.arguments
        if (bundle != null) {
            lat = bundle.getString(Constants.CITY_LAT,"")!!
            long = bundle.getString(Constants.CITY_LONG,"")!!
            cityName = bundle.getString(Constants.CITY_LOCATION,"")!! }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherDetailsBinding.inflate(inflater,container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.background.getBackgroundImage(Uri.parse((activity as MainActivity).mySharedPrefrences.getBrackgroundImage()))
        binding.forecastRecyclerview.apply {
            adapter = weatherForecastAdapter
        }
        observeViewModel()
        if(!lat.isNullOrEmpty()){
            fetchData()
        }
        binding.forecastSwipeRefresh.setOnRefreshListener {
            binding.recProg.visible()
            fetchData()
            binding.forecastSwipeRefresh.isRefreshing = false
        }
    }

    private fun fetchData(){
        val coord = Coord(lat.toDouble(),long.toDouble())
        viewModel.getWeatherForecastByLocation(coord)
        viewModel.getWeatherByLocation(coord)
    }
    private fun observeViewModel() {
        with(viewModel) {

            weatherData.observe(viewLifecycleOwner) { weather ->
                weather?.let { weather_state ->
                    when (weather_state) {
                        is ResponseState.Success -> {
                            binding.errorLayout.root.gone()
                            binding.forecastRecyclerview.visible()
                            setData(weather_state.data)

                        }
                        is ResponseState.Error -> {
                            weather_state.message?.let { binding.root.snackbar(it) }

                            if (weather_state.message == "No internet Connection") {
                                binding.errorLayout.root.visible()
                            }

                        }

                    }


                }
            }

            weatherForecast.observe(viewLifecycleOwner) { forecastState ->
                when (forecastState) {
                    is ResponseState.Success -> {
                        binding.recProg.gone()
                         forecastState.data?.let { dataList ->
                            weatherForecastAdapter.submitList(dataList)
                             binding.forecastRecyclerview.visible()
                             if (dataList.isEmpty()) {
                                binding.emptyLayout.root.visible()
                            } else {
                                binding.emptyLayout.root.gone()
                            }

                            mList = dataList

                        }

                    }
                    is ResponseState.Error -> {
                        binding.recProg.gone()
                        forecastState.message?.let {
                            binding.root.snackbar(it)
                         }
                    }
                    is ResponseState.Loading -> {
                        binding.recProg.visible()
                    }
                }

            }
        }

    }

    private fun setData(data: WeatherAPIResponse?) {
        binding.weatherInText.text = data?.name
        binding.dateText.text = viewModel.currentSystemTime()
        binding.weatherTemperature.text = getTemp(data?.main?.temp)
        binding.weatherMinMax.text = getTemp(data?.main?.temp_max) + "/" + getTemp(data?.main?.temp_min)
        binding.weatherMain.text = data?.weather?.get(0)?.description
        binding.humidityText.text = data?.main?.humidity.toString() + "%"
        binding.pressureText.text = data?.main?.pressure.toString() + "hPa"
        binding.windSpeedText.text = data?.wind?.speed.toString() + "m/s"
        binding.weatherIcon.setImageDrawable(resources.getDrawable(R.drawable.cloudy))


    }
    fun getTemp(temp: Double?): String {
        var mTemp: String = ""
        if ((activity as MainActivity).mySharedPrefrences.getUnitsOfMeasurement() == Constants.FAHRENHEIT)
            mTemp = temp?.let {
                convertCelsiusToFahrenheit(it) }.toString()
        else
            mTemp = temp?.toString() + "°C"
        return mTemp
    }

}