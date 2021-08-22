package com.app.weatherdemo.appdata

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.weatherdemo.appdata.CityAdapter.*
import com.app.weatherdemo.appdata.db.WeatherEntity
import com.app.weatherdemo.appdata.model.Coord
import com.app.weatherdemo.appdata.model.Main
import com.app.weatherdemo.appdata.model.Weather
import com.app.weatherdemo.appdata.model.Wind
import com.app.weatherdemo.databinding.CityItemBinding
class CityAdapter(clickListener: ClickListener) :
        ListAdapter<WeatherEntity, CityViewHolder>(
            DBWeatherDiffCallBack()
        ) {
    lateinit var context: Context
    var itemClickListener = clickListener
    lateinit var cityItemBind: CityItemBinding
    inner class CityViewHolder(val cityItemBinding: CityItemBinding):
        RecyclerView.ViewHolder(cityItemBinding.root){
        fun bindViewHolder(pos: Int, weatherEntity: WeatherEntity){
            cityItemBinding.cityName.text = weatherEntity.cityName.toString()
            cityItemBind = cityItemBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context = parent.context
        val binding = CityItemBinding.inflate(layoutInflater, parent, false)

        return CityViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bindViewHolder(position, getItem(position))
        /*holder.itemView.setOnClickListener(View.OnClickListener {
            itemClickListener.onClick(currentList[position])
        })*/
        cityItemBind.deleteIcon.setOnClickListener(View.OnClickListener {
            val coord = Coord(currentList[position].coord?.lat,currentList[position].coord?.lon)
            val adapterItem = AdapterItem(currentList[position].cityName,coord, true, currentList[position],position )
            itemClickListener.onClick(adapterItem)
        })
        cityItemBind.cityName.setOnClickListener(View.OnClickListener {
            val coord = Coord(currentList[position].coord?.lat,currentList[position].coord?.lon)
            val adapterItem = AdapterItem(currentList[position].cityName,coord, false, currentList[position],position )
            itemClickListener.onClick(adapterItem)
        })
    }

    class  DBWeatherDiffCallBack : DiffUtil.ItemCallback<WeatherEntity>() {
        override fun areItemsTheSame(
            oldItem: WeatherEntity,
            newItem: WeatherEntity
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: WeatherEntity,
            newItem: WeatherEntity
        ): Boolean {
            return oldItem == newItem
        }

    }

    data class AdapterItem(val cityName:String?, val coord: Coord?, val deleteBtnClicked: Boolean,
                           val objectToDelete:WeatherEntity,val position: Int)

    class ClickListener(val clickListener: (weatherEntity: AdapterItem) -> Unit){
        fun onClick(adapterItem: AdapterItem) = clickListener(adapterItem)
    }
}