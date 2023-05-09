package com.weather.myapplication.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.weather.myapplication.R
import com.weather.myapplication.databinding.ActivityMainBinding
import com.weather.myapplication.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
        observeViewModelState()
    }

    private fun initListener() {
        binding.toolbar.setOnMenuItemClickListener { itemMenu ->
            when (itemMenu.itemId) {
                R.id.addCity -> {
                    addCity()
                    true
                }
                R.id.actionSearch -> {
                    // код поиска
                    true
                }
                R.id.deleteCity -> {
                    // код удаления
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun addCity() {
        val navController: NavController =
            Navigation.findNavController(this, R.id.fragmentContainerView2)

        // для того, чтобы при переходе не сохранять backStack
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.listFragment, false)
            .build()

        navController.navigate(R.id.addFragment, null, navOptions)
    }

    /**
     * Подписка
     */
    private fun observeViewModelState() {
        viewModel.isVisibleSearchAction.observe(this) {
            setVisibleActionSearch(it)
        }
    }

    /**
     * Установка значения видимости для значка поиска в toolbar
     */
    private fun setVisibleActionSearch(isVisible: Boolean) {
        binding.toolbar.menu.findItem(R.id.actionSearch).isVisible = isVisible
    }
}
