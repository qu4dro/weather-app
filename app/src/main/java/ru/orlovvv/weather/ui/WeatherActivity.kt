package ru.orlovvv.weather.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.orlovvv.weather.R
import ru.orlovvv.weather.databinding.ActivityWeatherBinding

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {

    private var _binding: ActivityWeatherBinding? = null
    val binding
        get() = _binding!!

    val currentNavigationFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            ?.childFragmentManager
            ?.fragments
            ?.first()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavController()
    }

    private fun setNavController() {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        binding.bottomNavMenu.apply {
            setupWithNavController(navController)
            setOnItemReselectedListener {  }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}