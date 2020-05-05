package com.gabriel.traveladvisor.network

import com.gabriel.traveladvisor.model.*
import com.gabriel.traveladvisor.utils.Utils
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("getAllStationsXML")
    fun getAllStationsAsync(): Deferred<StationList>

    @GET("getStationDataByNameXML")
    fun getStationDataByNameAsync(@Query("StationDesc") stationDesc: String): Deferred<StationDataList>

    @GET("getTrainMovementsXML")
    fun getTrainMovementsAsync(
        @Query("TrainId") trainId: String,
        @Query("TrainDate") trainDate: String = Utils.getCurrentDate()
    ): Deferred<TrainMovementsList>

}