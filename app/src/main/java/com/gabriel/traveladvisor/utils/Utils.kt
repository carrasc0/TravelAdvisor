package com.gabriel.traveladvisor.utils

import android.annotation.SuppressLint
import android.content.Context
import com.gabriel.traveladvisor.R
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    companion object {
        @SuppressLint("SimpleDateFormat")
        fun getCurrentDate(): String {
            val c = Calendar.getInstance()
            return "${c[Calendar.DAY_OF_MONTH]} ${SimpleDateFormat("MMMM").format(c.time)} ${c[Calendar.YEAR]}"
        }

        fun getTrainStopLocationType(type: String, context: Context): String {
            return when (type) {
                "O" -> context.getString(R.string.origin_text)
                "S" -> context.getString(R.string.stop_text)
                "T" -> context.getString(R.string.non_stopping_text)
                "D" -> context.getString(R.string.destination_text)
                else -> ""
            }
        }
    }

}