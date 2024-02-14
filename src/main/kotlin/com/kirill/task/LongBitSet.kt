package com.kirill.task


class LongBitSet(size: Long) {
    private val bits: LongArray

    init {
        require(size > 0) { "Size must be greater than zero" }
        val arraySize = (size + 63 ushr 6).toInt()
        bits = LongArray(arraySize)
    }

    fun set(bitIndex: Long) {
        if (bitIndex < 0) {
            throw IndexOutOfBoundsException("Index must be non-negative")
        }
        val arrayIndex = (bitIndex ushr 6).toInt()
        val bitOffset = (bitIndex and 0x3FL).toInt()
        bits[arrayIndex] = bits[arrayIndex] or (1L shl bitOffset)
    }

    operator fun get(bitIndex: Long): Boolean {
        if (bitIndex < 0) {
            throw IndexOutOfBoundsException("Index must be non-negative")
        }
        val arrayIndex = (bitIndex ushr 6).toInt()
        val bitOffset = (bitIndex and 0x3FL).toInt()
        return bits[arrayIndex] and (1L shl bitOffset) != 0L
    }
}