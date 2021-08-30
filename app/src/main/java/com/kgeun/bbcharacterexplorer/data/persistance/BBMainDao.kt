package com.kgeun.bbcharacterexplorer.data.persistance

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kgeun.bbcharacterexplorer.data.model.network.BBCharacter

@Dao
interface BBMainDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(breedItem: List<BBCharacter>)

    @Query("SELECT * FROM character ORDER BY char_id ASC")
    fun getCharactersList(): LiveData<List<BBCharacter>?>

    @Query("SELECT * FROM character ORDER BY char_id ASC")
    fun getCharactersListSync(): List<BBCharacter>?

    @Query("SELECT * FROM character WHERE name LIKE '%' || :keyword  || '%' ORDER BY char_id ASC")
    fun findCharactersListByKeyword(keyword: String): LiveData<List<BBCharacter>?>

    @Query("SELECT * FROM character WHERE name LIKE '%' || :keyword || '%' ORDER BY char_id ASC")
    fun findCharactersListByKeywordSync(keyword: String): List<BBCharacter>?

    @Query("SELECT * FROM character WHERE appearance LIKE '%' || :seasonList || '%' ORDER BY char_id ASC")
    fun findCharactersListBySeasonList(seasonList: String): LiveData<List<BBCharacter>?>

    @Query("SELECT * FROM character WHERE appearance LIKE '%' || :seasonList || '%' ORDER BY char_id ASC")
    fun findCharactersListBySeasonListSync(seasonList: String): List<BBCharacter>?

    @Query("SELECT * FROM character WHERE name LIKE :keyword AND appearance LIKE :seasonList ORDER BY char_id ASC")
    fun findCharactersListByKeywordAndSeasonList(keyword: String, seasonList: String): LiveData<List<BBCharacter>?>

    @Query("SELECT * FROM character WHERE name LIKE :keyword AND appearance LIKE :seasonList ORDER BY char_id ASC")
    fun findCharactersListByKeywordAndSeasonListSync(keyword: String, seasonList: String): List<BBCharacter>?

    @Query("DELETE FROM character")
    fun truncateCharacters()
}