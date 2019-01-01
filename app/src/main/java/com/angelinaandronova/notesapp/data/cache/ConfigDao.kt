package com.angelinaandronova.notesapp.data.cache

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.angelinaandronova.notesapp.model.Config


@Dao
interface ConfigDao {

    @Query("SELECT * FROM config")
    fun getConfig(): Config?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConfig(config: Config)
}