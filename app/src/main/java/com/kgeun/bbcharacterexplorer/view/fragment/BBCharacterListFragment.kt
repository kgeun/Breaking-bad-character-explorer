package com.kgeun.bbcharacterexplorer.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.kgeun.bbcharacterexplorer.constants.BBConstants
import com.kgeun.bbcharacterexplorer.data.model.ui.BBSeasonItem
import com.kgeun.bbcharacterexplorer.data.persistance.BBMainDao
import com.kgeun.bbcharacterexplorer.databinding.FragmentCharacterListBinding
import com.kgeun.bbcharacterexplorer.view.BBBaseFragment
import com.kgeun.bbcharacterexplorer.view.adapter.BBCharacterAdapter
import com.kgeun.bbcharacterexplorer.view.adapter.BBSeasonAdapter
import com.kgeun.bbcharacterexplorer.viewmodel.BBMainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BBCharacterListFragment : BBBaseFragment() {
    private lateinit var binding: FragmentCharacterListBinding
    val mainViewModel: BBMainViewModel by viewModels()
    @Inject
    lateinit var mainDao: BBMainDao
    var callback = { item: BBSeasonItem ->
        mainViewModel.seasonLiveData.postValue(
            mainViewModel.seasonLiveData.value.also {
                it?.set(item.season, item)
            }
        )
    }

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

    override fun onPause() {
        super.onPause()
        mainViewModel.searchKeywordLiveData.postValue("")
        mainViewModel.seasonLiveData.value = BBConstants.seasonItems.also{ it.map { it.value.selected = false } }
        binding.seasonsList.invalidate()
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
                binding.seasonAdapter = BBSeasonAdapter(binding.root as ViewGroup, it, callback)
            }
        }
    }
}