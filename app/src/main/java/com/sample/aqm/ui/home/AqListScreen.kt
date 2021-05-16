package com.sample.aqm.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.aqm.R
import com.sample.aqm.datalayer.model.AirQualityInfoModel
import com.sample.aqm.ui.HomeViewModel
import com.sample.aqm.utils.hide
import com.sample.aqm.utils.show
import kotlinx.android.synthetic.main.fragment_aq_list_screen.*

/**
 * A simple [Fragment] subclass.
 * Use the [AqListScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class AqListScreen : Fragment() {
    private lateinit var viewModel: HomeViewModel

    private lateinit var aqiAdapter: AirQualityCityAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_aq_list_screen, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        initObserver()
    }

    /**
     * initialize views and bind the views
     */
    private fun initViews() {
        loader.show()
        aqiAdapter = AirQualityCityAdapter(itemClick)
        recyclerView.layoutManager = LinearLayoutManager(context);
        recyclerView.adapter = aqiAdapter
    }

    /**
     * Responsible to observe live data changes
     */
    private fun initObserver() {
        viewModel.aqiListInfo.observe(viewLifecycleOwner, Observer {
            loader.hide()
            aqiAdapter.updateList(it)
        })
    }

    private val itemClick: AQOnClickListener = object : AQOnClickListener {
        override fun onItemClick(model: AirQualityInfoModel) {
            findNavController().navigate(
                AqListScreenDirections.actionAqListScreenToAqiChartFragment(
                    model.city
                )
            )
        }
    }
}