package com.kgeun.bbcharacterexplorer.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.kgeun.bbcharacterexplorer.R
import com.kgeun.bbcharacterexplorer.databinding.ActivitySplashBinding
import com.kgeun.bbcharacterexplorer.view.CDGBaseActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BBSplashActivity : CDGBaseActivity() {

    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        lifecycleScope.launch {
            delay(1000L)
            moveToMain()
        }
    }

    fun moveToMain() {
        finish()
        startActivity(Intent(this, BBMainActivity::class.java))
        overridePendingTransition(R.anim.translate_fade_in, android.R.anim.fade_out)
    }
}