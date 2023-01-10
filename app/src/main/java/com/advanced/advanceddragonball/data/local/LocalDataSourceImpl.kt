package com.advanced.advanceddragonball.data.local

import com.advanced.advanceddragonball.data.local.model.HeroLocal
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val dao: HeroDao): LocalDataSource {

//    private lateinit var dao: HeroDao

//    override fun initDatabase(context: Context) {
//        val db = Room.databaseBuilder(
//            context,
//            HeroDatabase::class.java, "database-name"
//        ).build()
//
//        db.getDao()
//    }

    override fun getHeroes(): List<HeroLocal> {
        return dao.getAllHeroes()
    }

    override suspend fun insertHeroes(remoteHeroes: List<HeroLocal>) {
         dao.insertAll(remoteHeroes)
    }
}

