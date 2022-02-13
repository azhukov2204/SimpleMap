package ru.androidlearning.simplemap.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.androidlearning.simplemap.ui.fragment_map.MapFragment

object MapFragmentScreen : FragmentScreen {
    override fun createFragment(factory: FragmentFactory): Fragment =
        MapFragment.newInstance()
}
