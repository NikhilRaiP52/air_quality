package com.sample.aqm.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sample.aqm.R
import com.sample.aqm.databinding.CityItemLayoutBinding
import com.sample.aqm.datalayer.model.AirQualityInfoModel


class AirQualityCityAdapter(private val listener: AQOnClickListener) :
    RecyclerView.Adapter<CityAQIViewHolder>() {
    private val cityList: MutableList<AirQualityInfoModel> = mutableListOf()

    /**
     * updateList is a functionality which is responsible to update the UI after list data change
     * @param listData is an updated list
     */
    fun updateList(listData: List<AirQualityInfoModel>) {
        cityList.clear()
        cityList.addAll(listData)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityAQIViewHolder {
        val binding: CityItemLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.city_item_layout, parent, false
        )
        return CityAQIViewHolder(binding, listener)
    }


    override fun getItemCount(): Int {
        return cityList.size
    }

    override fun onBindViewHolder(holder: CityAQIViewHolder, position: Int) {
        val dataModel: AirQualityInfoModel = cityList[position]
        holder.bind(dataModel)
    }
}

class CityAQIViewHolder(
    private val binding: CityItemLayoutBinding,
    private val listener: AQOnClickListener
) : RecyclerView.ViewHolder(binding.root) {

    /**
     * bind is a functionality which is responsible to update the UI after list data change
     * @param model has the content to show in that card
     */
    fun bind(model: AirQualityInfoModel) {
        binding.data = model
        binding.setOnClick {
            listener.onItemClick(model)
        }
    }
}

interface AQOnClickListener {
    fun onItemClick(model: AirQualityInfoModel)
}