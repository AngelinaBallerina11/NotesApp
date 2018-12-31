package com.angelinaandronova.notesapp.injection.module

import com.angelinaandronova.notesapp.data.cache.CacheDataSource
import com.angelinaandronova.notesapp.data.cache.CacheDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class CacheModule {

    @Binds
    abstract fun bindCache(cacheImpl: CacheDataSourceImpl): CacheDataSource
}