package com.example.surfcocktailscompose.util

interface StateRestorer<S : UiState> {
    fun addState(state: S)
    fun selectState(position: Int)
    fun getStates(): List<S>
}

class StateRestorerImpl<S : UiState>(
    private val onStateSelected: (S) -> Unit
) : StateRestorer<S> {

    private val states = mutableListOf<S>()

    override fun addState(state: S) {
        states.add(state)
    }

    override fun selectState(position: Int) {
        onStateSelected(states[position])
    }

    override fun getStates(): List<S> {
        return states
    }
}