package to.chip.dogsgallery.view.activity

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import to.chip.dogsgallery.R
import to.chip.dogsgallery.databinding.ActivityMainBinding
import to.chip.dogsgallery.view.CDGBaseActivity


class CDGMainActivity : CDGBaseActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(getColor(R.color.primary_teal_1))
    }
}