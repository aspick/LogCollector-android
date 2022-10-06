package com.ensemble_lab.logcollector

/**
 * Log data interface
 */
interface LogData {
    /**
     * Sort order key of log data
     * Worker processes logs order of this parameter as asc
     *
     * @return order index value
     */
    fun orderKey() :Long
}