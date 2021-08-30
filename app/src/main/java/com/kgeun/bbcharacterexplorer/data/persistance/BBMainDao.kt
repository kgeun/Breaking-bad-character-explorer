package com.kgeun.bbcharacterexplorer.data.persistance

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kgeun.bbcharacterexplorer.constants.CDGConstants
import com.kgeun.bbcharacterexplorer.data.model.network.BBCharacter
import com.kgeun.bbcharacterexplorer.data.model.ui.BBCharacterListItem
import com.kgeun.bbcharacterexplorer.data.model.ui.CDGImageItem

@Dao
interface BBMainDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(breedItem: List<BBCharacter>)

    @Query("SELECT * FROM character ORDER BY char_id ASC")
    fun getCharactersList(): LiveData<List<BBCharacter>?>

    @Query("DELETE FROM character")
    fun truncateCharacters()
}