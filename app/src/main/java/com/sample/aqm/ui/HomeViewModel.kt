package com.sample.aqm.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.Entry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sample.aqm.datalayer.model.AirQualityInfoModel
import com.sample.aqm.utils.AqiLog
import com.sample.aqm.utils.twoDigits
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.ArrayList

class HomeViewModel : ViewModel() {

    private val updatedMapList: ConcurrentHashMap<String, AirQualityInfoModel> = ConcurrentHashMap()
    private val allAqiCityMap: ConcurrentHashMap<String, MutableList<AirQualityInfoModel>> =
        ConcurrentHashMap()
    private val allCityList: MutableList<AirQualityInfoModel> = mutableListOf()
    private var nextUpdateTime = Calendar.getInstance().timeInMillis
    private var nextChartUpdateTime = Calendar.getInstance().timeInMillis

    private val _aqiListInfo = MutableLiveData<List<AirQualityInfoModel>>()
    val aqiListInfo: LiveData<List<AirQualityInfoModel>> = _aqiListInfo

    private val _chartEntry = MutableLiveData<List<Entry>>()
    val chartEntry : LiveData<List<Entry>> = _chartEntry

    private var chartCityName: String? = null

    /**
     * update from chart screen
     * set as null if you are not in chart screen
     *
     * @param city name to show chart
     */
    fun setCityInfo(city: String?) {
        chartCityName = city
    }


    /**
     * This functionality maintain the Socket events
     * It maintain the old city list of data and add/update the city air quality data
     * It maintain the all Air quality records with city wise and help us to show in chart
     * it alos maintain the interval time to update the live data
     *
     * @param message has the updated data from socket
     */
    fun updateAQI(message: String) {
        val arrayAQI = object : TypeToken<Array<AirQualityInfoModel>>() {}.type
        val aqiList = Gson().fromJson<Array<AirQualityInfoModel>>(message, arrayAQI)
        AqiLog.d(TAG, "Start ---")
        aqiList.map {
            AqiLog.d(TAG, "Air Quality Update List : $it")
            updatedMapList[it.city] = it
        }
        AqiLog.d(TAG, "End ---")

        allCityList.clear()

        val currentTime = Calendar.getInstance().timeInMillis

        updatedMapList.forEach { (key: String, value: AirQualityInfoModel) ->
            allCityList.add(value)

            //adding logic to store all records to show in chart
            val list = allAqiCityMap[key]
            if (list != null && list.isNotEmpty()) {
                list.add(value)
                allAqiCityMap[key] = list
            } else {
                val mutableList = mutableListOf<AirQualityInfoModel>()
                mutableList.add(value)
                allAqiCityMap[key] = mutableList
            }

            chartCityName?.let {
                if (key == it && currentTime >= nextChartUpdateTime) {
                    nextChartUpdateTime = currentTime + CHART_INTERVAL
                    updateChartList(allAqiCityMap[key])
                }
            }
        }

        if (currentTime >= nextUpdateTime) {
            nextUpdateTime = currentTime + LIST_INTERVAL
            _aqiListInfo.value = allCityList.sortedBy { it.aqi }
        }
    }

    /**
     * this functionality is responsible to mapping between list of AirQualityInfoModel and Chart Entry
     * It maps the data and update the liveData so that chat can observe
     *
     * @param list has the all AQI data for that particular city which we need to show the chart.
     */
    private fun updateChartList(list: List<AirQualityInfoModel>?) {
        val entries: MutableList<Entry> = ArrayList()
        var lastUpdated = 10
        list?.forEach {
            lastUpdated += 10
            entries.add(Entry(lastUpdated.toFloat(), it.aqi.twoDigits().toFloat()))
        }
        _chartEntry.value = entries
    }

    companion object {
        private val TAG = HomeViewModel::class.java.canonicalName
        private const val LIST_INTERVAL = 10000
        private const val CHART_INTERVAL = 30000
    }
}