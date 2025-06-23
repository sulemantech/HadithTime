package com.hadithtime.model

import androidx.room.*
import com.hadithtime.model.Dua
import kotlinx.coroutines.flow.Flow

// DuaDao.kt
@Dao
interface DuaDao {

    @Query("SELECT * FROM duas")
    fun getAllDuas(): Flow<List<Dua>>

    @Query("SELECT * FROM duas WHERE level = :level ORDER BY id ASC")
    fun getDuasByLevel(level: Int): Flow<List<Dua>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(duas: List<Dua>)

    @Query("DELETE FROM duas")
    suspend fun deleteAll()
}
