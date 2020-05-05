package com.gabriel.traveladvisor.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root


@Root(name = "objTrainMovements", strict = false)
data class TrainMovement(
    @field:Element(name = "LocationCode")
    var locationCode: String? = null,
    @field:Element(name = "LocationType")
    var locationType: String? = null,
    @field:Element(name = "TrainOrigin")
    var trainOrigin: String? = null,
    @field:Element(name = "TrainDestination")
    var trainDestination: String? = null,
    @field:Element(name = "ScheduledArrival")
    var scheduledArrival: String? = null,
    @field:Element(name = "ScheduledDeparture", required = false)
    var scheduledDeparture: String? = null
) {
    constructor() : this("")
}