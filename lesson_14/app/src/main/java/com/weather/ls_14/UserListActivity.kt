package com.weather.ls_14

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.weather.ls_14.databinding.ActivityUserListBinding

class UserListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(binding.root.id,UserListFragment())
            .commit()
    }
}
