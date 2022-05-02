package com.alexeyyuditsky.test

import android.annotation.SuppressLint
import android.database.SQLException
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.alexeyyuditsky.test.databinding.FragmentMainBinding
import com.alexeyyuditsky.test.sqlite.AppSQLiteHelper
import com.alexeyyuditsky.test.sqlite.Repositories
import com.alexeyyuditsky.test.sqlite.Repositories.database
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        var integer1 = 1
        var integer2 = 3
        binding.insertDataButton.setOnClickListener { insertData(integer1++) }
        binding.getDataButton.setOnClickListener { getData() }
        binding.deleteDataButton.setOnClickListener { deleteData(integer2--) }
    }

    private fun deleteData(integer: Int) {
        database.execSQL("delete from companies where id = $integer")
    }

    @SuppressLint("SetTextI18n")
    private fun getData() {
        lifecycleScope.launch {
            try {
                val sql = "select *, strftime('%H:%M, %d.%m.%Y', created_at) as date from users"
                database.rawQuery(sql, null).use {
                    while (it.moveToNext()) {
                        binding.textView1.text = it.getString(it.getColumnIndexOrThrow("created_at")) + " |"
                        binding.textView2.text = it.getString(it.getColumnIndexOrThrow("name")) + " |"
                        binding.textView3.text = it.getString(it.getColumnIndexOrThrow("date")) ?: "NULL"
                        binding.textView4.isVisible = false
                        binding.textView5.isVisible = false
                        binding.textView6.isVisible = false
                        delay(2000)
                    }
                }
            } catch (e: SQLException) {
                binding.errorTextView.text = e.toString()
            }
        }
    }

    private fun insertData(integer: Int) {
        try {
            if (binding.companiesEditText.text.toString().isNotBlank()) {
                val text = binding.companiesEditText.text.toString()
                database.execSQL("insert into companies values (?, '$text')")
            } else if (binding.usersEditText.text.toString().isNotBlank()) {
                val text = binding.usersEditText.text.toString()
                val randomInteger = Random.nextInt(18, 40)
                database.execSQL("insert into users values (?, '$text', $randomInteger, $integer)")
            }
        } catch (e: SQLException) {
            binding.errorTextView.text = e.toString()
        }
    }

}