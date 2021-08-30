package com.kgeun.bbcharacterexplorer.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import com.kgeun.bbcharacterexplorer.analytics.CDGAnalytics
import com.kgeun.bbcharacterexplorer.data.persistance.BBMainDao
import com.kgeun.bbcharacterexplorer.databinding.FragmentDetailBinding
import com.kgeun.bbcharacterexplorer.view.CDGBaseFragment
import com.kgeun.bbcharacterexplorer.viewmodel.CDGMainViewModel
import javax.inject.Inject

@AndroidEntryPoint
class BBDetailFragment : CDGBaseFragment() {
    private lateinit var binding: FragmentDetailBinding
    val mainViewModel: CDGMainViewModel by viewModels()
    @Inject
    lateinit var mainDao: BBMainDao
    var breedName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        setListener()
        subscribeUi()

        return binding.root
    }

    private fun subscribeUi() {
//        mainViewModel.getImageList(breedName).observe(viewLifecycleOwner) {
//            if (it.isNullOrEmpty()) {
//                try {
//                    mainViewModel.viewModelScope.launch {
//                        mainViewModel.loadImageList(
//                            breedName,
//                            CDGUtils.errorHandler(requireContext())
//                        )
//                    }
//                } catch (e: retrofit2.HttpException) {
//                    Toast.makeText(requireContext(),
//                        R.string.communication_error,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                } catch (e: Exception) {
//                    Toast.makeText(requireContext(),
//                        R.string.unknown_error,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//            binding.adapter = CDGGalleryAdapter(binding.root as ViewGroup, it)
//        }
    }

    private fun setListener() {
//        breedName = CDGBreedsFragmentArgs.fromBundle(requireArguments()).breedName

        binding.breedsName = breedName

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
            CDGAnalytics.sendClick("ClickBack_${javaClass.simpleName}")
        }
    }

    fun refreshImageList() {
        try {
//            mainViewModel.viewModelScope.launch {
//                withContext(Dispatchers.IO) {
//                    mainDao.deleteImageList(breedName)
//                }
//                mainViewModel.loadImageList(breedName, CDGUtils.errorHandler(requireContext()))
//            }
        } catch (e: retrofit2.HttpException) {
            e.printStackTrace()
        }
    }
}