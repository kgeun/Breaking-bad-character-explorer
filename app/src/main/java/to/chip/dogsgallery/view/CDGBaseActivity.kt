package to.chip.dogsgallery.view

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.AndroidEntryPoint
import to.chip.dogsgallery.analytics.CDGAnalytics
import to.chip.dogsgallery.utils.CDGUtils

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