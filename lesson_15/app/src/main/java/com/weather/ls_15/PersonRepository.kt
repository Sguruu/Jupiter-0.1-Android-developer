package com.weather.ls_15

class PersonRepository {
    private val link1 = "https://cs14.pikabu.ru/post_img/big/2023/02/13/8/1676295806139337963.png"
    private val link2 = "https://www.blast.hk/attachments/64804/"
    private val link3 =
        "https://bipbap.ru/wp-content/uploads/2022/11/1652235714_41-kartinkin-net-p-prikolnie-kartinki-dlya-stima-44.jpg"
    private val link4 =
        "https://avatars.mds.yandex.net/i?id=a151e48f1650195e75984a39c8db7484d4a11928-9140040-images-thumbs&n=13"

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
        return generatePersons(count)
    }

    /**
     * @param oldListPerson старый список персон
     * @return обновленный список персон с добавленным пользователем
     */
    fun addPerson(oldListPerson: List<Person>): List<Person> {
        val newUser = generatePersons(1)
        return newUser + oldListPerson
    }

    /**
     * @param oldListPerson текущий список из которого нужно удалить пользователя
     * @param position позиция элемента для удаления
     * @return обновленный список персон с удаленным пользователем
     */
    fun deletePerson(oldListPerson: List<Person>, position: Int): List<Person> {
        return oldListPerson.filterIndexed { index, _ -> index != position }
    }
}
