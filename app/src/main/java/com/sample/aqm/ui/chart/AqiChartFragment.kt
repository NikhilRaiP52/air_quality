package com.sample.aqm.ui.chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.sample.aqm.R
import com.sample.aqm.ui.HomeViewModel
import kotlinx.android.synthetic.main.fragment_aqi_chart.*


/**
 * A simple [Fragment] subclass.
 * Use the [AqiChartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AqiChartFragment : Fragment() {

    private val args: AqiChartFragmentArgs by navArgs()
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_aqi_chart, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        viewModel.setCityInfo(args.cityName)
        initViews()
    }

    /**
     * initialize views and bind the views
     */
    private fun initViews() {
        cityName.text = args.cityName
        backView.setOnClickListener { activity?.onBackPressed() }

        viewModel.chartEntry.observe(viewLifecycleOwner, Observer {
            updateChart(it)
        })
    }

    /**
     * updateChart is a functionality which is responsible to update the list of item in chart
     * @param entries has the list of content to show in chart
     */
    private fun updateChart(entries: List<Entry>) {
        val lineDataSet: LineDataSet
        if (chartView.data != null && chartView.data.dataSetCount > 0) {
            lineDataSet = chartView.data.getDataSetByIndex(0) as LineDataSet
            lineDataSet.values = entries
            chartView.data.notifyDataChanged()
            chartView.notifyDataSetChanged()
        } else {
            lineDataSet = LineDataSet(entries, "Air Quality")
            lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
            lineDataSet.lineWidth = 1.8f
            lineDataSet.circleRadius = 1f
            lineDataSet.setDrawFilled(true)

            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.fed_red)
            lineDataSet.fillDrawable = drawable
            val dataSets: MutableList<ILineDataSet> = mutableListOf()
            dataSets.add(lineDataSet)

            val data = LineData(dataSets)
            chartView.data = data
            chartView.description.isEnabled = false
        }
        chartView.invalidate()
    }

    override fun onStop() {
        super.onStop()
        viewModel.setCityInfo(null)
    }
}