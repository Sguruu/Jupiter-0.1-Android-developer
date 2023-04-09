package com.weather.ls_15

import android.util.Log
import androidx.lifecycle.ViewModel

class PersonListViewModel : ViewModel() {

    private val link1 = "https://cs14.pikabu.ru/post_img/big/2023/02/13/8/1676295806139337963.png"
    private val link2 = "https://www.blast.hk/attachments/64804/"
    private val link3 =
        "https://bipbap.ru/wp-content/uploads/2022/11/1652235714_41-kartinkin-net-p-prikolnie-kartinki-dlya-stima-44.jpg"
    private val link4 =
        "https://avatars.mds.yandex.net/i?id=a151e48f1650195e75984a39c8db7484d4a11928-9140040-images-thumbs&n=13"

    private var listPersons: List<Person> = emptyList()

    // метод генерации персон
    private fun generatePersons(count: Int): List<Person> {
        val names = listOf(
            "Тяпа Тяпков",
            "Иван Кортофанов",
            "Генадий Горшков",
            "Олег Олегов"
        )
        val avatars = listOf(link1, link2, link3, link4)
        val listProgrammingLanguage = listOf("Kotlin", "Java", "C++", "ЕптаСкрипт", "Go", "Dark")
        val rangeAge = 14..70
        val listIsDeveloper = listOf(true, false)

        val listPersons = (1..count).map {
            val name = names.random()
            val avatar = avatars.random()
            val programmingLanguage = listProgrammingLanguage.random()
            val age = rangeAge.random()
            val isDeveloper = listIsDeveloper.random()

            when (isDeveloper) {
                true -> {
                    Person.Developer(
                        name = name,
                        avatarLink = avatar,
                        age = age,
                        programmingLanguage = programmingLanguage
                    )
                }
                false -> {
                    Person.User(
                        name = name,
                        avatarLink = avatar,
                        age = age
                    )
                }
            }
        }

        return listPersons
    }

    fun createListPerson(count: Int): List<Person> {
        listPersons = generatePersons(count)
        return listPersons
    }

    fun addPerson() {
        val newUser = generatePersons(1)
        listPersons = newUser + listPersons
        Log.d("MyTest", "addPerson ${listPersons.size}")
    }

    fun deletePerson(position: Int) {
        listPersons = listPersons.filterIndexed { index, _ -> index != position }
        Log.d("MyTest", "deletePerson ${listPersons.size}")
    }

    fun getListPersons() = listPersons
}
