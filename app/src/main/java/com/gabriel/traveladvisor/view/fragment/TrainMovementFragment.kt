package com.gabriel.traveladvisor.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabriel.traveladvisor.R
import com.gabriel.traveladvisor.adapters.TrainsMovementsAdapter
import com.gabriel.traveladvisor.network.ApiHelper
import com.gabriel.traveladvisor.network.RetrofitBuilder
import com.gabriel.traveladvisor.model.TrainMovementsList
import com.gabriel.traveladvisor.network.Status
import com.gabriel.traveladvisor.viewmodel.ViewModelFactory
import com.gabriel.traveladvisor.utils.gone
import com.gabriel.traveladvisor.utils.visible
import com.gabriel.traveladvisor.view.custom.PlaceholderView
import com.gabriel.traveladvisor.viewmodel.ApiViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.content_main.recycler
import kotlinx.android.synthetic.main.train_movements.*

const val TRAIN_ID = "train_id"

class TrainMovementFragment : BottomSheetDialogFragment(), PlaceholderView.OnRetryButtonListener {

    private val viewModel: ApiViewModel by lazy {
        val factory = ViewModelFactory(
            ApiHelper(RetrofitBuilder.apiService)
        )
        ViewModelProvider(this, factory).get(ApiViewModel::class.java)
    }
    private lateinit var trainMovementAdapter: TrainsMovementsAdapter
    private var trainId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.train_movements, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trainId = arguments?.getString(TRAIN_ID)
        setupViews()
        setupPlaceHolder()
        setupObservables()
    }

    private fun setupViews() {
        train_information_label.text = getString(R.string.train_information_label_text, trainId)
        trainMovementAdapter = TrainsMovementsAdapter(mutableListOf())
        recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = trainMovementAdapter
        }
    }

    private fun setupPlaceHolder() {
        placeholder_trains_movements.setupPlaceHolder(
            this,
            PlaceholderView.PlaceholderMode.TRAIN_DATA
        )
    }

    private fun setupObservables() {
        viewModel.trainMovementsLiveData.observe(requireActivity(), Observer { output ->
            when (output?.status) {
                Status.LOADING -> performLoading()
                Status.ERROR -> performError()
                Status.SUCCESS -> output.data?.let { performSuccess(it) }
            }
        })
        trainId?.let { viewModel.getTrainMovements(trainId = it) }
    }

    private fun performLoading() {
        recycler.gone()
        placeholder_trains_movements.apply {
            visible()
            setState(
                PlaceholderView.PlaceholderState.LOADING
            )
        }
    }

    private fun performSuccess(data: TrainMovementsList) {
        if (data.trainMovementList.isNullOrEmpty()) {
            placeholder_trains_movements.apply {
                visible()
                setState(PlaceholderView.PlaceholderState.EMPTY)
            }
        } else {
            placeholder_trains_movements.gone()
            recycler.visible()
            data.trainMovementList?.let {
                trainMovementAdapter.apply {
                    addTrains(it)
                    notifyDataSetChanged()
                }
            }

        }
    }

    private fun performError() {
        placeholder_trains_movements.apply {
            visible()
            setState(
                PlaceholderView.PlaceholderState.ERROR
            )
        }
    }

    override fun onRetryButtonClicked(placeholderMode: PlaceholderView.PlaceholderMode) {
        when (placeholderMode) {
            PlaceholderView.PlaceholderMode.TRAIN_DATA -> trainId?.let {
                viewModel.getTrainMovements(it)
            }
            else -> {
            }
        }
    }

}

