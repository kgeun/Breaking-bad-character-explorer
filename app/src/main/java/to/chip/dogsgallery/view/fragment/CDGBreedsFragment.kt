package to.chip.dogsgallery.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import to.chip.dogsgallery.data.persistance.CDGMainDao
import to.chip.dogsgallery.databinding.FragmentBreedsBinding
import to.chip.dogsgallery.view.CDGBaseFragment
import to.chip.dogsgallery.view.adapter.CDGBreedsAdapter
import to.chip.dogsgallery.viewmodel.CDGMainViewModel
import javax.inject.Inject

@AndroidEntryPoint
class CDGBreedsFragment : CDGBaseFragment() {
    private lateinit var binding: FragmentBreedsBinding
    val mainViewModel: CDGMainViewModel by viewModels()
    @Inject
    lateinit var mainDao: CDGMainDao

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
                binding.adapter = CDGBreedsAdapter(binding.root as ViewGroup, it)
            }
        }

        mainViewModel.totalCount.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.count = it
            }
        }
    }
}