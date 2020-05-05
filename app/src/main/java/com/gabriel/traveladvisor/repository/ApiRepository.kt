package com.gabriel.traveladvisor.repository

import com.gabriel.traveladvisor.network.ApiHelper

class ApiRepository(private val apiHelper: ApiHelper) {

    suspend fun getAllStations() = apiHelper.getAllStations()

    suspend fun getStationDataByDesc(station: String) = apiHelper.getStationDataByName(station)

    suspend fun getTrainMovements(trainId: String) = apiHelper.getTrainMovements(trainId)

}