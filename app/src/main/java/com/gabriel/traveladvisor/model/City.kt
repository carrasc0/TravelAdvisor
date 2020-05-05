package com.gabriel.traveladvisor.model

enum class City(val cityName: String) {
    ARKLOW("Arklow"),
    SHANKILL("Shankill");

    companion object {
        fun getCityNames(): ArrayList<String> {
            val cities = arrayListOf<String>()
            values().forEach {
                cities.add(it.cityName)
            }
            return cities
        }
    }
}
