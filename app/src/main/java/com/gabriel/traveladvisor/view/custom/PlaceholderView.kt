package com.gabriel.traveladvisor.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.gabriel.traveladvisor.R
import com.gabriel.traveladvisor.utils.gone
import com.gabriel.traveladvisor.utils.visible
import kotlinx.android.synthetic.main.placeholder_view.view.*

class PlaceholderView : RelativeLayout {

    private lateinit var onRetryButtonListener: OnRetryButtonListener
    private lateinit var placeholderMode: PlaceholderMode

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

    fun setState(state: PlaceholderState) = updateState(state)

    fun setupPlaceHolder(
        onRetryButtonListener: OnRetryButtonListener,
        placeholderMode: PlaceholderMode
    ) {
        this.onRetryButtonListener = onRetryButtonListener
        this.placeholderMode = placeholderMode
    }

    private fun updateState(state: PlaceholderState) {
        when (state) {
            PlaceholderState.LOADING -> performLoadingState()
            PlaceholderState.ERROR -> performErrorState()
            PlaceholderState.EMPTY -> performEmptyState()
        }
    }

    private fun performLoadingState() {
        placeholder_button.gone()
        placeholder_label.gone()
        placeholder_progress.visible()
    }

    private fun performErrorState() {
        placeholder_progress.gone()
        placeholder_button.visible()
        placeholder_label.apply {
            visible()
            text = when (placeholderMode) {
                PlaceholderMode.STATION -> context.getString(R.string.station_error_state_text)
                PlaceholderMode.STATION_DATA -> context.getString(R.string.station_data_error_state_text)
                PlaceholderMode.TRAIN_DATA -> context.getString(R.string.train_movements_error_state_text)
            }
        }
    }

    private fun performEmptyState() {
        placeholder_progress.gone()
        placeholder_button.visible()
        placeholder_label.apply {
            visible()
            text = when (placeholderMode) {
                PlaceholderMode.STATION -> context.getString(R.string.station_empty_state_text)
                PlaceholderMode.STATION_DATA -> context.getString(R.string.station_data_empty_state_text)
                PlaceholderMode.TRAIN_DATA -> context.getString(R.string.train_empty_state_text)
            }
        }
    }

    private fun init() {
        View.inflate(context, R.layout.placeholder_view, this)
        placeholder_button.setOnClickListener {
            onRetryButtonListener.onRetryButtonClicked(placeholderMode)
        }
    }

    interface OnRetryButtonListener {
        fun onRetryButtonClicked(placeholderMode: PlaceholderMode)
    }

    enum class PlaceholderState {
        LOADING, ERROR, EMPTY
    }

    enum class PlaceholderMode {
        STATION, STATION_DATA, TRAIN_DATA
    }

}