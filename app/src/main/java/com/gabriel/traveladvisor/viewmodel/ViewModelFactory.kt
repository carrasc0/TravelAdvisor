package com.gabriel.traveladvisor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gabriel.traveladvisor.network.ApiHelper
import com.gabriel.traveladvisor.repository.ApiRepository

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ApiViewModel::class.java)) {
            return ApiViewModel(
                ApiRepository(
                    apiHelper
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}