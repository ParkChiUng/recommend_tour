package com.recommend_tour.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.recommend_tour.R
import com.recommend_tour.databinding.ActivityMainBinding
import com.recommend_tour.viewmodels.TourListViewModel

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.widget.AdapterView
import android.widget.Spinner
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.recommend_tour.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: TourListViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    var selectedArea: String = "서울"
    var contentTypeApiCode: String = "12"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSpinner(binding.areaSpinner)
        initSpinner(binding.contentTypeSpinner)

        initViews()

        viewModel.adapter.onItemClick = { tourItem ->
            val bundle = Bundle().apply {
                putParcelable("tourItem", tourItem)
            }

            val navOptions = NavOptions.Builder().setPopUpTo(R.id.homeFragment, false).build()
            findNavController().navigate(R.id.tourDetailFragment, bundle, navOptions)
        }
    }

    private fun initViews() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = viewModel.adapter
    }

    private fun initSpinner(spinner: Spinner){
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                when (spinner) {
                    binding.areaSpinner -> selectedArea = spinner.selectedItem.toString()
                    binding.contentTypeSpinner -> contentTypeApiCode = resources.getStringArray(R.array.contentType_apiCode)[position]
                }

                Log.d("HomeFragment", "spinner Select !! ")
                viewModel.getTourData(selectedArea, contentTypeApiCode)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("HomeFragment", "spinner Nothing Select")
            }
        }
    }

    private val receiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent?.action == "tourDataReady"){
                viewModel.getTourData(selectedArea, contentTypeApiCode)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter("tourDataReady")
        requireActivity().registerReceiver(receiver, filter)
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(receiver)
    }
}