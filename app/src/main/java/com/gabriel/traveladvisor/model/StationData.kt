package com.gabriel.traveladvisor.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root


@Root(name = "objStationData", strict = false)
data class StationData(
    @field:Element(name = "Traincode")
    var trainCode: String? = null,
    @field:Element(name = "Traindate")
    var trainDate: String? = null,
    @field:Element(name = "Origin")
    var origin: String? = null,
    @field:Element(name = "Destination")
    var destination: String? = null,
    @field:Element(name = "Origintime")
    var originTime: String? = null,
    @field:Element(name = "Destinationtime")
    var destinationTime: String? = null,
    @field:Element(name = "Status")
    var status: String? = null,
    @field:Element(name = "Lastlocation", required = false)
    var lastLocation: String? = null,
    @field:Element(name = "Duein")
    var dueIn: String? = null,
    @field:Element(name = "Late")
    var late: String? = null,
    @field:Element(name = "Exparrival")
    var expArrival: String? = null,
    @field:Element(name = "Expdepart")
    var expDepart: String? = null,
    @field:Element(name = "Scharrival")
    var schArrival: String? = null,
    @field:Element(name = "Schdepart")
    var schDepart: String? = null,
    @field:Element(name = "Direction")
    var direction: String? = null,
    @field:Element(name = "Traintype")
    var trainType: String? = null,
    @field:Element(name = "Locationtype")
    var locationType: String? = null
) {
    constructor() : this("")
}