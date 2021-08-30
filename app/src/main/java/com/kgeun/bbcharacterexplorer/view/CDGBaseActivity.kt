package com.kgeun.bbcharacterexplorer.view

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.AndroidEntryPoint
import com.kgeun.bbcharacterexplorer.analytics.CDGAnalytics
import com.kgeun.bbcharacterexplorer.utils.CDGUtils

@AndroidEntryPoint
open class CDGBaseActivity : AppCompatActivity() {

    var errorLiveData = MutableLiveData<(String?) -> Unit> {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOrientation()
        sendAnalytics()
        registerErrorHandler()
    }

    private fun registerErrorHandler() {
        errorLiveData.postValue (CDGUtils.errorHandler(this))
    }

    private fun setOrientation() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun sendAnalytics() {
        CDGAnalytics.sendView(javaClass.simpleName)
    }
}