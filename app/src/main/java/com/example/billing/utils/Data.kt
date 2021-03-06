package com.example.billing.utils

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.billing.utils.datas.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.gson.annotations.Expose

data class RememberState<T>(
    @Expose var value: T,
    var valueState: MutableState<T> = mutableStateOf(value = value),
) {
    fun getState(): MutableState<T> {
        try {
            this.valueState.value = this.value
        } catch (e: NullPointerException) {
            this.valueState = mutableStateOf(value = value)
        }
        return valueState
    }

    infix fun allEtc(value: T): Boolean {
        if (getState().value == value) {
            return true
        }
        return false
    }

    infix fun set(value: T) {
        this.value = value
        getState().value = this.value
    }

    infix fun set(value: RememberState<T>) {
        this.value = value.value
        this set this.value
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            !is RememberState<*> -> false
            else -> this === other || value == other.value
        }
    }

    fun toIntState() = RememberState<Int>(value = this.getState().value.toString().toInt())

    fun toStringState() = RememberState<String>(value = this.getState().value.toString())
}

data class Screen(
    val width: Int,
    val height: Int,
)

data class Settings @OptIn(ExperimentalPagerApi::class) constructor(
    @Expose val user: User = User(),
    @Expose val navDefault: RememberState<Int> = RememberState(0),
    @Expose var navLastTimeSelected: RememberState<Int> = RememberState(0),
    @Expose val bottomBarStyle: RememberState<Boolean> = RememberState(true),
    @Expose val theme: RememberState<Int> = RememberState(1)
)