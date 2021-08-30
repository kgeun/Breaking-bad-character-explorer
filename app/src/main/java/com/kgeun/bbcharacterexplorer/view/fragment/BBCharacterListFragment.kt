package com.kgeun.bbcharacterexplorer.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.kgeun.bbcharacterexplorer.constants.CDGConstants
import dagger.hilt.android.AndroidEntryPoint
import com.kgeun.bbcharacterexplorer.data.persistance.BBMainDao
import com.kgeun.bbcharacterexplorer.databinding.FragmentCharacterListBinding
import com.kgeun.bbcharacterexplorer.view.CDGBaseFragment
import com.kgeun.bbcharacterexplorer.view.adapter.BBCharacterAdapter
import com.kgeun.bbcharacterexplorer.view.adapter.BBSeasonAdapter
import com.kgeun.bbcharacterexplorer.viewmodel.CDGMainViewModel
import javax.inject.Inject

@AndroidEntryPoint
class BBCharacterListFragment : CDGBaseFragment() {
    private lateinit var binding: FragmentCharacterListBinding
    val mainViewModel: CDGMainViewModel by viewModels()
    @Inject
    lateinit var mainDao: BBMainDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCharacterListBinding.inflate(inflater, container, false)

        subscribeUi()
        bindUi()
        return binding.root
    }

    private fun bindUi() {
        binding.viewModel = mainViewModel
    }

    private fun subscribeUi() {
        mainViewModel.charactersLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.characterAdapter = BBCharacterAdapter(binding.root as ViewGroup, it)
            }
        }

        mainViewModel.seasonLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.seasonAdapter = BBSeasonAdapter(binding.root as ViewGroup, it) { item ->
                    Log.i("kglee", "season : ${item.season} / item : ${item.selected}")
                    mainViewModel.seasonLiveData.value?.set(item.season, item)
                }
            }
        }
    }
}