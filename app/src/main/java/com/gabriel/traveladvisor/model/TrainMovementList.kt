package com.gabriel.traveladvisor.model

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "TrainMovementsList", strict = false)
data class TrainMovementsList constructor(
    @field:ElementList(
        name = "objTrainMovements",
        entry = "objTrainMovements",
        inline = true,
        required = false,
        empty = true
    )
    var trainMovementList: List<TrainMovement>? = null
)