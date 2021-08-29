package to.chip.dogsgallery.constants

import to.chip.dogsgallery.CDGApplication
import to.chip.dogsgallery.R

object CDGConstants {
    val ANIM_DURATION: Long = 800
    val SPLASH_LOGO_DURATION_1: Long = 500
    const val MAX_SHOWING_IMAGE: Int = 10
    val SERVER_SUCCESS = CDGApplication.instance.applicationContext.getString(R.string.server_success)
}