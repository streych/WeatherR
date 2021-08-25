package com.example.weatherr.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherr.R
import com.example.weatherr.databinding.HistoryFragmentBinding
import com.example.weatherr.ui.main.adapter.HistoryAdapter
import com.example.weatherr.viewmodel.Apstate
import com.example.weatherr.viewmodel.HistoryViewModel
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class HistoryFragment : Fragment() {
    private var _binding: HistoryFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryViewModel by lazy {
        ViewModelProvider(this).get(HistoryViewModel::class.java)
    }

    private val adapter: HistoryAdapter by lazy {
        HistoryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.historyFragmentRecyclerview.adapter = adapter
        viewModel.historyLiveData.observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getAllHistory()
    }

    private fun renderData(apstate: Apstate) {
        when (apstate) {
            is Apstate.Succes -> {
                binding.historyFragmentRecyclerview.visibility = View.VISIBLE
                binding.includeLoadingLayout.loadingLayout.visibility = View.GONE
                adapter.setData(apstate.weatherData)
            }
            is Apstate.Loading -> {
                binding.includeLoadingLayout.loadingLayout.visibility = View.GONE
                binding.historyFragmentRecyclerview.visibility = View.VISIBLE
            }
            is Apstate.Error -> {
                binding.includeLoadingLayout.loadingLayout.visibility = View.GONE
                binding.historyFragmentRecyclerview.visibility = View.VISIBLE
                binding.historyFragmentRecyclerview.showSnakeBar(getString(R.string.error),
                    getString(R.string.reload)
                ) { viewModel.getAllHistory() }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
}