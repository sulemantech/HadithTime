package com.hadithtime.model
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Hadith::class], version = 5, exportSchema = false)
abstract class HadithDatabase : RoomDatabase() {
    abstract fun duaDao(): HadithDao

    companion object {
        @Volatile
        private var INSTANCE: HadithDatabase? = null

        fun getDatabase(context: Context): HadithDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HadithDatabase::class.java,
                    "duas_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
