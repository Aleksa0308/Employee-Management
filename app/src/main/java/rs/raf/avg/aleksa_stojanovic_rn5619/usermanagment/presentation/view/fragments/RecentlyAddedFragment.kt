package rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.R
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.databinding.FragmentRecentlyaddedBinding
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.contract.RecentlyContract
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.recycler.adapter.RecentlyAdapter
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.view.states.RecentlyState
import rs.raf.avg.aleksa_stojanovic_rn5619.usermanagment.presentation.viewmodel.RecentlyViewModel
import timber.log.Timber

class RecentlyAddedFragment : Fragment(R.layout.fragment_recentlyadded) {

    private val recentlyViewModel: RecentlyContract.ViewModel by sharedViewModel<RecentlyViewModel>()

    private var _binding: FragmentRecentlyaddedBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: RecentlyAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecentlyaddedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun initUi() {
        initRecycler()
        binding.refreshBtn.setOnClickListener(){
            recentlyViewModel.getLast2Minutes()
        }
    }

    private fun init() {
        initUi()
        initObservers()
    }

    private fun initRecycler() {
        binding.recentlyRv.layoutManager = LinearLayoutManager(context)
        adapter = RecentlyAdapter()
        binding.recentlyRv.adapter = adapter
    }

    private fun initObservers() {
        recentlyViewModel.recentlyState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)

        })
        // Pravimo subscription kad observablu koji je vezan za getAll iz baze
        // Na svaku promenu tabele, obserrvable ce emitovati na onNext sve elemente
        // koji zadovoljavaju query
        recentlyViewModel.getLast2Minutes()


    }

    private fun renderState(state: RecentlyState) {
        when (state) {
            is RecentlyState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.recentlys)
            }
            is RecentlyState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is RecentlyState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is RecentlyState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {

        binding.recentlyRv.isVisible = !loading
        binding.progressBar.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}