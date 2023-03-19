package com.weather.ls12

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.weather.ls12.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("MyTest", "MainActivity onCreate")

        binding.buttonAddFragment.setOnClickListener {
            addFragment()
        }

        binding.buttonReplace.setOnClickListener {
            replaceFragment()
        }

        binding.buttonPopBackstack.setOnClickListener {
            val stateName = getStateName()
            if (stateName != null) {
                supportFragmentManager.popBackStack(stateName, 0)
            } else {
                supportFragmentManager.popBackStack()
            }
        }

        binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.editTextStateName.visibility = View.VISIBLE
            } else {
                binding.editTextStateName.visibility = View.GONE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("MyTest", "MainActivity onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MyTest", "MainActivity onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MyTest", "MainActivity onPause")
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        Log.d("MyTest", "MainActivity onSaveInstanceState")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MyTest", "MainActivity onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyTest", "MainActivity onDestroy")
    }

    override fun onBackPressed() {
        // заглушка
    }

    private fun addFragment() {
        val alreadyHasFragment =
            supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) != null

        //      if (!alreadyHasFragment) {
        val transaction = supportFragmentManager.beginTransaction()
            .add(binding.fragmentContainerView.id, FragmentOne())
//        } else {
//            Toast.makeText(this, "Фрагмент уже показан", Toast.LENGTH_SHORT).show()
//        }
        if (binding.checkBox.isChecked) {
            transaction.addToBackStack(getStateName())
        }
        transaction.commit()
    }

    private fun replaceFragment() {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainerView.id, FragmentOne())
        if (binding.checkBox.isChecked) {
            transaction.addToBackStack(getStateName())
        }
        transaction.commit()
    }

    private fun getStateName(): String? {
        /*
        trim - Возвращает строку, в которой удалены начальные и конечные пробелы.
         */
        val trimText = binding.editTextStateName.text.toString().trim()

        if (trimText.isNotEmpty()) {
            return trimText
        } else {
            return null
        }
    }
}
