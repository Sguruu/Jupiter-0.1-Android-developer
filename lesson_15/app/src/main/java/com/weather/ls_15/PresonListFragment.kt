package com.weather.ls_15

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weather.ls_15.databinding.FragmentUserListBinding

class PersonListFragment : Fragment(R.layout.fragment_user_list) {
    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!
    private var adapter: PersonAdapter? = null

    private val link1 = "https://cs14.pikabu.ru/post_img/big/2023/02/13/8/1676295806139337963.png"
    private val link2 = "https://www.blast.hk/attachments/64804/"
    private val link3 =
        "https://bipbap.ru/wp-content/uploads/2022/11/1652235714_41-kartinkin-net-p-prikolnie-kartinki-dlya-stima-44.jpg"
    private val link4 =
        "https://avatars.mds.yandex.net/i?id=a151e48f1650195e75984a39c8db7484d4a11928-9140040-images-thumbs&n=13"

    private var listPerson = listOf(
        Person.Developer(
            name = "Тяпа Тяпков",
            age = 11,
            avatarLink = link1,
            programmingLanguage = "Java"
        ),
        Person.User(
            name = "Иван Кортофанов",
            age = 48,
            avatarLink = link2
        ),
        Person.User(
            name = "Генадий Горшков",
            age = 34,
            avatarLink = link3
        ),
        Person.Developer(
            name = "Олег Олегов",
            age = 24,
            avatarLink = link4,
            programmingLanguage = "Kotlin"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        binding.addFloatingActionButton.setOnClickListener {
            addUser()
        }
    }

    private fun addUser() {
        // сделаем так, что будем добавлять случайного пользователя в список
        // получение случаайного пользователя
        val newUser = listPerson.random()
        // создаем новый список (старый + новый пользователь)
        listPerson = listOf(newUser) + listPerson

        // обновление списка
        adapter?.updatePersons(listPerson)

        // добавляем элемент в список
        adapter?.notifyItemInserted(0)
        // скролл до нужной позиции
        binding.recyclerView.scrollToPosition(0)
    }

    private fun initList() {
        adapter = PersonAdapter { position ->
            deleteUser(position)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        adapter?.updatePersons(listPerson)
        adapter?.notifyDataSetChanged()
    }

    private fun deleteUser(position: Int) {
        Toast.makeText(requireContext(), "deleteUser $position", Toast.LENGTH_SHORT).show()
        // отфилтруем пользователей так, что получим всех пользователей кроме пользователя по нажатой
        // позиции
        listPerson = listPerson.filterIndexed { index, user -> index != position }
        adapter?.updatePersons(listPerson)
        adapter?.notifyItemRemoved(position)
    }
}
