package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.R
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.adapters.TabPagerAdapter

class WrapperFragment : Fragment(R.layout.fragment_wrapper) {

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNavigation(view)
    }

    private fun initNavigation(view: View) {
        viewPager = view.findViewById<View>(R.id.viewPager) as ViewPager
        tabLayout = view.findViewById<View>(R.id.tabLayout) as TabLayout

        viewPager.adapter = TabPagerAdapter(requireActivity().supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }
}