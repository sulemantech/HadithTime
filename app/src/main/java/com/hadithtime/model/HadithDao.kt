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

    @Query("DELETE FROM duas")
    suspend fun deleteAll()
}
