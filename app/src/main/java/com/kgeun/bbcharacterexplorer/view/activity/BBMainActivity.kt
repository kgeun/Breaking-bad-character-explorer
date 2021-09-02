package com.kgeun.bbcharacterexplorer.view.activity

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.kgeun.bbcharacterexplorer.R
import com.kgeun.bbcharacterexplorer.databinding.ActivityMainBinding
import com.kgeun.bbcharacterexplorer.view.BBBaseActivity


class BBMainActivity : BBBaseActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = getColor(R.color.primary_teal_1)
    }
}