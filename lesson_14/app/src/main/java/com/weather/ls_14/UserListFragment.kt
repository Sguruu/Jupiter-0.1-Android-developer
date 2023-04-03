package com.weather.ls_14

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weather.ls_14.databinding.FragmentUserListBinding

class UserListFragment : Fragment(R.layout.fragment_user_list) {
    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!
    private var adapter: UserAdapter? = null

    private val link1 = "https://cs14.pikabu.ru/post_img/big/2023/02/13/8/1676295806139337963.png"
    private val link2 = "https://www.blast.hk/attachments/64804/"
    private val link3 =
        "https://bipbap.ru/wp-content/uploads/2022/11/1652235714_41-kartinkin-net-p-prikolnie-kartinki-dlya-stima-44.jpg"
    private val link4 =
        "https://avatars.mds.yandex.net/i?id=a151e48f1650195e75984a39c8db7484d4a11928-9140040-images-thumbs&n=13"

    private var listUsers = listOf(
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
        val newUser = listUsers.random()
        // создаем новый список (старый + новый пользователь)
        listUsers = listOf(newUser) + listUsers

        // обновление списка
        adapter?.updateUsers(listUsers)

        // добавляем элемент в список
        adapter?.notifyItemInserted(0)
        // скролл до нужной позиции
        binding.recyclerView.scrollToPosition(0)
    }

    private fun initList() {
        adapter = UserAdapter(listUsers) { position ->
            deleteUser(position)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }

    private fun deleteUser(position: Int) {
        Toast.makeText(requireContext(), "deleteUser $position", Toast.LENGTH_SHORT).show()
        // отфилтруем пользователей так, что получим всех пользователей кроме пользователя по нажатой
        // позиции
        listUsers = listUsers.filterIndexed { index, user -> index != position }
        adapter?.updateUsers(listUsers)
        adapter?.notifyItemRemoved(position)
    }
}
