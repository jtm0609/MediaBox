package com.example.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event : UiEvent, State : UiState, Effect : UiEffect> : ViewModel() {

    abstract fun createInitialState(): State
    abstract fun handleEvent(event: Event)

    private val initialState: State by lazy { createInitialState() }

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _event: Channel<Event> = Channel()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    private val currentState: State get() = _uiState.value

    init {
        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            _event.receiveAsFlow().collect {
                handleEvent(it)
            }
        }
    }

    fun setEvent(event: Event) {
        viewModelScope.launch {
            _event.send(event)
        }
    }

    fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }
}