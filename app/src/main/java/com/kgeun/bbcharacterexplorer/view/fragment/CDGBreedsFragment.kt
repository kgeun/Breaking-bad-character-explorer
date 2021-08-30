package com.kgeun.bbcharacterexplorer.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import com.kgeun.bbcharacterexplorer.data.persistance.BBMainDao
import com.kgeun.bbcharacterexplorer.databinding.FragmentBreedsBinding
import com.kgeun.bbcharacterexplorer.view.CDGBaseFragment
import com.kgeun.bbcharacterexplorer.view.adapter.CDGCharacterAdapter
import com.kgeun.bbcharacterexplorer.viewmodel.CDGMainViewModel
import javax.inject.Inject

@AndroidEntryPoint
class CDGBreedsFragment : CDGBaseFragment() {
    private lateinit var binding: FragmentBreedsBinding
    val mainViewModel: CDGMainViewModel by viewModels()
    @Inject
    lateinit var mainDao: BBMainDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentBreedsBinding.inflate(inflater, container, false)

        subscribeUi()
        return binding.root
    }

    private fun subscribeUi() {
        mainViewModel.breedsList.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                binding.adapter = CDGCharacterAdapter(binding.root as ViewGroup, it)
            }
        }

        mainViewModel.totalCount.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.count = it
            }
        }
    }
}