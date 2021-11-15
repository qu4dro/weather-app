package ru.orlovvv.weather.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.orlovvv.weather.R
import ru.orlovvv.weather.adapters.LocationAdapter
import ru.orlovvv.weather.databinding.FragmentSearchBinding
import ru.orlovvv.weather.utils.Resource
import ru.orlovvv.weather.viewmodels.ForecastViewModel
import timber.log.Timber

@AndroidEntryPoint
class FragmentSearch : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    val binding
        get() = _binding!!

    private val forecastViewModel: ForecastViewModel by activityViewModels()
    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        binding.apply {
            lifecycleOwner = this@FragmentSearch
            foreViewModel = forecastViewModel
            rvFoundedLocations.adapter = LocationAdapter(isSaved = false)
            etSearch.addTextChangedListener {
                if(it.toString().isNotEmpty()) {
                    forecastViewModel.setSearchQuery(it.toString())
                }
            }
        }
    }

    private fun setupObservers() {
        forecastViewModel.apply {
            searchQuery.observe(viewLifecycleOwner, Observer {
                findLocation(it)
            })
        }

        forecastViewModel.foundedLocations.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Loading -> {
                    //binding.swipeRefresh.isRefreshing = true
                }
                is Resource.Error -> {
                    Timber.d(response.message.toString())
                    //binding.swipeRefresh.isRefreshing = false
                }
                is Resource.Success -> {
                    //binding.swipeRefresh.isRefreshing = false
                    response.data?.let {
                        Timber.d(it.toString())
                    }
                }
            }
        })

    }

    private fun findLocation(searchQuery: String) {
        job?.cancel()
        if (searchQuery.isEmpty()) {
            forecastViewModel.searchLocation(searchQuery)
        } else {
            job = MainScope().launch {
                delay(500L)
                forecastViewModel.searchLocation(searchQuery)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}