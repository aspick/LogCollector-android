package com.ensemble_lab.logcollector

import java.time.LocalDateTime
import java.time.ZoneOffset

class TestLogData(val id: String, val time: LocalDateTime, val message: String): LogData {
    override fun orderKey(): Long {
        return time.toInstant(ZoneOffset.UTC).epochSecond
    }

    override fun payload(): Map<String, Any> {
        return mapOf("id" to id)
    }
}