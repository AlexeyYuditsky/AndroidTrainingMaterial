package com.alexeyyuditsky.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import com.alexeyyuditsky.test.databinding.ActivityMainBinding
import com.alexeyyuditsky.test.model.User
import com.alexeyyuditsky.test.model.UsersListener
import com.alexeyyuditsky.test.model.UsersService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: UsersAdapter

    private val usersService: UsersService
        get() = (applicationContext as App).usersService

    private val usersListener: UsersListener = {
        adapter.users = it
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

        adapter = UsersAdapter(actionListener = object : UserActionListener {
            override fun onUserMove(user: User, moveBy: Int) = usersService.moveUser(user, moveBy)

            override fun onUserDelete(user: User) = usersService.deleteUser(user)

            override fun onUserDetails(user: User) =
                Toast.makeText(this@MainActivity, user.name, Toast.LENGTH_SHORT).show()

            override fun onUserFire(user: User) = usersService.fireUser(user)
        })
        binding.recyclerView.adapter = adapter

        // disable flicker when editing a user
        val itemAnimator = binding.recyclerView.itemAnimator
        if (itemAnimator is DefaultItemAnimator) itemAnimator.supportsChangeAnimations = false

        usersService.addListener(usersListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        usersService.removeListener()
    }

}