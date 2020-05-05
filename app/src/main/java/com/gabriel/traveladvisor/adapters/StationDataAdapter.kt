package com.gabriel.traveladvisor.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabriel.traveladvisor.R
import com.gabriel.traveladvisor.model.StationData
import com.gabriel.traveladvisor.utils.gone
import com.gabriel.traveladvisor.utils.inflate
import kotlinx.android.synthetic.main.station_data_item.view.*

class StationDataAdapter(
    private val trains: MutableList<StationData>,
    private val listener: (String) -> Unit
) :
    RecyclerView.Adapter<StationDataAdapter.StationDataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationDataViewHolder {
        return StationDataViewHolder(parent.inflate(R.layout.station_data_item, false))
    }

    override fun getItemCount() = trains.size

    override fun onBindViewHolder(holder: StationDataViewHolder, position: Int) =
        holder.bind(trains[position], listener)

    fun addTrains(trains: List<StationData>) {
        this.trains.apply {
            clear()
            addAll(trains)
        }
    }

    inner class StationDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(stationData: StationData, listener: (String) -> Unit) = with(itemView) {
            stationData.apply {
                service_started.text = context.getString(R.string.service_started_text, trainDate)
                train_type.text = trainType
                this@with.origin.text =
                    context.getString(R.string.place_and_hour_text, origin, originTime)
                this@with.destination.text =
                    context.getString(R.string.place_and_hour_text, destination, destinationTime)
                if (status == "No Information") this@with.status.gone() else this@with.status.text =
                    stationData.status
                if (stationData.lastLocation == null) last_location.gone() else last_location.text =
                    context.getString(R.string.last_location_text, lastLocation)
            }

            station_data_info.setOnClickListener {
                stationData.trainCode?.let { trainCode ->
                    listener(
                        trainCode.trim()
                    )
                }
            }
        }
    }
}