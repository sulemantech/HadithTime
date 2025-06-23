package com.hadithtime.model
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hadithtime.model.Dua

@Database(entities = [Dua::class], version = 1, exportSchema = false)
abstract class DuaDatabase : RoomDatabase() {
    abstract fun duaDao(): DuaDao

    companion object {
        @Volatile
        private var INSTANCE: DuaDatabase? = null

        fun getDatabase(context: Context): DuaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DuaDatabase::class.java,
                    "duas_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
