package ru.orlovvv.weather.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.orlovvv.weather.R
import ru.orlovvv.weather.adapters.LocationAdapter
import ru.orlovvv.weather.data.model.cache.LocationCache
import ru.orlovvv.weather.databinding.BottomSheetLocationsBinding
import ru.orlovvv.weather.viewmodels.ForecastViewModel

class BottomSheetLocations : BottomSheetDialogFragment(), LocationAdapter.LocationAdapterListener {

    private var _binding: BottomSheetLocationsBinding? = null
    val binding
        get() = _binding!!

    private val forecastViewModel: ForecastViewModel by activityViewModels()

    companion object {
        const val TAG = "BOTTOM_SHEET_LOCATIONS"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = BottomSheetLocationsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            lifecycleOwner = this@BottomSheetLocations
            foreViewModel = forecastViewModel
            rvSavedLocations.adapter = LocationAdapter(this@BottomSheetLocations, true)
            btnAddLocation.setOnClickListener {
                this@BottomSheetLocations.dismiss()
                findNavController().navigate(R.id.action_fragmentHomeContainer_to_fragmentSearch)
            }
        }
    }

    override fun onClick(cardView: View, location: LocationCache) {
        forecastViewModel.selectLocation(location)
        this.dismiss()
    }
}