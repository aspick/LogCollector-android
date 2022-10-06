package com.ensemble_lab.logcollector

import org.junit.Test

import org.junit.Assert.*
import java.time.LocalDateTime
import java.util.*

class LogCollectorUnitTest {

    fun storeLog_invokeWorker(logSize: Int, invokeCount: Int, assertTimeout: Long) {
        var calledCount = 0
        val workerTask = { logs: List<LogData> ->
            calledCount++

            Unit
        }
        val logCollector = LogCollector(workerTask = workerTask, workerInterval = 2)

        for (i in 1..logSize) {
            val log = TestLogData(UUID.randomUUID().toString(), LocalDateTime.now().plusSeconds(i.toLong()), "Test Log ${i}")
            logCollector.storeLog(log)
        }

        assertEquals(0, calledCount)
        Thread.sleep(assertTimeout * 1000)
        assertEquals(invokeCount, calledCount)
    }

    @Test
    fun storeLog_storeBatchSizeLogsAndInvokeWorker(){
        storeLog_invokeWorker(5,1,3)
    }

    @Test
    fun storeLog_storeOverBatchsizeLogsAndInvokeWorker() {
        storeLog_invokeWorker(8,2,5)
    }

}