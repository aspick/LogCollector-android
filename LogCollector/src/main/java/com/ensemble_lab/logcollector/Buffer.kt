package com.ensemble_lab.logcollector

/**
 * Buffer class of log data
 *
 * @args batchSize
 */
class Buffer(val batchSize: Int = 5) {
    private var data = mutableListOf<LogData>()

    /**
     * @args logs to insert logs
     */
    fun pushLogs(logs: List<LogData>) {
        data.addAll(logs)
        data.sortBy { it.orderKey() }
    }

    /**
     * @return popped logs
     */
    fun popLogs() : List<LogData> {
        val poppedLogs = data.take(batchSize)
        data = data.drop(batchSize).toMutableList()
        return poppedLogs
    }

    /**
     * @return buffer is empty or not
     */
    fun isEmpty(): Boolean {
        return data.isEmpty()
    }
}