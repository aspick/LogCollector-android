package com.ensemble_lab.logcollector

import org.junit.Test

import org.junit.Assert.*
import java.time.LocalDateTime
import java.util.*

class BufferUnitTest {
    @Test
    fun pushLogs_isSuccess() {
        val buffer = Buffer()
        val log = TestLogData(UUID.randomUUID().toString(), LocalDateTime.now(), "Test Log")

        buffer.pushLogs(listOf(log))

        assertEquals(false, buffer.isEmpty())
    }

    @Test
    fun popLogs_poppedSortedOrder() {
        val buffer = Buffer()
        val log1 = TestLogData(UUID.randomUUID().toString(), LocalDateTime.now().plusSeconds(1), "Test Log 1")
        val log2 = TestLogData(UUID.randomUUID().toString(), LocalDateTime.now().plusSeconds(2), "Test Log 2")

        buffer.pushLogs(listOf(log1, log2))

        assertEquals(listOf(log1, log2), buffer.popLogs())
    }

    @Test
    fun popLogs_poppedSortedOrder2() {
        val buffer = Buffer()
        val log1 = TestLogData(UUID.randomUUID().toString(), LocalDateTime.now().plusSeconds(1), "Test Log 1")
        val log2 = TestLogData(UUID.randomUUID().toString(), LocalDateTime.now().plusSeconds(2), "Test Log 2")

        buffer.pushLogs(listOf(log2, log1))

        assertEquals(listOf(log1, log2), buffer.popLogs())
    }

    @Test
    fun popLogs_poppedWithBatchSeize() {
        val buffer = Buffer(batchSize = 4)
        for (i in 1..5) {
            val log = TestLogData(UUID.randomUUID().toString(), LocalDateTime.now().plusSeconds(i.toLong()), "Test Log ${i}")
            buffer.pushLogs(listOf(log))
        }

        assertEquals(buffer.popLogs().count(), 4)
        assertEquals(buffer.popLogs().count(), 1)
    }

    @Test
    fun popLogs_poppedEmptyListWhenBufferIsEmpty() {
        val buffer = Buffer()

        assertEquals(buffer.popLogs(), emptyList<TestLogData>())
    }

    @Test
    fun popLogs_poppedWithSortedOrder() {
        val buffer = Buffer(batchSize = 4)
        val seeds = listOf(1,2,3,4,5)
        val logs = seeds.map { i ->
            TestLogData(UUID.randomUUID().toString(), LocalDateTime.now().plusSeconds(i.toLong()), "Test Log ${i}")
        }
        buffer.pushLogs(logs)

        assertEquals(buffer.popLogs(), listOf(logs[0], logs[1], logs[2], logs[3]))
        assertEquals(buffer.popLogs(), listOf(logs[4]))
    }

    @Test
    fun isEmpty_isTrueWithEmptyBuffer() {
        val buffer = Buffer()
        assertEquals(true, buffer.isEmpty())
    }

    @Test
    fun isEmpty_isFalseWithContainedBuffer() {
        val buffer = Buffer()
        val log = TestLogData(UUID.randomUUID().toString(), LocalDateTime.now(), "Test Log")

        buffer.pushLogs(listOf(log))

        assertEquals(false, buffer.isEmpty())
    }
}



