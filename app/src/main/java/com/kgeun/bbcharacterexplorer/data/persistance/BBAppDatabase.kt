package com.kgeun.bbcharacterexplorer.data.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kgeun.bbcharacterexplorer.data.model.ui.BBCharacterListItem
import com.kgeun.bbcharacterexplorer.data.model.ui.CDGImageItem

@Database(
    entities = [BBCharacterListItem::class],
    version = 1,
    exportSchema = false
)
abstract class BBAppDatabase : RoomDatabase() {
    abstract fun BBMainDao(): BBMainDao
}