package to.chip.dogsgallery.view.activity

import android.content.Intent
import android.os.Bundle
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import to.chip.dogsgallery.R
import to.chip.dogsgallery.databinding.ActivitySplashBinding
import to.chip.dogsgallery.utils.CDGAnimationUtils
import to.chip.dogsgallery.view.CDGBaseActivity

class CDGSplashActivity : CDGBaseActivity() {

    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        animateLogo()
    }

    fun animateLogo() {
        YoYo.with(Techniques.BounceIn)
            .withListener(CDGAnimationUtils.getFirstSplashListener(this, binding) { moveToMain() })
            .duration(CDGAnimationUtils.FIRST_SPLASH_DURATION)
            .playOn(binding.introAppLogoImg)
    }

    fun moveToMain() {
        finish()
        startActivity(Intent(this, CDGMainActivity::class.java))
        overridePendingTransition(R.anim.translate_fade_in, android.R.anim.fade_out)
    }
}