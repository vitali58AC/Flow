package com.example.flow.base

interface EventHandler<T> {
    fun obtainEvent(event: T)
}