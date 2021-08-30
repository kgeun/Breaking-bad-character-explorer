package com.kgeun.bbcharacterexplorer.data.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kgeun.bbcharacterexplorer.data.model.network.BBCharacter
import com.kgeun.bbcharacterexplorer.data.model.ui.BBCharacterListItem
import com.kgeun.bbcharacterexplorer.data.model.ui.CDGImageItem
import com.kgeun.bbcharacterexplorer.utils.BBTypeConverter

@Database(
    entities = [BBCharacter::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(BBTypeConverter::class)
abstract class BBAppDatabase : RoomDatabase() {
    abstract fun BBMainDao(): BBMainDao
}