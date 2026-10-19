package com.example.lifecyclemaster

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class CounterViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val count = savedStateHandle.getStateFlow(
        key = "counter_key",
        initialValue = 0
    )

    fun increment() {
        savedStateHandle["counter_key"] = count.value + 1
    }
}