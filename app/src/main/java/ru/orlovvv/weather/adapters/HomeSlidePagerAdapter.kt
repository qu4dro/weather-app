package ru.orlovvv.weather.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.orlovvv.weather.ui.home.FragmentForecast
import ru.orlovvv.weather.ui.home.FragmentHistory
import ru.orlovvv.weather.ui.home.FragmentToday
import ru.orlovvv.weather.utils.Constants.HOME_PAGES

class HomeSlidePagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = HOME_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> FragmentForecast()
            2 -> FragmentHistory()
            else -> FragmentToday()
        }
    }

}