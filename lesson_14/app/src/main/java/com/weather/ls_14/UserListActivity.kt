package com.weather.ls_14

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.weather.ls_14.databinding.ActivityUserListBinding

class UserListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("MyTest", "getStringExtra ${intent.getStringExtra(KEY_NAME_FRAGMENT)}")
        val nameFragment: String =
            intent.getStringExtra(KEY_NAME_FRAGMENT) ?: NAME_USER_LIST_FRAGMENT

        val transactionFragment = supportFragmentManager.beginTransaction()

        when (nameFragment) {
            NAME_USER_LIST_FRAGMENT -> {
                Log.d("MyTest", "NAME_USER_LIST_FRAGMENT")
                transactionFragment.add(binding.root.id, UserListFragment())
                transactionFragment.commit()
            }
            NAME_PERSON_LIST_FRAGMENT -> {
                Log.d("MyTest", "NAME_PERSON_LIST_FRAGMENT")
                transactionFragment.add(binding.root.id, PersonListFragment())
                transactionFragment.commit()
            }
            else -> error("Ожидалось имя фрагмента")
        }
    }

    companion object {
        const val NAME_PERSON_LIST_FRAGMENT = "NAME_PERSON_LIST_FRAGMENT"
        const val NAME_USER_LIST_FRAGMENT = "NAME_USER_LIST_FRAGMENT"
        private const val KEY_NAME_FRAGMENT = "KEY_NAME_FRAGMENT"
        fun getIntentUserListActivity(context: Context, nameStartFragment: String): Intent {
            val intentActivity = Intent(context, UserListActivity::class.java)
            intentActivity.putExtra(KEY_NAME_FRAGMENT, nameStartFragment)
            return intentActivity
        }
    }
}
