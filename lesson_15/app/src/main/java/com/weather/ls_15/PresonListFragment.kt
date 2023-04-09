package com.weather.ls_15

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weather.ls_15.databinding.FragmentUserListBinding

class PersonListFragment : Fragment(R.layout.fragment_user_list) {

    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!
    private var adapter: PersonAdapter? = null

    // этот метод возвращает Lazy проперти делегат, который при первом обращении ко вьюмодели создаст
    // ее, закеширует и потом будет использовать вьюмодель из кеша.
    // один момент, который следует учесть - обращаться ко вью модели можно только после метода ЖЦ
    // onAttach потому что под капотом происходит обращение к активити для создания вьюмодели.
    private val personListViewModel: PersonListViewModel by viewModels()

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
        binding.addFloatingActionButton.setOnClickListener { addUser() }
        observeViewModelState()
    }

    private fun addUser() {
        personListViewModel.addPerson()
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
    }

    private fun deleteUser(position: Int) {
        Toast.makeText(requireContext(), "deleteUser $position", Toast.LENGTH_SHORT).show()
        personListViewModel.deletePerson(position)
        adapter?.notifyItemRemoved(position)
    }

    // наблюдение за изменением состоянии viewModel
    private fun observeViewModelState() {
        personListViewModel.personLiveData
            /*
            * на вход этой функции необходимо первым параметром передать lifecycleowner для
            * тогго, чтобы подписчик автоматически отписался когда фрагмент станет неактивным
            * для предотвращения утечки памяти,
            * второй параметр лямбду функцию
            * */
            .observe(viewLifecycleOwner) { newPersons ->
                adapter?.updatePersons(newPersons.orEmpty())
            }

        personListViewModel.showToastLiveData
            .observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), "Пользователь добавлен", Toast.LENGTH_SHORT).show()
            }
    }
}
