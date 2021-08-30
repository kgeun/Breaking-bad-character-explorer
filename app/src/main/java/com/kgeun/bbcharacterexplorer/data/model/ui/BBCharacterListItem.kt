package com.kgeun.bbcharacterexplorer.data.model.ui

import androidx.room.Entity
import androidx.room.PrimaryKey

data class BBCharacterListItem(
    var breedId: Int? = 0,
    var name: String? = "",
    var thumbnailUrl: String? = "",
    var thumbnailColor: Int? = 0
)