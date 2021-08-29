package to.chip.dogsgallery

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CDGApplication : Application() {
    companion object {
        lateinit var instance: CDGApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this@CDGApplication
    }
}