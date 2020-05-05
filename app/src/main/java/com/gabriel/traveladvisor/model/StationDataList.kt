package com.gabriel.traveladvisor.model

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


@Root(name = "ArrayOfObjStationData", strict = false)
data class StationDataList constructor(
    @field:ElementList(
        name = "objStationData",
        entry = "objStationData",
        inline = true,
        required = false,
        empty = true
    )
    var stationDataList: List<StationData>? = null
)