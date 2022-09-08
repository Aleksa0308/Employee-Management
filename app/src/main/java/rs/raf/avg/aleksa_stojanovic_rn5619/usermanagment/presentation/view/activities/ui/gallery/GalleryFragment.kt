package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.activities.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.R
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.databinding.FragmentGalleryBinding
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.adapters.TabPagerAdapter
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.fragments.WrapperFragment

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        init()

        return root
    }

    private fun init() {
        initInnerFragment()
    }

    private fun initInnerFragment() {
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.outerFragmentFcv, WrapperFragment())
        transaction.commit()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}