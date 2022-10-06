package com.ensemble_lab.logcollector

/**
 * Log collector main class
 *
 * @args batchSize max count of payloads to pass each worker task
 * @args workerTask worker task function
 * @args workerInterval worker invoker interval seconds
 */
class LogCollector(val batchSize: Int = 5, val workerTask: (List<LogData>) -> Unit, val workerInterval: Long = 60) {
    private val buffer = Buffer(batchSize)
    private val supervisor = Supervisor(buffer, workerTask, workerInterval)

    /**
     * Store and send lo to server asynchronously
     *
     * @args log log data
     */
    fun storeLog(log: LogData) {
        buffer.pushLogs(listOf(log))
        supervisor.startWorker()
    }
}