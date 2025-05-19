package com.example.stocksapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.stocksapp.data.local.entities.*

@Database(
    entities = [
        CachedTopGainersLosers::class,
        CachedCompanyOverview::class,
        CachedTickerSearch::class
    ],
    version = 1
)
abstract class StockDatabase : RoomDatabase() {
    abstract fun stockDao(): StockDAO

    companion object {
        @Volatile
        private var INSTANCE: StockDatabase? = null

        fun getDatabase(context: Context): StockDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StockDatabase::class.java,
                    "stock_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                    INSTANCE = instance
                    instance
            }
        }

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `recent_searches` (
                        `query` TEXT NOT NULL, 
                        `timestamp` INTEGER NOT NULL, 
                        PRIMARY KEY(`query`)
                    )
                """)
            }
        }
    }
}