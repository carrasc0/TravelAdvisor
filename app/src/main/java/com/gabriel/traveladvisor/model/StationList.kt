package com.gabriel.traveladvisor.model

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "ArrayOfObjStation", strict = false)
data class StationList constructor(
    @field:ElementList(
        name = "objStation",
        entry = "objStation",
        inline = true,
        required = false,
        empty = true
    )
    var stationList: List<Station>? = null
)