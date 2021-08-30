package com.kgeun.bbcharacterexplorer.data.model.network

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class BBCharacter(
    @PrimaryKey
    val char_id: Long = 0L,
    val name: String = "",
    val birthday: String = "",
    val occupation: List<String> = listOf(),
    val img: String = "",
    val status: String = "",
    val nickname: String = "",
    val appearance: List<Int> = listOf(),
    val portrayed: String = "",
    val category: String,
    val better_call_saul_appearance: List<Int> = listOf()
)