package com.astar.osterrig.controllightmvvm.view.base

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

internal abstract class BaseViewModel() : ViewModel() {

    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        }
    )

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    protected fun cancelJob() {
        Log.d("BaseViewModel", "cancelJob called()")
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    abstract fun handleError(error: Throwable)
}