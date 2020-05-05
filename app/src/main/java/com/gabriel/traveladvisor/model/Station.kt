package com.gabriel.traveladvisor.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "objStation", strict = false)
data class Station(
    @field:Element(name = "StationDesc")
    var desc: String? = null,
    @field:Element(name = "StationAlias", required = false)
    var alias: String? = null,
    @field:Element(name = "StationLatitude")
    var latitude: String? = null,
    @field:Element(name = "StationLongitude")
    var longitude: String? = null,
    @field:Element(name = "StationCode")
    var code: String? = null,
    @field:Element(name = "StationId")
    var id: Int? = null
) {
    constructor() : this("")
}