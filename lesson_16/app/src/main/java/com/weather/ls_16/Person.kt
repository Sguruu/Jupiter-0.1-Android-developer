package com.weather.ls_16

import android.util.Log

data class Person(
    val name: String
) {

    @Synchronized
    fun throwBallTo(friend: Person) {
        Log.d("MyTest", "$name бросает мяч ${friend.name} на потоке ${Thread.currentThread().name}")
        // эмуляция того что мяч летит
        Thread.sleep(500)
        // указывает что друг должен бросить мяч нам обратно
        friend.throwBallTo(this)
    }
}
