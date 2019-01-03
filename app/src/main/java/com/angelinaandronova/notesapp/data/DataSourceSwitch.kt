package com.angelinaandronova.notesapp.data

import com.angelinaandronova.notesapp.data.cache.CacheDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class DataSourceSwitchImpl @Inject constructor(private val cache: CacheDataSource) : DataSourceSwitch {

    private val expirationInterval = 60 * 10 * 1000L /* 10 mins */

    override fun isCacheExpired(): Boolean {
        var delta = 0L
        runBlocking(Dispatchers.IO) {
            val currentTime = System.currentTimeMillis()
            val lastCacheTime = async { cache.getLastCacheTime() }
            delta = currentTime - lastCacheTime.await()
        }
        Timber.d("delta: $delta")
        return delta > expirationInterval
    }

    override fun isOnline(): Boolean {
        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
            val exitValue = ipProcess.waitFor()
            return exitValue == 0
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return false
    }
}

interface DataSourceSwitch {
    fun isOnline(): Boolean
    fun isCacheExpired(): Boolean
}