package com.weather.ls_14

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.weather.ls_14.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val link1 = "https://cs14.pikabu.ru/post_img/big/2023/02/13/8/1676295806139337963.png"
    private val link2 = "https://www.blast.hk/attachments/64804/"
    private val link3 =
        "https://bipbap.ru/wp-content/uploads/2022/11/1652235714_41-kartinkin-net-p-prikolnie-kartinki-dlya-stima-44.jpg"
    private val link4 =
        "https://avatars.mds.yandex.net/i?id=a151e48f1650195e75984a39c8db7484d4a11928-9140040-images-thumbs&n=13"

    private val listUsers = listOf(
        User(
            name = "Тяпа Тяпков",
            age = 11,
            isDeveloper = true,
            avatarLink = link1
        ),
        User(
            name = "Иван Кортофанов",
            age = 48,
            isDeveloper = false,
            avatarLink = link2
        ),
        User(
            name = "Генадий Горшков",
            age = 34,
            isDeveloper = false,
            avatarLink = link3
        ),
        User(
            name = "Олег Олегов",
            age = 24,
            isDeveloper = true,
            avatarLink = link4
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initList()

        binding.nextScreenButton.setOnClickListener {
            val newScreenIntent = Intent(this, UserListActivity::class.java)
            startActivity(newScreenIntent)
        }
    }

    private fun initList() {
        binding.recyclerView.adapter =
            UserAdapter(listUsers + listUsers + listUsers + listUsers + listUsers + listUsers)
        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        // говорит о том что размер списка не будет увеличиваться что позволит оптимзировать список
        binding.recyclerView.setHasFixedSize(true)
    }
}
