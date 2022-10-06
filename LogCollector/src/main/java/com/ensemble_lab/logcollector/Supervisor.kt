package com.ensemble_lab.logcollector

import java.util.*
import kotlin.concurrent.schedule

/**
 * Supervisor class of worker task
 *
 * @args buffer Buffer class instance
 * @args workerTask worker task function
 * @args workerInterval worker invoke interval seconds
 */
class Supervisor(val buffer: Buffer, val workerTask: (List<LogData>) -> Unit, val workerInterval: Long = 60) {
    private var timer: TimerTask? = null

    /**
     * Start timer to invoke worker periodically
     */
    fun startWorker() {
        if (timer != null) { return }
        timer = Timer().schedule(workerInterval * 1000, workerInterval * 1000) {
            invokeTask()
            stopWorkerIfNeeded()
        }
    }

    /**
     * Stop timer and invoke worker finally
     */
    fun stopWorker() {
        invokeTask()
        if (timer == null) { return }

        timer!!.cancel()
        timer = null
    }

    /**
     * Invoke worker task
     * Pop log data from buffer and pass them to worker task
     * If worker task failed, re-push popped log data to restore initial state
     */
    fun invokeTask() {
        val logs = buffer.popLogs()
        if (logs.isEmpty()) {
            return
        }

        try {
            workerTask(logs)
        } catch(e: Exception) {
            buffer.pushLogs(logs)
        }
    }

    /**
     * Stop timer if buffer is empty
     */
    fun stopWorkerIfNeeded() {
        if (buffer.isEmpty()) {
            stopWorker()
        }
    }
}