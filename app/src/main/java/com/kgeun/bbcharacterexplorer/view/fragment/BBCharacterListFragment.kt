package com.kgeun.bbcharacterexplorer.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import com.kgeun.bbcharacterexplorer.data.persistance.BBMainDao
import com.kgeun.bbcharacterexplorer.databinding.FragmentCharacterListBinding
import com.kgeun.bbcharacterexplorer.view.CDGBaseFragment
import com.kgeun.bbcharacterexplorer.view.adapter.CDGCharacterAdapter
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
        return binding.root
    }

    private fun subscribeUi() {
        mainViewModel.charactersList.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.adapter = CDGCharacterAdapter(binding.root as ViewGroup, it)
            }
        }
    }
}