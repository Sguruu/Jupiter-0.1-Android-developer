package com.weather.lesson9

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CounterState(
    val count: Int,
    val message: String
) : Parcelable {

    fun increment(): CounterState {
        return this.copy(count = count + 1)
    }

    fun decrement(): CounterState {
        return this.copy(count = count - 1)
    }
}
