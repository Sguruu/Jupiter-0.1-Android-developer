package com.weather.ls_14

sealed class Person {

    data class User(
        // имя пользователя
        val name: String,
        // ссылка на автар из интернета
        val avatarLink: String,
        // возраст
        val age: Int
    ) : Person()

    data class Developer(
        // имя пользователя
        val name: String,
        // ссылка на автар из интернета
        val avatarLink: String,
        // возраст
        val age: Int,
        // языка программирования
        val programmingLanguage: String
    ) : Person()
}
