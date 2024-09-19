package com.sample.rickandmorty.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.rickandmorty.data.source.local.dao.CharacterDao
import com.sample.rickandmorty.data.source.local.entity.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

}
