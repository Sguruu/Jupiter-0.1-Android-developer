package com.weather.ls_15

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weather.ls_15.databinding.FragmentUserListBinding
import com.weather.ls_15.view.DetailsFragment
import com.weather.ls_15.view.adapter.PersonAdapter
import com.weather.ls_15.viewmodel.PersonListViewModel

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
        adapter = PersonAdapter({ name, age ->
            //  val keyName = DetailsFragment.KEY_NAME
            //  val keyAge = DetailsFragment.KEY_AGE
            /*
             * нав контоллер есть только у navhost’а. текущий фрагмент списка персон не является
             * навхостом. поэтому начиная с текущего фрагмента этот метод будет обращаться к
             * родительскому фрагменту и проверять, является ли родитель реализацией navhost - если
             * да, то вернется контроллер от найденного хоста. если все родители вплоть до корневого
             * фрагмента не являются реализацией navhosta то выбрасывается исключение.
             */
            findNavController()
                // в него необходимо передать идентификатор action, который мы хотим выполнить при
                // навигации
                .navigate(
                    R.id.action_personListFragment_to_detailsFragment,
                    bundleOf(
                        Pair(DetailsFragment.KEY_NAME, name),
                        Pair(DetailsFragment.KEY_AGE, age)
                    )
                )
            // вернуться назад программно
            // findNavController().popBackStack()
        }, { position ->
            deleteUser(position = position)
        })
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
