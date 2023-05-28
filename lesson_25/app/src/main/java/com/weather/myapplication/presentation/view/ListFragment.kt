package com.weather.myapplication.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weather.myapplication.R
import com.weather.myapplication.databinding.FragmentListBinding
import com.weather.myapplication.presentation.view.adapter.CustomDuffCallback
import com.weather.myapplication.presentation.view.adapter.ListWeatherAdapter
import com.weather.myapplication.presentation.viewmodel.ListViewModel
import com.weather.myapplication.presentation.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private var adapter: ListWeatherAdapter? = null
    private val viewModel: ListViewModel by viewModels()
    private val activityViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observeViewModelState()
        activityViewModel.updateIsVisibleSearchAction(true)
        viewModel.getAllCity()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initList() {
        adapter = ListWeatherAdapter { name, lat, lon, link ->
            findNavController()
                .navigate(
                    R.id.action_listFragment_to_infoFragment,
                    bundleOf(
                        Pair(InfoFragment.KEY_NAME_CITY, name),
                        Pair(InfoFragment.KEY_LAT, lat),
                        Pair(InfoFragment.KEY_LON, lon),
                        Pair(InfoFragment.KEY_IMAGE_LINK, link)
                    )
                )
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }

    private fun observeViewModelState() {
        viewModel.cityListLiveData.observe(viewLifecycleOwner) {
            // Использование DiffUtil
            val customDuffCallback = CustomDuffCallback(adapter?.cityList.orEmpty(), it)
            val diffResult = DiffUtil.calculateDiff(customDuffCallback)

            adapter?.let { adapter ->
                adapter.updateCity(it)
                diffResult.dispatchUpdatesTo(adapter)
            }
        }
    }
}
