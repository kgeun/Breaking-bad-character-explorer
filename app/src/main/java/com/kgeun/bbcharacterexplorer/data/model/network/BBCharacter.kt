package com.kgeun.bbcharacterexplorer.data.model.network

import androidx.room.Entity

@Entity(tableName = "character")
data class BBCharacter(
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