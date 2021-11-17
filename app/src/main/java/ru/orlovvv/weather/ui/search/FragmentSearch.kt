package ru.orlovvv.weather.ui.search

import android.Manifest
import android.content.Context
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.location.LocationManagerCompat.requestLocationUpdates
import androidx.core.view.doOnPreDraw
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import ru.orlovvv.weather.R
import ru.orlovvv.weather.adapters.LocationAdapter
import ru.orlovvv.weather.data.model.cache.LocationCache
import ru.orlovvv.weather.data.model.other.Coordinates
import ru.orlovvv.weather.databinding.FragmentSearchBinding
import ru.orlovvv.weather.utils.Constants
import ru.orlovvv.weather.utils.LocationUtility
import ru.orlovvv.weather.utils.Resource
import ru.orlovvv.weather.viewmodels.CoordinatesViewModel
import ru.orlovvv.weather.viewmodels.ForecastViewModel
import timber.log.Timber

@AndroidEntryPoint
class FragmentSearch : Fragment(R.layout.fragment_search), LocationAdapter.LocationAdapterListener,
    EasyPermissions.PermissionCallbacks {

    private var _binding: FragmentSearchBinding? = null
    val binding
        get() = _binding!!

    private val forecastViewModel: ForecastViewModel by activityViewModels()
    private val coordinatesViewModel: CoordinatesViewModel by activityViewModels()
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(
            MaterialSharedAxis.X,
            /* forward= */ true
        ).apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
        returnTransition = MaterialSharedAxis(
            MaterialSharedAxis.X,
            /* forward= */ false
        ).apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
    }

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
            rvFoundedLocations.adapter = LocationAdapter(this@FragmentSearch, isSaved = false)
            etSearch.addTextChangedListener {
                if (it.toString().isNotEmpty()) {
                    forecastViewModel.setSearchQuery(it.toString())
                }
            }
            ibBack.setOnClickListener { findNavController().navigateUp() }
            btnMyLocation.setOnClickListener {
                checkGpsEnabled()
                requestPermissions()
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

    private fun checkGpsEnabled() {
        val lm: LocationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        try {
            coordinatesViewModel.isGpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (ex: Exception) {

        }

        if (!coordinatesViewModel.isGpsEnabled) {
            Timber.d("123123123123123123")
        }
    }

    private fun requestPermissions() {
        if (LocationUtility.hasLocationPermissions(requireContext())) {
            Timber.d("LOCATION UPDATES")
            requestLocationUpdates()
            return
        } else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                EasyPermissions.requestPermissions(
                    this,
                    "You need to accept location permissions",
                    Constants.REQUEST_CODE_LOCATION_PERMISSION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } else {
                EasyPermissions.requestPermissions(
                    this,
                    "You need to accept location permissions",
                    Constants.REQUEST_CODE_LOCATION_PERMISSION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                    //place for code for background location
                )
            }
        }
    }

    private fun requestLocationUpdates() {
        coordinatesViewModel.locationLiveData.apply {
            observe(viewLifecycleOwner, object : Observer<Coordinates> {

                override fun onChanged(t: Coordinates?) {
                    value?.let {
                        Timber.d(it.toString())
                        binding.etSearch.setText(
                            LocationUtility.getCityString(
                                it,
                                requireContext()
                            )
                        )
                    }
                    removeObserver(this)
                }
            })
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        requestLocationUpdates()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(cardView: View, location: LocationCache) {
        forecastViewModel.insertLocation(location)
        findNavController().navigateUp()
        Toast.makeText(
            requireContext(),
            requireContext().getString(R.string.new_location, location.name),
            Toast.LENGTH_SHORT
        ).show()
    }


}