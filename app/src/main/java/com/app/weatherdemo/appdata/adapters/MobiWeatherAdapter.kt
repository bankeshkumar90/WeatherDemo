package com.app.weatherdemo.appdata.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.weatherdemo.R
import com.app.weatherdemo.appdata.model.NetworkWeatherForecast
import com.app.weatherdemo.apputils.Constants
import com.app.weatherdemo.databinding.CatdItemBinding
import com.app.weatherdemo.apputils.celsiusToFahrenheit

class MobiWeatherAdapter() :
    ListAdapter<NetworkWeatherForecast, MobiWeatherAdapter.WeatherViewHolder>
    (UpdateWeatherDataOnCall()){

    lateinit var context: Context
    inner class WeatherViewHolder(private val cardItemBinding: CatdItemBinding): RecyclerView.ViewHolder(cardItemBinding.root) {
        fun bind(position: Int, weatherForecast: NetworkWeatherForecast){
            cardItemBinding.weatherMain.text = weatherForecast.weather[0].main
            cardItemBinding.weatherDescription.text = weatherForecast.weather.get(0).description
            cardItemBinding.weatherIcon.setImageDrawable(context.resources.getDrawable(R.drawable.cloudy))
            cardItemBinding.weatherTime.text = weatherForecast.dt_txt
            cardItemBinding.cityTemp.text = getWeatherTemp(weatherForecast.main?.temp)
            cardItemBinding.humidityText.text = weatherForecast.main?.humidity.toString()+"%"
            cardItemBinding.pressureText.text = weatherForecast.main?.pressure.toString()+ "hPa"
            cardItemBinding.windText.text = weatherForecast.wind?.speed.toString()+ "m/s"

        }
    }

    class UpdateWeatherDataOnCall : DiffUtil.ItemCallback<NetworkWeatherForecast>(){
        override fun areItemsTheSame(
            oldItem: NetworkWeatherForecast,
            newItem: NetworkWeatherForecast
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: NetworkWeatherForecast,
            newItem: NetworkWeatherForecast
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        val binding = CatdItemBinding.inflate(inflater, parent, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(position, getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun getWeatherTemp(temp:Double?): String {
        val sp = context.getSharedPreferences(Constants.SHARED_PREFRENCE, 0)
        var mTemp:String = ""
        if( sp.getString(Constants.UNITS_TO_MEASURE, Constants.CELSIUS)==Constants.FAHRENHEIT)
            mTemp = temp?.let { celsiusToFahrenheit(it) }.toString()
        else
            mTemp = temp?.toString()+"°C"
        return mTemp
    }
}