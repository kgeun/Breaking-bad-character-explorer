package com.kgeun.bbcharacterexplorer.constants

import com.kgeun.bbcharacterexplorer.data.model.ui.BBSeasonItem

object BBConstants {
    val ANIM_DURATION: Long = 800
    val SPLASH_LOGO_DURATION_1: Long = 500

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