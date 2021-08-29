package to.chip.dogsgallery.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import to.chip.dogsgallery.R
import to.chip.dogsgallery.analytics.CDGAnalytics
import to.chip.dogsgallery.data.persistance.CDGMainDao
import to.chip.dogsgallery.databinding.FragmentGalleryBinding
import to.chip.dogsgallery.utils.CDGUtils
import to.chip.dogsgallery.view.CDGBaseFragment
import to.chip.dogsgallery.view.adapter.CDGGalleryAdapter
import to.chip.dogsgallery.viewmodel.CDGMainViewModel
import javax.inject.Inject

@AndroidEntryPoint
class CDGGalleryFragment : CDGBaseFragment() {
    private lateinit var binding: FragmentGalleryBinding
    val mainViewModel: CDGMainViewModel by viewModels()
    @Inject
    lateinit var mainDao: CDGMainDao
    var breedName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentGalleryBinding.inflate(inflater, container, false)

        setListener()
        subscribeUi()

        return binding.root
    }

    private fun subscribeUi() {
        mainViewModel.getImageList(breedName).observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                try {
                    mainViewModel.viewModelScope.launch {
                        mainViewModel.loadImageList(
                            breedName,
                            CDGUtils.errorHandler(requireContext())
                        )
                    }
                } catch (e: retrofit2.HttpException) {
                    Toast.makeText(requireContext(),
                        R.string.communication_error,
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(requireContext(),
                        R.string.unknown_error,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            binding.adapter = CDGGalleryAdapter(binding.root as ViewGroup, it)
        }
    }

    private fun setListener() {
        breedName = CDGBreedsFragmentArgs.fromBundle(requireArguments()).breedName

        binding.breedsName = breedName

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
            CDGAnalytics.sendClick("ClickBack_${javaClass.simpleName}")
        }
    }

    fun refreshImageList() {
        try {
            mainViewModel.viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    mainDao.deleteImageList(breedName)
                }
                mainViewModel.loadImageList(breedName, CDGUtils.errorHandler(requireContext()))
            }
        } catch (e: retrofit2.HttpException) {
            e.printStackTrace()
        }
    }
}