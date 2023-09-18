package com.alpha.showcase.common.ui.vm

abstract class BaseViewModel {
}

interface BaseState
sealed interface UiState<out T> : BaseState {
  data class Content<out T>(val data: T) : UiState<T>
  object Loading : UiState<Nothing>
  data class Error(val msg: String? = "Error") : UiState<Nothing>
}

val UiState<*>.succeeded
  get() = this is UiState.Content && data != null