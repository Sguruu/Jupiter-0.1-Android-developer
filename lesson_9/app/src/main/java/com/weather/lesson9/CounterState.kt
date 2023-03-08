package com.weather.lesson9

import android.os.Parcel
import android.os.Parcelable

data class CounterState(
    val count: Int,
    val message: String
) : Parcelable {
    // чтение значения из parcel
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        // вернем пустую строку если будет null
        parcel.readString().orEmpty()
    ) {
    }

    fun increment(): CounterState {
        return this.copy(count = count + 1)
    }

    fun decrement(): CounterState {
        return this.copy(count = count - 1)
    }

    /**
     * Сохраняет данные из объект текущего класса в parcel
     * Порядок сохранения важен
     * @param parcel специальный класс, который предназначен для передачи данных между компонентами
     * @param flags влияют на поведение файловых дискрипторов, в большенстве случаев они не
     * используются
     * в android
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(count)
        parcel.writeString(message)
    }

    override fun describeContents(): Int {
        return 0
    }

    /**
     * Parcelable.Creator<CounterState> - Интерфейс который позволяет создать из Parcel объект
     * CounterState
     */
    companion object CREATOR : Parcelable.Creator<CounterState> {
        /**
         * Создает из parcel объект нашего класса
         */
        override fun createFromParcel(parcel: Parcel): CounterState {
            return CounterState(parcel)
        }

        /**
         * Создание массивов объектов
         * @return массив нужной длины, который заполнен нулями
         */
        override fun newArray(size: Int): Array<CounterState?> {
            return arrayOfNulls(size)
        }
    }
}
