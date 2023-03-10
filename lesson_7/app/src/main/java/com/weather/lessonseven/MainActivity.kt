package com.weather.lessonseven

import android.os.Bundle
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.weather.lessonseven.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val users = listOf(
        "User1",
        "User2",
        "Unknown"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
    }

    private fun initToolbar() {
        binding.toolbar.title = "Новый текст из кода"

        binding.toolbar.setNavigationOnClickListener {
            Toast.makeText(this, "Стрелка назад нажата", Toast.LENGTH_SHORT).show()
        }

        binding.toolbar.setOnMenuItemClickListener {
            // через it можем получить доступ ко всем элементам меню и их атрибутам
            // получаем id элемента по которому кликнули  it.itemId
            when (it.itemId) {
                R.id.action_1 -> {
                    // означает что мы отработали нажатие на элемент меню
                    toast("action 1 clicked")
                    true
                }
                R.id.action_2 -> {
                    toast("action 2 clicked")
                    true
                }
                else -> false
            }
        }

        // получение доступа к элементу меню
        val searchItem = binding.toolbar.menu.findItem(R.id.action_search)

        // повесим слушатель (он опеващается когда у нас появляет search view и закрывается)
        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            // вызывается когда нажал на кнопку поиска
            override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                binding.expandTextView.text = "search expanded"
                // это означет что функция отработала и view будет раскрыта
                return true
            }

            // когда пользователь нажал на закрытие евента и search view превратилась в иконку пользователя
            override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                binding.expandTextView.text = "search collapsed"
                return true
            }
        })

        // кастим (приводим) тип и вызываем у него слушатель
        (searchItem.actionView as SearchView).setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                // Вызвается тогда, когда пользователь нажимает на значек поиска
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return true
                }

                // Вызывается тогда, когда пользователь что-то вводит
                override fun onQueryTextChange(p0: String?): Boolean {
                    users.filter { it.contains(p0 ?: "", ignoreCase = true) }
                        // все отфилтрованные строки объединяться в одну строку
                        .joinToString()
                        .let {
                            binding.searchResultTextView.text = it
                        }
                    return true
                }
            })
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
