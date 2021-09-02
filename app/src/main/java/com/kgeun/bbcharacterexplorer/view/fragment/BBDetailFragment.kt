package com.kgeun.bbcharacterexplorer.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kgeun.bbcharacterexplorer.analytics.CDGAnalytics
import com.kgeun.bbcharacterexplorer.data.persistance.BBMainDao
import com.kgeun.bbcharacterexplorer.databinding.FragmentDetailBinding
import com.kgeun.bbcharacterexplorer.view.CDGBaseFragment
import com.kgeun.bbcharacterexplorer.viewmodel.BBMainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BBDetailFragment : CDGBaseFragment() {
    private lateinit var binding: FragmentDetailBinding
    val mainViewModel: BBMainViewModel by viewModels()
    var charId: Long = 1L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        charId = BBCharacterListFragmentArgs.fromBundle(requireArguments()).charId

        subscribeUi()
        setListener()

        return binding.root
    }

    private fun subscribeUi() {
        mainViewModel.getCharacterByCharId(charId).observe(viewLifecycleOwner) {
            if (it != null) {
                binding.character = it
            }
        }
    }

    private fun setListener() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
            CDGAnalytics.sendClick("ClickBack_${javaClass.simpleName}")
        }
    }
}