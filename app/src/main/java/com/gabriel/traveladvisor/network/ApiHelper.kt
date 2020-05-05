package com.gabriel.traveladvisor.network

class ApiHelper(private val apiService: ApiService) {

    suspend fun getAllStations() = apiService.getAllStationsAsync().await()

    suspend fun getStationDataByName(station: String) =
        apiService.getStationDataByNameAsync(station).await()

    suspend fun getTrainMovements(trainId: String) =
        apiService.getTrainMovementsAsync(trainId).await()

}