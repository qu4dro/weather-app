package ru.orlovvv.weather.ui

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.orlovvv.weather.R
import ru.orlovvv.weather.databinding.ActivityWeatherBinding
import ru.orlovvv.weather.utils.NetworkHelper
import ru.orlovvv.weather.workers.NetworkBroadcastReceiver
import timber.log.Timber
import java.lang.Exception
import android.content.Intent


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

    private var networkReceiver: NetworkBroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavController()
        setNetworkReceiver(makeNetworkSnack())
    }

    private fun setNavController() {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        binding.bottomNavMenu.apply {
            setupWithNavController(navController)
            setOnItemReselectedListener { }
        }
    }

    private fun setNetworkReceiver(snack: Snackbar) {
        networkReceiver = object : NetworkBroadcastReceiver() {
            override fun onNetworkChange() {
                val status = NetworkHelper(this@WeatherActivity).isNetworkConnected()
                if (!status) {
                    snack.show()
                } else {
                    snack.dismiss()
                }
            }
        }
    }

    private fun registerNetworkReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver, intentFilter)
    }

    private fun unregisterNetworkReceiver() {
        try {
            unregisterReceiver(networkReceiver)
        } catch (e: Exception) {
            Timber.d("${e.stackTrace}")
        }
    }

    private fun makeNetworkSnack(): Snackbar =
        Snackbar.make(
            findViewById(R.id.nav_host_fragment),
            R.string.check_connection,
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            anchorView = binding.bottomNavMenu
            setAction(R.string.settings) {
                startActivity(Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                this.dismiss()
            }
        }

    override fun onResume() {
        super.onResume()
        registerNetworkReceiver()
    }

    override fun onPause() {
        unregisterNetworkReceiver()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}