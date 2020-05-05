package com.gabriel.traveladvisor.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabriel.traveladvisor.R
import com.gabriel.traveladvisor.model.TrainMovement
import com.gabriel.traveladvisor.utils.Utils
import com.gabriel.traveladvisor.utils.inflate
import kotlinx.android.synthetic.main.train_movement_item.view.*

class TrainsMovementsAdapter(private val movements: MutableList<TrainMovement>) :
    RecyclerView.Adapter<TrainsMovementsAdapter.TrainMovementsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainMovementsViewHolder {
        return TrainMovementsViewHolder(parent.inflate(R.layout.train_movement_item, false))
    }

    override fun getItemCount() = movements.size

    override fun onBindViewHolder(holder: TrainMovementsViewHolder, position: Int) =
        holder.bind(movements[position])

    fun addTrains(movements: List<TrainMovement>) {
        this.movements.apply {
            clear()
            addAll(movements)
        }
    }

    inner class TrainMovementsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(trainMovement: TrainMovement) {
            itemView.apply {
                location_code.text = trainMovement.locationCode
                location_type.text = trainMovement.locationType?.let {
                    Utils.getTrainStopLocationType(it, context)
                }
                departure.text = context.getString(
                    R.string.place_and_hour_text,
                    trainMovement.trainOrigin, trainMovement.scheduledDeparture
                )
                destination.text = context.getString(
                    R.string.place_and_hour_text,
                    trainMovement.trainDestination, trainMovement.scheduledArrival
                )
            }
        }
    }
}