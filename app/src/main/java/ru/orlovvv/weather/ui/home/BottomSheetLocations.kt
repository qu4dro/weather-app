package ru.orlovvv.weather.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.orlovvv.weather.databinding.BottomSheetLocationsBinding

class BottomSheetLocations : BottomSheetDialogFragment() {

    private var _binding: BottomSheetLocationsBinding? = null
    val binding
        get() = _binding!!

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
}