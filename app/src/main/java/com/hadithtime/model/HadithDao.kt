package com.hadithtime.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HadithDao {

    @Query("SELECT * FROM duas")
    fun getAllDuas(): Flow<List<Hadith>>

    @Update
    suspend fun updateDua(dua: Hadith)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(duas: List<Hadith>)

    @Query("DELETE FROM duas")
    suspend fun deleteAll()

    @Query("SELECT * FROM duas WHERE id = :id")
    fun getDuaById(id: Int): Flow<Hadith>

    @Query("SELECT * FROM duas WHERE level IN (:levels)")
    fun getDuasByLevels(levels: List<Int>): Flow<List<Hadith>>
}

