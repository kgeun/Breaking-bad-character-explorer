package com.kgeun.bbcharacterexplorer.view

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.kgeun.bbcharacterexplorer.utils.BBUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BBBaseActivity : AppCompatActivity() {

    var errorLiveData = MutableLiveData<(String?) -> Unit> {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOrientation()
        registerErrorHandler()
    }

    private fun registerErrorHandler() {
        errorLiveData.postValue (BBUtils.errorHandler(this))
    }

    private fun setOrientation() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}