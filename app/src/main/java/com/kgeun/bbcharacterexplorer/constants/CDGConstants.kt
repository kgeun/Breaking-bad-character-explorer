package com.kgeun.bbcharacterexplorer.constants

import com.kgeun.bbcharacterexplorer.BBApplication
import com.kgeun.bbcharacterexplorer.R
import com.kgeun.bbcharacterexplorer.data.model.ui.BBSeasonItem

object CDGConstants {
    val ANIM_DURATION: Long = 800
    val SPLASH_LOGO_DURATION_1: Long = 500
    const val MAX_SHOWING_IMAGE: Int = 10
    val SERVER_SUCCESS = BBApplication.instance.applicationContext.getString(R.string.server_success)

    val seasonItems = hashMapOf<Int, BBSeasonItem>(
        1 to BBSeasonItem(
            "Season 1",
            1,
            false
        ),
        2 to BBSeasonItem(
            "Season 2",
            2,
            false
        ),
        3 to BBSeasonItem(
            "Season 3",
            3,
            false
        ),
        4 to BBSeasonItem(
            "Season 4",
            4,
            false
        ),
        5 to BBSeasonItem(
            "Season 5",
            5,
            false
        )
    )
}