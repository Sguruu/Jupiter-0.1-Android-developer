package com.weather.ls12

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
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

    private fun addFragment() {
        val alreadyHasFragment =
            supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) != null

        //      if (!alreadyHasFragment) {
        supportFragmentManager.beginTransaction()
            .add(binding.fragmentContainerView.id, FragmentOne())
            .commit()
//        } else {
//            Toast.makeText(this, "Фрагмент уже показан", Toast.LENGTH_SHORT).show()
//        }
    }

    private fun replaceFragment() {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainerView.id, FragmentOne())
            .commit()
    }
}
