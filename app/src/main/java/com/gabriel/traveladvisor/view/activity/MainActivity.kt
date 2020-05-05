package com.gabriel.traveladvisor.view.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabriel.traveladvisor.R
import com.gabriel.traveladvisor.adapters.StationDataAdapter
import com.gabriel.traveladvisor.model.City
import com.gabriel.traveladvisor.network.ApiHelper
import com.gabriel.traveladvisor.network.RetrofitBuilder
import com.gabriel.traveladvisor.model.Station
import com.gabriel.traveladvisor.model.StationDataList
import com.gabriel.traveladvisor.network.Status
import com.gabriel.traveladvisor.utils.*
import com.gabriel.traveladvisor.view.custom.PlaceholderView
import com.gabriel.traveladvisor.view.fragment.TRAIN_ID
import com.gabriel.traveladvisor.view.fragment.TrainMovementFragment
import com.gabriel.traveladvisor.viewmodel.ApiViewModel
import com.gabriel.traveladvisor.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

const val BOTTOM_SHEET_TAG = "bottom_sheet_fragment"

class MainActivity : AppCompatActivity(),
    PlaceholderView.OnRetryButtonListener {

    private val viewModel: ApiViewModel by lazy {
        val factory = ViewModelFactory(
            ApiHelper(RetrofitBuilder.apiService)
        )
        ViewModelProvider(this, factory).get(ApiViewModel::class.java)
    }

    private lateinit var currentCity: City
    private lateinit var stationDataAdapter: StationDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupViews()
        setupPlaceHolders()
        showInitialDialog()
    }

    private fun setupViews() {
        setupStationObserver()
        setupStationDataObserver()
        stationDataAdapter = StationDataAdapter(mutableListOf()) {
            showBottomSheetFragment(it)
        }
        recycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = stationDataAdapter
        }
    }

    private fun setupPlaceHolders() {
        station_placeholder.setupPlaceHolder(this, PlaceholderView.PlaceholderMode.STATION)
        station_data_placeholder.setupPlaceHolder(
            this,
            PlaceholderView.PlaceholderMode.STATION_DATA
        )
    }

    private fun showBottomSheetFragment(trainCode: String) {
        val bottomSheetFragment = TrainMovementFragment()
        val bundle = Bundle()
        bundle.putString(TRAIN_ID, trainCode)
        bottomSheetFragment.arguments = bundle
        bottomSheetFragment.show(supportFragmentManager, BOTTOM_SHEET_TAG)
    }

    private fun setupStationObserver() {
        viewModel.stationLiveData.observe(this@MainActivity, Observer {
            it?.let { output ->
                when (output.status) {
                    Status.LOADING -> performStationLoading()
                    Status.ERROR -> performStationError()
                    Status.SUCCESS -> output.data?.let { station ->
                        performStationSuccess(station)
                    }
                }
            }
        })
    }

    private fun performStationLoading() {
        station_label.gone()
        station_placeholder.apply {
            visible()
            setState(
                PlaceholderView.PlaceholderState.LOADING
            )
        }
    }

    private fun performStationError() {
        station_label.gone()
        station_placeholder.apply {
            visible()
            setState(
                PlaceholderView.PlaceholderState.ERROR
            )
        }
    }

    private fun performStationSuccess(station: Station) {
        station_placeholder.gone()
        station_label.visible()
        showStation(station)
    }

    private fun setupStationDataObserver() {
        viewModel.stationDataListLiveData.observe(this, Observer {
            it?.let { output ->
                when (output.status) {
                    Status.LOADING -> performStationDataLoading()
                    Status.ERROR -> performStationDataError()
                    Status.SUCCESS -> output.data?.let { data ->
                        performStationDataSuccess(data)
                    }
                }
            }
        })
    }

    private fun performStationDataLoading() {
        recycler.gone()
        trains_label.gone()
        station_data_placeholder.apply {
            visible()
            setState(
                PlaceholderView.PlaceholderState.LOADING
            )
        }
    }

    private fun performStationDataError() {
        station_data_placeholder.apply {
            visible()
            setState(
                PlaceholderView.PlaceholderState.ERROR
            )
        }
        trains_label.gone()
    }

    private fun performStationDataSuccess(data: StationDataList) {
        if (data.stationDataList.isNullOrEmpty()) {
            trains_label.gone()
            station_data_placeholder.apply {
                visible()
                setState(
                    PlaceholderView.PlaceholderState.EMPTY
                )
            }
        } else {
            recycler.visible()
            trains_label.visible()
            station_data_placeholder.gone()
            stationDataAdapter.apply {
                addTrains(data.stationDataList!!)
                notifyDataSetChanged()
            }
        }
    }

    private fun showStation(station: Station) {
        station_label.text =
            getString(
                R.string.station_view_description_text,
                station.desc,
                station.code,
                station.id
            )
    }

    private fun switchDestination() {
        currentCity = if (currentCity == City.ARKLOW) {
            City.SHANKILL
        } else {
            City.ARKLOW
        }
        updateViewModelData()
        switch_destination_view.setData(currentCity.cityName)
    }

    private fun showInitialDialog() {
        val cities = City.getCityNames().toTypedArray()
        val builder = AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.dialog_current_city_tittle))
            setItems(cities) { dialog, which ->
                currentCity = City.valueOf(cities[which].toUpperCase(Locale.ROOT))
                switch_destination_view.setData(currentCity.cityName)
                updateViewModelData()
                dialog.dismiss()
            }
            create()
        }
        builder.show()
    }

    private fun updateViewModelData() {
        viewModel.apply {
            getStation(currentCity.cityName)
            getStationDataByDesc(currentCity.cityName)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.change_destination -> switchDestination()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRetryButtonClicked(placeholderMode: PlaceholderView.PlaceholderMode) {
        when (placeholderMode) {
            PlaceholderView.PlaceholderMode.STATION -> viewModel.getStation(currentCity.cityName)
            PlaceholderView.PlaceholderMode.STATION_DATA -> viewModel.getStationDataByDesc(
                currentCity.cityName
            )
            else -> {
            }
        }
    }

}
