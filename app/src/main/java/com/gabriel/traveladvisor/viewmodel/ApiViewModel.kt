package com.gabriel.traveladvisor.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabriel.traveladvisor.model.Station
import com.gabriel.traveladvisor.model.StationDataList
import com.gabriel.traveladvisor.model.TrainMovementsList
import com.gabriel.traveladvisor.repository.ApiRepository
import com.gabriel.traveladvisor.network.Output
import com.gabriel.traveladvisor.network.Output.Companion.error
import com.gabriel.traveladvisor.network.Output.Companion.loading
import com.gabriel.traveladvisor.network.Output.Companion.success
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApiViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    private var station = MutableLiveData<Output<Station?>>()
    val stationLiveData: LiveData<Output<Station?>>
        get() = station

    private var stationDataList = MutableLiveData<Output<StationDataList?>>()
    val stationDataListLiveData: LiveData<Output<StationDataList?>>
        get() = stationDataList

    private var trainMovements = MutableLiveData<Output<TrainMovementsList?>>()
    val trainMovementsLiveData: LiveData<Output<TrainMovementsList?>>
        get() = trainMovements

    fun getStation(stationDesc: String) {
        CoroutineScope(Dispatchers.IO).launch {
            station.postValue(loading(data = null))
            try {
                station.postValue(success(data = apiRepository.getAllStations().stationList?.let {
                    filterStation(
                        it,
                        stationDesc
                    )
                }))
            } catch (exception: Exception) {
                station.postValue(
                    error(
                        data = null,
                        message = exception.message ?: "Error fetching StationList"
                    )
                )
            }
        }
    }

    fun getStationDataByDesc(station: String) {
        CoroutineScope(Dispatchers.IO).launch {
            stationDataList.postValue(loading(data = null))
            try {
                stationDataList.postValue(success(data = apiRepository.getStationDataByDesc(station)))
            } catch (exception: Exception) {
                stationDataList.postValue(
                    error(
                        data = null,
                        message = exception.message ?: "Error fetching Station Data"
                    )
                )
            }
        }
    }

    fun getTrainMovements(trainId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            trainMovements.postValue(loading(data = null))
            try {
                trainMovements.postValue(success(data = apiRepository.getTrainMovements(trainId)))
            } catch (exception: Exception) {
                trainMovements.postValue(
                    error(
                        data = null,
                        message = exception.message ?: "Error fetching Trains Movements"
                    )
                )
            }
        }
    }

    private fun filterStation(stations: List<Station>, station: String): Station? {
        return stations.find {
            it.desc?.trim() == station
        }
    }

}