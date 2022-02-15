package com.alexeyyuditsky.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.alexeyyuditsky.test.databinding.ActivityMainBinding

sealed class AcceptedCurrency {

    abstract val valueInRubels: Double

    var amount: Int = 0

    val name: String
        get() = when (this) {
            is Rubel -> "Рубль"
            is Dollar -> "Доллар"
            is Tugrik -> "Тугрик"
        }

    fun totalValueInRubels(): Double {
        return amount * valueInRubels
    }
}

class Rubel : AcceptedCurrency() {
    override val valueInRubels: Double = 1.0
}

class Dollar : AcceptedCurrency() {
    override val valueInRubels: Double = 75.0
}

class Tugrik : AcceptedCurrency() {
    override val valueInRubels: Double = 5.0
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val currencies = listOf(Rubel(), Dollar(), Tugrik())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            currencies.map { it.name }
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        binding.convert.setOnClickListener {
            val left = currencyFromSelection()
            val right = currencyFromSelection()

            left.amount = binding.leftEditText.text.toString().toInt()
            right.amount = binding.rightEditText.text.toString().toInt()

            binding.topTextView.text = String.format("%.2f руб.", left.totalValueInRubels())
            binding.bottomTextView.text = String.format("%.2f руб.", right.totalValueInRubels())
        }

    }

    private fun currencyFromSelection(): AcceptedCurrency {
        return when (currencies[binding.spinner.selectedItemPosition]) {
            is Dollar -> Dollar()
            is Rubel -> Rubel()
            is Tugrik -> Tugrik()
        }
    }

}