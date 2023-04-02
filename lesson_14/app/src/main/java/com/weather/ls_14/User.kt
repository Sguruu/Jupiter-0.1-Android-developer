package com.weather.ls_14

data class User(
    // имя пользователя
    val name: String,
    // ссылка на автар из интернета
    val avatarLink: String,
    // возраст
    val age: Int,
    // обзначет является ли пользователь разработчиком или нет
    val isDeveloper: Boolean
)
