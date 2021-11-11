package ru.orlovvv.weather.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.orlovvv.weather.R
import ru.orlovvv.weather.adapters.DailyForecastAdapter
import ru.orlovvv.weather.databinding.FragmentHistoryBinding
import ru.orlovvv.weather.viewmodels.ForecastViewModel

@AndroidEntryPoint
class FragmentHistory : Fragment(R.layout.fragment_history) {

    private var _binding: FragmentHistoryBinding? = null
    val binding
        get() = _binding!!

    private val forecastViewModel: ForecastViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            foreViewModel = forecastViewModel
            lifecycleOwner = this@FragmentHistory
            rvDailyForecast.adapter = DailyForecastAdapter()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}