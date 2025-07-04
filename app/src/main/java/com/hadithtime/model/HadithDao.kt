package com.hadithtime.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// DuaDao.kt
@Dao
interface HadithDao {

    @Query("SELECT * FROM duas")
    fun getAllDuas(): Flow<List<Hadith>>

    @Query("SELECT * FROM duas WHERE level = :level ORDER BY id ASC")
    fun getDuasByLevel(level: Int): Flow<List<Hadith>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(duas: List<Hadith>)

    @Query("SELECT * FROM duas WHERE isFavorite = 1")
    fun getFavoriteDuas(): Flow<List<Hadith>>

    @Query("UPDATE duas SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean)

    @Update
    suspend fun updateDua(dua: Hadith)

    @Query("DELETE FROM duas")
    suspend fun deleteAll()

    @Query("SELECT * FROM duas WHERE level IN (:levels)")
    fun getDuasByLevels(levels: List<Int>): Flow<List<Hadith>>


}
