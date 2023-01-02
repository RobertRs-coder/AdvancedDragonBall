package com.advanced.advanceddragonball.data.local

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

class LocalDataSource {

    private lateinit var dao: HeroDao

    fun initDatabase(context: Context) {
        val db = Room.databaseBuilder(
            context,
            HeroDatabase::class.java, "database-name"
        ).addCallback(object: RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.d("ROOM", "onCreate database")
            }
        })
            .build()

        dao = db.getDao()
    }
}