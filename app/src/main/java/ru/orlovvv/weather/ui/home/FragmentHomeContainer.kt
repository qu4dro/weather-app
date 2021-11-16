package ru.orlovvv.weather.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import ru.orlovvv.weather.R
import ru.orlovvv.weather.adapters.HomeSlidePagerAdapter
import ru.orlovvv.weather.databinding.FragmentHomeContainerBinding
import ru.orlovvv.weather.utils.Constants.HOME_PAGES
import ru.orlovvv.weather.utils.Resource
import ru.orlovvv.weather.viewmodels.ForecastViewModel
import timber.log.Timber

@AndroidEntryPoint
class FragmentHomeContainer : Fragment(R.layout.fragment_home_container) {

    private var _binding: FragmentHomeContainerBinding? = null
    val binding
        get() = _binding!!

    private val forecastViewModel: ForecastViewModel by activityViewModels()

    override fun onStart() {
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        binding.apply {
            lifecycleOwner = this@FragmentHomeContainer
            foreViewModel = forecastViewModel
            btnLocations.setOnClickListener { showDialog() }
            tvLocation.setOnClickListener { showDialog() }
            swipeRefresh.setOnRefreshListener {
                getUpdatesFromNetwork()
            }
        }
        setPager()
        setTabs()
    }

    private fun setupObservers() {

        forecastViewModel.selectedLocation.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                forecastViewModel.setSelectedLocationId(it.id)
                getUpdatesFromNetwork()
            }
        })

        forecastViewModel.forecast.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Loading -> {
                    binding.swipeRefresh.isRefreshing = true
                }
                is Resource.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                }
                is Resource.Success -> {
                    binding.swipeRefresh.isRefreshing = false
                    response.data?.let {
                        forecastViewModel.insertForecastCache()
                    }
                }
            }
        })

        forecastViewModel.forecastHistory.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Loading -> {
                    //binding.swipeRefresh.isRefreshing = true
                }
                is Resource.Error -> {
                    //binding.swipeRefresh.isRefreshing = false
                }
                is Resource.Success -> {
                    //binding.swipeRefresh.isRefreshing = false
                    response.data?.let {
                        forecastViewModel.insertHistoryCache()
                    }
                }
            }
        })
    }

    private fun setPager() {
        binding.pager.apply {
            offscreenPageLimit = HOME_PAGES as Int
            adapter = HomeSlidePagerAdapter(childFragmentManager, lifecycle)

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    binding.tabLayout.apply {
                        selectTab(getTabAt(position))
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    enableDisableSwipeRefresh(state == ViewPager.SCROLL_STATE_IDLE)
                }
            })
        }
    }

    private fun setTabs() {
        binding.tabLayout.apply {
            addTab(newTab().setText(R.string.today))
            addTab(newTab().setText(R.string.forecast))
            addTab(newTab().setText(R.string.history))

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    binding.pager.currentItem = tab.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }

    private fun enableDisableSwipeRefresh(enable: Boolean) {
        binding.swipeRefresh.isEnabled = enable
    }

    private fun showDialog() {
        val bottomSheet = BottomSheetLocations()
        bottomSheet.show(childFragmentManager, BottomSheetLocations.TAG)
    }

    private fun getUpdatesFromNetwork() {
        forecastViewModel.apply {
            getForecast()
            getForecastHistory()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}