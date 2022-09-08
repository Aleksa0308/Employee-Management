package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.R
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.fragments.AddEmployeeFragment
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.fragments.RecentlyAddedFragment

class TabPagerAdapter(
    fragmentManager: FragmentManager,

) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private const val ITEM_COUNT = 2
        const val FRAGMENT_1 = 0
        const val FRAGMENT_2 = 1
    }

    override fun getItem(position: Int): Fragment {
        val fragment: Fragment = when (position) {
            FRAGMENT_1 -> AddEmployeeFragment()
            FRAGMENT_2 -> RecentlyAddedFragment()
            else -> throw IllegalStateException("Unexpected value: $position")
        }
        return fragment
    }

    override fun getCount(): Int {
        return ITEM_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            FRAGMENT_1 -> "Add user"
            FRAGMENT_2 -> "Recently Added"
            else -> "error"
        }
    }



}