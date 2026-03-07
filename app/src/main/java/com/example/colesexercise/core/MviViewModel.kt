package com.example.colesexercise.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface MviViewModelEvent

interface MviViewModelState

interface MviViewModelEffect

abstract class MviViewModel<
    Event : MviViewModelEvent,
    State : MviViewModelState,
    Effect : MviViewModelEffect,
    > : ViewModel() {

    abstract fun setInitialState(): State
    abstract fun handleEvents(event: Event)

    private val initialState: State by lazy { setInitialState() }

    private val _viewState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val viewState: StateFlow<State> = _viewState.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect {
                handleEvents(it)
            }
        }
    }

    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    protected fun setState(reducer: State.() -> State) {
        val newState = viewState.value.reducer()
        _viewState.compareAndSet(viewState.value, newState)
    }

    protected fun setEffect(builder: () -> Effect) {
        val actionValue = builder()
        viewModelScope.launch {
            _effect.send(actionValue)
        }
    }
}

