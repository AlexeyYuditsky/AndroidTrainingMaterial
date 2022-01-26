package com.alexeyyuditsky.test

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alexeyyuditsky.test.interfaces.Navigator
import com.alexeyyuditsky.test.model.User
import com.alexeyyuditsky.test.screens.UserDetailsFragment
import com.alexeyyuditsky.test.screens.UsersListFragment

class MainActivity : AppCompatActivity(R.layout.activity_main), Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) createStartFragment()
    }

    private fun createStartFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, UsersListFragment())
            .commit()
    }

    override fun showDetails(user: User) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, UserDetailsFragment.newInstance(user.id))
            .commit()
    }

    override fun goBack() = onBackPressed()

    override fun toast(messageRes: Int) =
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()

}