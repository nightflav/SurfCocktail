package com.example.surfcocktailscompose.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseReducer<S : UiState, E : UiEvent>(initialVal: S) {

    private val _state: MutableStateFlow<S> = MutableStateFlow(initialVal)
    val state: StateFlow<S>
        get() = _state

    fun sendEvent(event: E) {
        reduce(_state.value, event)
    }

    fun setState(newState: S) {
        val success = _state.tryEmit(newState)

        if (success) {
            stateRestorer.addState(newState)
        }
    }

    val stateRestorer: StateRestorer<S> = StateRestorerImpl { storedState ->
        _state.tryEmit(storedState)
    }

    init {
        stateRestorer.addState(initialVal)
    }

    abstract fun reduce(oldState: S, event: E)
}