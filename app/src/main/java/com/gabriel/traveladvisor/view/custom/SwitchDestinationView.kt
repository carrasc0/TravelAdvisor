package com.gabriel.traveladvisor.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.gabriel.traveladvisor.R
import com.gabriel.traveladvisor.model.City
import kotlinx.android.synthetic.main.switch_destination.view.*

class SwitchDestinationView : RelativeLayout {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.switch_destination, this)
    }

    fun setData(originCity: String) {
        origin.text = originCity
        destination.text = getDestination(originCity)
    }

    private fun getDestination(originCity: String): String {
        return if (originCity == City.ARKLOW.cityName) City.SHANKILL.cityName else City.ARKLOW.cityName
    }
}